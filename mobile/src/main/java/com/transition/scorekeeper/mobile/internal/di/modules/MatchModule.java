package com.transition.scorekeeper.mobile.internal.di.modules;

import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.interactor.GetMatches;
import com.transition.scorekeeper.domain.interactor.GetPlayers;
import com.transition.scorekeeper.domain.interactor.PostMatch;
import com.transition.scorekeeper.domain.interactor.PutMatch;
import com.transition.scorekeeper.domain.interactor.PutPlayer;
import com.transition.scorekeeper.domain.interactor.UseCase;
import com.transition.scorekeeper.domain.repository.MatchRepository;
import com.transition.scorekeeper.domain.repository.PlayerRepository;
import com.transition.scorekeeper.mobile.internal.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
@Module
public class MatchModule {
    public MatchModule() {
    }

    @Provides
    @PerActivity
    @Named("matches")
    UseCase provideGetMatchesUseCase(GetMatches getMatches) {
        return getMatches;
    }

    @Provides
    @PerActivity
    @Named("putMatch")
    UseCase providePutMatchUseCase(
            MatchRepository matchRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new PutMatch(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("postMatch")
    UseCase providePostMatchUseCase(
            MatchRepository matchRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new PostMatch(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("players")
    UseCase provideGetPlayersUseCase(GetPlayers getPlayers) {
        return getPlayers;
    }

    @Provides
    @PerActivity
    @Named("putPlayer")
    UseCase providePutPlayerUseCase(
            PlayerRepository playerRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new PutPlayer(playerRepository, threadExecutor, postExecutionThread);
    }
}

