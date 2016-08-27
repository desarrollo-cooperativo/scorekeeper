package com.transition.scorekeeper.mobile.presenter;

import android.support.annotation.NonNull;

import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.exception.DefaultErrorBundle;
import com.transition.scorekeeper.domain.exception.ErrorBundle;
import com.transition.scorekeeper.domain.interactor.DefaultSubscriber;
import com.transition.scorekeeper.domain.interactor.UseCase;
import com.transition.scorekeeper.mobile.exception.ErrorMessageFactory;
import com.transition.scorekeeper.mobile.internal.di.PerActivity;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.model.mapper.MatchModelDataMapper;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.MatchesView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class MatchesPresenter implements Presenter {
    private final UseCase getMatchesUseCase;
    private final UseCase putMatchUseCase;
    private final MatchModelDataMapper matchModelDataMapper;
    private MatchesView matchesView;

    @Inject
    public MatchesPresenter(@Named("matches") UseCase getMatchesUserCase,
                            @Named("putMatch") UseCase putMatchUserCase,
                            MatchModelDataMapper matchModelDataMapper) {
        this.getMatchesUseCase = getMatchesUserCase;
        this.putMatchUseCase = putMatchUserCase;
        this.matchModelDataMapper = matchModelDataMapper;
    }

    public void setView(@NonNull MatchesView view) {
        this.matchesView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getMatchesUseCase.unSubscribe();
        this.matchesView = null;
    }

    public void initialize() {
        this.loadMatches();
    }

    private void loadMatches() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getMatches();
    }

    public void onMatchClicked(MatchModel matchModel) {
        if (matchModel == null) {
            this.putMatchUseCase.execute(new PutMatchSubscriber());
        } else {
            this.matchesView.viewMatch(matchModel);
        }
    }

    private void showViewLoading() {
        this.matchesView.showLoading();
    }

    private void hideViewLoading() {
        this.matchesView.hideLoading();
    }

    private void showViewRetry() {
        this.matchesView.showRetry();
    }

    private void hideViewRetry() {
        this.matchesView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.matchesView.context(),
                errorBundle.getException());
        this.matchesView.showError(errorMessage);
    }

    private void showMatchesCollectionInView(Collection<Match> matchesCollection) {
        final Collection<MatchModel> matchModelsCollection =
                this.matchModelDataMapper.transform(matchesView.context(), matchesCollection);
        this.matchesView.renderMatches(matchModelsCollection);
    }

    private void getMatches() {
        this.getMatchesUseCase.execute(new MatchesSubscriber());
    }

    private void onMatchCreated(Long id) {
        MatchModel matchModel = new MatchModel();
        matchModel.setId(id);
        matchesView.createMatch(matchModel);
        MatchesPresenter.this.matchesView.viewMatch(matchModel);
    }

    private final class MatchesSubscriber extends DefaultSubscriber<List<Match>> {

        @Override
        public void onCompleted() {
            MatchesPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            MatchesPresenter.this.hideViewLoading();
            MatchesPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MatchesPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Match> matches) {
            MatchesPresenter.this.showMatchesCollectionInView(matches);
        }
    }

    private class PutMatchSubscriber extends DefaultSubscriber<Long> {
        @Override
        public void onCompleted() {
            MatchesPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            MatchesPresenter.this.hideViewLoading();
            MatchesPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            MatchesPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Long id) {
            MatchesPresenter.this.onMatchCreated(id);
        }
    }
}
