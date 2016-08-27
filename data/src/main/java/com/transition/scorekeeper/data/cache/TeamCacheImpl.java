package com.transition.scorekeeper.data.cache;

import android.content.Context;

import com.transition.scorekeeper.data.cache.database.provider.TeamProvider;
import com.transition.scorekeeper.data.entity.TeamEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 15/06/16
 */
@Singleton
public class TeamCacheImpl extends CacheImpl implements TeamCache {
    private final TeamProvider provider;

    @Inject
    public TeamCacheImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.provider = new TeamProvider();
    }

    @Override
    public Observable<Long> insert(TeamEntity teamEntity) {
        return Observable.create(subscriber -> {
            subscriber.onNext(provider.saveOrUpdate(teamEntity));
            subscriber.onCompleted();
        });
    }
}
