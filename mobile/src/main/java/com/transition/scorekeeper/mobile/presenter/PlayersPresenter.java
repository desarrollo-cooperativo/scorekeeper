package com.transition.scorekeeper.mobile.presenter;

import android.support.annotation.NonNull;

import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.exception.DefaultErrorBundle;
import com.transition.scorekeeper.domain.interactor.DefaultSubscriber;
import com.transition.scorekeeper.domain.interactor.UseCase;
import com.transition.scorekeeper.mobile.exception.ErrorMessageFactory;
import com.transition.scorekeeper.mobile.internal.di.PerActivity;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.model.mapper.PlayerModelDataMapper;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.PlayersView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author diego.rotondale
 * @since 29/05/16
 */
@PerActivity
public class PlayersPresenter implements Presenter {
    private final UseCase getPlayersUseCase;
    private final PlayerModelDataMapper playerModelDataMapper;
    private PlayersView playersView;

    @Inject
    public PlayersPresenter(@Named("players") UseCase getPlayersUseCase,
                            PlayerModelDataMapper playerModelDataMapper) {
        this.getPlayersUseCase = getPlayersUseCase;
        this.playerModelDataMapper = playerModelDataMapper;
    }

    public void setView(@NonNull PlayersView view) {
        this.playersView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getPlayersUseCase.unSubscribe();
        this.playersView = null;
    }


    public void initialize() {
        this.loadPlayers();
    }

    private void loadPlayers() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getPlayers();
    }

    private void showPlayersCollectionInView(List<Player> playersCollection) {
        final Collection<PlayerModel> playerModelsCollection =
                this.playerModelDataMapper.transform(playersCollection);
        this.playersView.renderPlayers(playerModelsCollection);
    }

    private void showViewLoading() {
        this.playersView.showLoading();
    }

    private void hideViewLoading() {
        this.playersView.hideLoading();
    }

    private void showViewRetry() {
        this.playersView.showRetry();
    }

    private void hideViewRetry() {
        this.playersView.hideRetry();
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.playersView.context(),
                errorBundle.getException());
        this.playersView.showError(errorMessage);
    }

    public void getPlayers() {
        this.getPlayersUseCase.execute(new PlayersSubscriber());
    }

    private final class PlayersSubscriber extends DefaultSubscriber<List<Player>> {

        @Override
        public void onCompleted() {
            PlayersPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PlayersPresenter.this.hideViewLoading();
            PlayersPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PlayersPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Player> players) {
            PlayersPresenter.this.showPlayersCollectionInView(players);
        }
    }
}
