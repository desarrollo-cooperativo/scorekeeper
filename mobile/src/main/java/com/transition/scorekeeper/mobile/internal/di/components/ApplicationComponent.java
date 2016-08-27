package com.transition.scorekeeper.mobile.internal.di.components;

import android.content.Context;

import com.transition.scorekeeper.domain.executor.PostExecutionThread;
import com.transition.scorekeeper.domain.executor.ThreadExecutor;
import com.transition.scorekeeper.domain.repository.MatchRepository;
import com.transition.scorekeeper.domain.repository.PlayerRepository;
import com.transition.scorekeeper.mobile.internal.di.modules.ApplicationModule;
import com.transition.scorekeeper.mobile.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    MatchRepository matchRepository();
    PlayerRepository playerRepository();
}
