package com.transition.scorekeeper.mobile.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.internal.di.components.MatchComponent;
import com.transition.scorekeeper.mobile.model.LogModel;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.presenter.MatchPresenter;
import com.transition.scorekeeper.mobile.view.activity.interfaces.INeedUpdate;
import com.transition.scorekeeper.mobile.view.adapter.MatchLogAdapter;
import com.transition.scorekeeper.mobile.view.component.MatchHeaderView;
import com.transition.scorekeeper.mobile.view.component.MatchStatusView;
import com.transition.scorekeeper.mobile.view.component.MatchTeamPlayersView;
import com.transition.scorekeeper.mobile.view.component.common.RecyclerViewWithEmptyView;
import com.transition.scorekeeper.mobile.view.fragment.dialog.NumberPickerDialog;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayersDialog;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.MatchView;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class MatchFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MatchTeamPlayersView.IOnPlayerClick,
        MatchView, MatchStatusView.OnMatchFinishListener, INeedUpdate,
        MatchHeaderView.OnGoalInfoListener, MatchHeaderView.OnTimeInfoListener,
        NumberPickerDialog.INumberPickerDialogListener, PlayersDialog.IPlayersDialogDialogListener {
    @Inject
    protected MatchPresenter matchPresenter;
    @Bind(R.id.match_swipe_refresh_layout)
    protected SwipeRefreshLayout swipe;
    @Bind(R.id.match_header)
    protected MatchHeaderView matchHeader;
    @Bind(R.id.team_left)
    protected MatchTeamPlayersView leftTeam;
    @Bind(R.id.team_right)
    protected MatchTeamPlayersView rightTeam;
    @Bind(R.id.match_progress_bar)
    protected View matchProgress;
    @Bind(R.id.match_info_container)
    protected View matchContainer;

    @Bind(R.id.match_log_list)
    protected RecyclerViewWithEmptyView rv;
    protected MatchLogAdapter adapter;
    private Menu menu;

    public MatchFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MatchComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initializeSwipeView();
        initializeMatch();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.matchPresenter.setView(this);
    }

    private void initializeMatch() {
        MatchModel matchModel = getMatch();
        if (matchModel != null) {
            leftTeam.setData(matchModel.getLeftTeam(), matchModel.needTheMachStart());
            rightTeam.setData(matchModel.getRightTeam(), matchModel.needTheMachStart());
            leftTeam.setOnPlayerClickListener(this);
            rightTeam.setOnPlayerClickListener(this);
            matchHeader.setData(matchModel);
            matchHeader.setOnMatchFinishListener(this);
            matchHeader.setOnGoalInfoListener(this);
            matchHeader.setOnTimeInfoListener(this);
            initializeList(matchModel);
        } else {
            getActivity().finish();
        }
    }

    private void initializeList(MatchModel matchLog) {
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MatchLogAdapter(getContext());
        rv.addOnItemTouchListener(adapter.getListener());
        Long rightTeamId = matchLog.getRightTeamId();
        if (rightTeamId != null) {
            adapter.initializeList(matchLog.getMatchLog(getContext()), rightTeamId);
        }
        rv.setAdapter(adapter);
    }

    public MatchModel getMatch() {
        return matchPresenter.getMatchModel((MatchModel) getActivity().getIntent().getSerializableExtra(ViewConstants.ExtraKeys.MATCH));
    }

    private void initializeSwipeView() {
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(R.color.primary_dark, R.color.primary,
                R.color.primary_light, R.color.primary_dark);
        swipe.setEnabled(false);
    }

    @Override
    public boolean canDoBack() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_match;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_match:
                onMatchStart();
                return true;
            case R.id.action_end_match:
                matchPresenter.matchEnd();
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.action_send_watch:
                matchPresenter.sendWatch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onMatchStart() {
        matchPresenter.matchStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (getMatch().needTheMachStart()) {
            inflater.inflate(R.menu.match_to_play, menu);
        }
        if (getMatch().isTheMatchInProgress()) {
            inflater.inflate(R.menu.match_in_progress, menu);
        }
        this.menu = menu;
        if (matchPresenter != null && matchPresenter.needShowWearableMenu()) {
            showWearableMenu();
        }
    }

    @Override
    public void onPlayerClick(PlayerModel playerModel) {
        this.matchPresenter.onPlayerClick(playerModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.matchPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.matchPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.matchPresenter.destroy();
    }

    @Override
    public void showSnackBar(int length, String message, View.OnClickListener onUndoListener) {
        Snackbar snackbar = Snackbar
                .make(swipe, message, length)
                .setAction(getString(R.string.undo), onUndoListener);
        snackbar.show();
    }

    @Override
    public void toast(int length, String message) {
        Toast.makeText(getContext(), message, length).show();
    }

    @Override
    public void addLog(LogModel matchLog) {
        adapter.add(matchLog);
    }


    @Override
    public void addLogs(List<LogModel> matchLogs) {
        adapter.addAll(matchLogs);
    }

    @Override
    public void updateResult(MatchModel matchModel) {
        matchHeader.updateResult(matchModel);
    }

    @Override
    public void updatePlayers(MatchModel matchModel) {
        leftTeam.updateData(matchModel.getLeftTeam(), matchModel.needTheMachStart());
        rightTeam.updateData(matchModel.getRightTeam(), matchModel.needTheMachStart());
    }

    @Override
    public void onMatchEndSuccessfully() {
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onMatchStartSuccessfully() {
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void updateInfo(MatchModel matchModel) {
        matchHeader.updateInfo(matchModel);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideViewLoading() {
        matchProgress.setVisibility(View.GONE);
        matchContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        matchProgress.setVisibility(View.VISIBLE);
        matchContainer.setVisibility(View.GONE);
    }

    @Override
    public void updateMatchOnView() {
        initializeMatch();
    }

    @Override
    public void showWearableMenu() {
        if (menu != null) {
            menu.setGroupVisible(R.id.wearable_group, true);
        }
    }

    @Override
    public void matchEndByTime() {
        if (isAdded()) {
            onMatchEndSuccessfully();
            matchPresenter.matchFinish();
        }
    }

    @Override
    public boolean wasUpdated() {
        return matchPresenter.wasUpdated();
    }

    @Override
    public Serializable getUpdatedObject() {
        return getMatch();
    }

    @Override
    public void onGoalInfoClick() {
        matchPresenter.onGoalInfoClick();
    }

    @Override
    public void onTimeInfoClick() {
        matchPresenter.onTimeInfoClick();
    }

    @Override
    public void onDialogPositiveClick(String tag, int selectedValue) {
        matchPresenter.onDialogPositiveClick(tag, selectedValue);
    }

    @Override
    public void onDialogNegativeClick(String tag, int selectedValue) {
        matchPresenter.onDialogNegativeClick(tag, selectedValue);
    }

    @Override
    public void onDialogItemClick(PlayerModel playerModelSource, PlayerModel playerModel) {
        matchPresenter.onDialogItemClick(playerModelSource, playerModel);
    }

    @Override
    public void onDialogPositiveClick(PlayerModel playerModelSource) {
        matchPresenter.onDialogPositiveClick(playerModelSource);
    }
}
