package com.transition.scorekeeper.domain.interactor;

import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.repository.MatchRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public class GetMatches extends UseCase {
    private final MatchRepository matchRepository;

    @Inject
    protected GetMatches(MatchRepository matchRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.matchRepository = matchRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.matchRepository.matches();
    }
}
