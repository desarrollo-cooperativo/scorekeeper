package com.transition.scorekeeper.mobile;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.transition.scorekeeper.BuildConfig;
import com.transition.scorekeeper.mobile.internal.di.components.ApplicationComponent;
import com.transition.scorekeeper.mobile.internal.di.components.DaggerApplicationComponent;
import com.transition.scorekeeper.mobile.internal.di.modules.ApplicationModule;

import io.fabric.sdk.android.Fabric;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class App extends MultiDexApplication {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeCrashlytics();
        this.initializeInjector();
        this.initializeStetho();
    }

    private void initializeCrashlytics() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initializeStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
