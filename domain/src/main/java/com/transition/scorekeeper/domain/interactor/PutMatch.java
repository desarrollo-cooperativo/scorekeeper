package com.transition.scorekeeper.domain.interactor;

import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.repository.MatchRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 11/06/16
 */
public class PutMatch extends UseCase {
    private final MatchRepository matchRepository;
    private Match match;

    @Inject
    public PutMatch(MatchRepository matchRepository, ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.matchRepository = matchRepository;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.matchRepository.putMatch(this.match);
    }
}
