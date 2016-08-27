package com.transition.scorekeeper.mobile.internal.di.modules;

import android.content.Context;

import com.transition.scorekeeper.data.cache.MatchCache;
import com.transition.scorekeeper.data.cache.MatchCacheImpl;
import com.transition.scorekeeper.data.cache.PlayerCache;
import com.transition.scorekeeper.data.cache.PlayerCacheImpl;
import com.transition.scorekeeper.data.cache.TeamCache;
import com.transition.scorekeeper.data.cache.TeamCacheImpl;
import com.transition.scorekeeper.data.executor.JobExecutor;
import com.transition.scorekeeper.data.repository.MatchDataRepository;
import com.transition.scorekeeper.data.repository.PlayerDataRepository;
import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.repository.MatchRepository;
import com.transition.scorekeeper.domain.repository.PlayerRepository;
import com.transition.scorekeeper.mobile.App;
import com.transition.scorekeeper.mobile.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    MatchCache provideMatchCache(MatchCacheImpl matchCache) {
        return matchCache;
    }

    @Provides
    @Singleton
    MatchRepository provideMatchRepository(MatchDataRepository matchDataRepository) {
        return matchDataRepository;
    }

    @Provides
    @Singleton
    PlayerCache providePlayerCache(PlayerCacheImpl playerCache) {
        return playerCache;
    }

    @Provides
    @Singleton
    PlayerRepository providePlayerRepository(PlayerDataRepository playerDataRepository) {
        return playerDataRepository;
    }

    @Provides
    @Singleton
    TeamCache provideTeamCache(TeamCacheImpl teamCache) {
        return teamCache;
    }
}
