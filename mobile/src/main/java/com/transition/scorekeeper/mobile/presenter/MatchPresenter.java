package com.transition.scorekeeper.mobile.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.transition.scorekeeper.R;
import com.transition.scorekeeper.common.Constants;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.exception.DefaultErrorBundle;
import com.transition.scorekeeper.domain.interactor.DefaultSubscriber;
import com.transition.scorekeeper.domain.interactor.PostMatch;
import com.transition.scorekeeper.domain.interactor.UseCase;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.exception.ErrorMessageFactory;
import com.transition.scorekeeper.mobile.internal.di.PerActivity;
import com.transition.scorekeeper.mobile.model.GoalModel;
import com.transition.scorekeeper.mobile.model.LogModel;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.model.TeamModel;
import com.transition.scorekeeper.mobile.model.factories.NumberPickerDialogFactory;
import com.transition.scorekeeper.mobile.model.mapper.MatchModelDataMapper;
import com.transition.scorekeeper.mobile.view.fragment.dialog.NumberPickerDialog;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayerDialog;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayersDialog;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.MatchView;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author diego.rotondale
 * @since 24/05/16
 */
@PerActivity
public class MatchPresenter implements Presenter, NumberPickerDialog.INumberPickerDialogListener,
        PlayersDialog.IPlayersDialogDialogListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DataApi.DataListener {
    private MatchModelDataMapper matchModelDataMapper;
    private MatchView matchView;
    private MatchModel matchModel;
    private boolean wasUpdated;
    private PostMatch postMatchUseCase;
    private GoogleApiClient mApiClient;
    private boolean showWearableMenu = false;

    @Inject
    public MatchPresenter(@Named("postMatch") UseCase postMatchUseCase,
                          MatchModelDataMapper matchModelDataMapper) {
        this.postMatchUseCase = (PostMatch) postMatchUseCase;
        this.matchModelDataMapper = matchModelDataMapper;
    }

    public void setView(@NonNull MatchView view) {
        this.matchView = view;
    }

    @Override
    public void resume() {
        if (mApiClient != null) {
            mApiClient.connect();
        } else {
            mApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(Wearable.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mApiClient.connect();
            Wearable.DataApi.addListener(mApiClient, this);
        }
    }

    @Override
    public void pause() {
        if (mApiClient != null) {
            mApiClient.disconnect();
        }
    }

    @Override
    public void destroy() {
        this.matchView = null;
        if (mApiClient != null) {
            mApiClient.disconnect();
        }
    }

    public void onPlayerClick(PlayerModel playerModel) {
        if (matchModel.isTheMatchInProgress()) {
            score(playerModel);
        } else {
            if (matchModel.needTheMachStart()) {
                selectPlayer(playerModel);
            }
        }
    }

    private void selectPlayer(PlayerModel playerModel) {
        PlayersDialog dialog = new PlayersDialog();
        Bundle args = new Bundle();
        args.putSerializable(ViewConstants.ExtraKeys.PLAYER, playerModel);
        args.putSerializable(ViewConstants.ExtraKeys.PLAYERS_ON_MATCH, (Serializable) matchModel.getPlayersIds());
        dialog.setArguments(args);
        dialog.show(matchView.getFragmentManager(), ViewConstants.FragmentTag.PLAYERS_DIALOG);
    }

    private void score(PlayerModel playerModel) {
        GoalModel goalModel = getGoal(playerModel);
        boolean wasAdded = matchModel.addGoal(goalModel);
        if (wasAdded) {
            onGoalAdded(goalModel);
        } else {
            onGoalNotAdded();
        }
    }

    private void onGoalAdded(GoalModel goalModel) {
        wasUpdated = true;
        addLog(goalModel.getLog());
        matchFinish();
        updateMatch();
        matchView.updateResult(matchModel);
        matchView.showSnackBar(Snackbar.LENGTH_LONG, goalModel.getDescription(), null);
    }

    private void addLog(LogModel log) {
        matchModel.addLog(log);
        matchView.addLog(log);
    }

    private void onGoalNotAdded() {
        String message;
        if (matchModel.isTheMatchEnd()) {
            matchFinish();
            message = matchView.getContext().getString(R.string.match_status_end);
        } else {
            message = matchView.getContext().getString(R.string.error_adding_the_goal);
        }
        matchView.toast(Toast.LENGTH_SHORT, message);
    }

    @NonNull
    private GoalModel getGoal(PlayerModel playerModel) {
        return new GoalModel.GoalModelBuilder(matchView.getContext())
                .setMatchStart(matchModel.getMatchStart())
                .setPlayer(playerModel)
                .setPosition(playerModel.getPreferentialPosition())
                .build();
    }

    public MatchModel getMatchModel(MatchModel matchModel) {
        if (this.matchModel == null) {
            this.matchModel = matchModel;
        }
        return this.matchModel;
    }

    public void matchFinish() {
        addMatchEndLogs();
        matchView.onMatchEndSuccessfully();
    }

    private void addMatchEndLogs() {
        List<LogModel> logs = matchModel.getMatchEndLog(matchView.getContext());
        if (logs != null) {
            matchModel.addLog(logs);
            matchView.addLogs(logs);
        }
    }

    public void matchEnd() {
        wasUpdated = true;
        matchModel.setMatchEnd(new Date());
        addMatchEndLogs();
        matchView.updateResult(matchModel);
        updateMatch();
        matchView.onMatchEndSuccessfully();
    }

    public void matchStart() {
        String unableToStart = matchModel.unableToStart(matchView.getContext());
        if (unableToStart == null) {
            wasUpdated = true;
            matchModel.setMatchStart(new Date());
            matchView.addLogs(matchModel.getMatchLog(matchView.getContext()));
            matchView.updateResult(matchModel);
            matchView.updatePlayers(matchModel);
            updateMatch();
            matchView.onMatchStartSuccessfully();
        } else {
            matchView.showSnackBar(Snackbar.LENGTH_LONG, unableToStart, null);
        }
    }

    public boolean wasUpdated() {
        return wasUpdated;
    }

    public void onGoalInfoClick() {
        if (matchModel.needTheMachStart()) {
            DialogFragment dialog = NumberPickerDialogFactory.getGoalDialog(matchModel.getMaxGoals());
            dialog.show(matchView.getFragmentManager(), ViewConstants.FragmentTag.GOAL_INFO_DIALOG);
        }
    }

    public void onTimeInfoClick() {
        if (matchModel.needTheMachStart()) {
            DialogFragment dialog = NumberPickerDialogFactory.getTimeDialog(matchModel.getMaxMinutes());
            dialog.show(matchView.getFragmentManager(), ViewConstants.FragmentTag.TIME_INFO_DIALOG);
        }
    }

    @Override
    public void onDialogPositiveClick(String tag, int selectedValue) {
        onDialogInfoClick(tag, selectedValue);
    }

    private void onDialogInfoClick(String tag, int selectedValue) {
        if (matchModel.needTheMachStart()) {
            wasUpdated = true;
            switch (tag) {
                case ViewConstants.FragmentTag.TIME_INFO_DIALOG:
                    matchModel.setMaxMinutes(selectedValue);
                    break;
                case ViewConstants.FragmentTag.GOAL_INFO_DIALOG:
                    matchModel.setMaxGoals(selectedValue);
                    break;
            }
            matchView.updateInfo(matchModel);
            updateMatch();
        }
    }

    private void updateMatch() {
        wasUpdated = true;
        showViewLoading();
        this.postMatchUseCase.setMatch(MatchModelDataMapper.transform(matchModel));
        this.postMatchUseCase.execute(new PostMatchSubscriber());
    }

    private void showViewLoading() {
        this.matchView.showLoading();
    }

    @Override
    public void onDialogNegativeClick(String tag, int selectedValue) {
        onDialogInfoClick(tag, 0);
    }

    @Override
    public void onDialogItemClick(PlayerModel playerModelSource, PlayerModel playerModel) {
        TeamModel teamModel = matchModel.getTeam(playerModelSource, playerModel);
        matchModel.addTeam(teamModel);
        updateMatch();
    }

    @Override
    public void onDialogPositiveClick(PlayerModel playerModelSource) {
        PlayerDialog dialog = new PlayerDialog();
        Bundle args = new Bundle();
        args.putSerializable(ViewConstants.ExtraKeys.PLAYER, playerModelSource);
        dialog.setArguments(args);
        dialog.show(matchView.getFragmentManager(), ViewConstants.FragmentTag.PLAYER_DIALOG);
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.matchView.getContext(),
                errorBundle.getException());
        this.matchView.showError(errorMessage);
    }

    public Context getContext() {
        return matchView.getContext();
    }

    private void updateMatchOnView() {
        this.matchView.updateMatchOnView();
    }

    private void hideViewLoading() {
        this.matchView.hideViewLoading();
    }

    public void sendWatch() {
        if (mApiClient != null) {
            sendPlayers();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mApiClient != null) {
            Wearable.NodeApi.getConnectedNodes(mApiClient)
                    .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                        @Override
                        public void onResult(@NonNull NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                            if (getConnectedNodesResult.getNodes().size() > 0) {
                                showWearableMenu = true;
                                matchView.showWearableMenu();
                            }
                        }
                    });
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent dataEvent : dataEventBuffer) {
            DataItem dataItem = dataEvent.getDataItem();
            if (dataItem.getUri().getPath().equals(Constants.Path.GOAL)) {
                DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                Long idPlayer = dataMap.get(Constants.Key.ID_PLAYER);
                PlayerModel playerModel = matchModel.getPlayerById(idPlayer);
                if (playerModel != null) {
                    score(playerModel);
                }
            }
        }
    }

    public void sendPlayers() {
        sendRequest(generateDataMapRequest(matchModel.getPlayers()));
    }

    @NonNull
    private PutDataMapRequest generateDataMapRequest(List<PlayerModel> players) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(Constants.Path.PLAYERS);
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putLong(Constants.Key.TIME, new Date().getTime());
        dataMap.putLong(Constants.Key.ID_MATCH, matchModel.getId());
        for (PlayerModel player : players) {
            dataMap.putLong(player.getName(), player.getId());
        }
        return putDataMapRequest;
    }

    private void sendRequest(PutDataMapRequest putDataMapRequest) {
        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(mApiClient, request)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                        if (!dataItemResult.getStatus().isSuccess()) {
                            matchView.showError(dataItemResult.getStatus().getStatusMessage());
                        }
                    }
                });
    }

    public boolean needShowWearableMenu() {
        return showWearableMenu;
    }

    private class PostMatchSubscriber extends DefaultSubscriber<Match> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            MatchPresenter.this.hideViewLoading();
            MatchPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(Match match) {
            matchModel = matchModelDataMapper.transform(MatchPresenter.this.getContext(), match);
            MatchPresenter.this.updateMatchOnView();
            MatchPresenter.this.hideViewLoading();
        }
    }

}
