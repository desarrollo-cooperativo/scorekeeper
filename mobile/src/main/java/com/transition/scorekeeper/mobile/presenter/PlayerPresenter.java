package com.transition.scorekeeper.mobile.presenter;

import com.transition.scorekeeper.domain.exception.DefaultErrorBundle;
import com.transition.scorekeeper.domain.interactor.DefaultSubscriber;
import com.transition.scorekeeper.domain.interactor.PutPlayer;
import com.transition.scorekeeper.domain.interactor.UseCase;
import com.transition.scorekeeper.mobile.exception.ErrorMessageFactory;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.model.mapper.PlayerModelDataMapper;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.PlayerView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author diego.rotondale
 * @since 12/06/16
 */
public class PlayerPresenter implements Presenter {
    private final PlayerModelDataMapper playerModelDataMapper;
    private final PutPlayer putPlayerUseCase;
    private PlayerView playerView;
    private PlayerModel playerModel;

    @Inject
    public PlayerPresenter(@Named("putPlayer") UseCase putPlayerUseCase,
                           PlayerModelDataMapper playerModelDataMapper) {
        this.putPlayerUseCase = (PutPlayer) putPlayerUseCase;
        this.playerModelDataMapper = playerModelDataMapper;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.playerView = null;
    }

    public void createPlayer(String name) {
        playerModel = new PlayerModel();
        playerModel.setName(name);
        this.putPlayerUseCase.setPlayer(PlayerModelDataMapper.transform(playerModel));
        this.putPlayerUseCase.execute(new PutPlayerSubscriber());
    }

    public void setView(PlayerView view) {
        this.playerView = view;
    }

    private void hideViewLoading() {
        this.playerView.hideLoading();
    }

    private void showViewRetry() {
        this.playerView.showRetry();
    }

    private void onPlayerCreated(Long id) {
        playerModel.setId(id);
        this.playerView.onPlayerCreated(playerModel);
    }

    private void showErrorMessage(DefaultErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.playerView.context(),
                errorBundle.getException());
        this.playerView.showError(errorMessage);
    }

    private class PutPlayerSubscriber extends DefaultSubscriber<Long> {
        @Override
        public void onCompleted() {
            PlayerPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PlayerPresenter.this.hideViewLoading();
            PlayerPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            PlayerPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Long id) {
            PlayerPresenter.this.onPlayerCreated(id);
        }
    }
}
