package com.transition.scorekeeper.domain.interactor;

import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.repository.PlayerRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 29/05/16
 */
public class GetPlayers extends UseCase {
    private final PlayerRepository playerRepository;

    @Inject
    protected GetPlayers(PlayerRepository playerRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.playerRepository = playerRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.playerRepository.players();
    }
}
