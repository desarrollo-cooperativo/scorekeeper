package com.transition.scorekeeper.domain.interactor;

import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.repository.PlayerRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 12/06/16
 */
public class PutPlayer extends UseCase {
    private final PlayerRepository playerRepository;
    private Player player;

    @Inject
    public PutPlayer(PlayerRepository playerRepository, ThreadExecutor threadExecutor,
                     PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.playerRepository = playerRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.playerRepository.putPlayer(this.player);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
