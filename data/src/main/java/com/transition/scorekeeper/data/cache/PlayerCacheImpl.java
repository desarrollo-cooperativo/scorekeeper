package com.transition.scorekeeper.data.cache;

import android.content.Context;

import com.transition.scorekeeper.data.cache.database.provider.PlayerProvider;
import com.transition.scorekeeper.data.entity.PlayerEntity;
import com.transition.scorekeeper.data.exception.MatchNotFoundException;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PlayerCacheImpl extends CacheImpl implements PlayerCache {
    private final PlayerProvider provider;

    @Inject
    public PlayerCacheImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.provider = new PlayerProvider();
    }

    @Override
    public Observable<PlayerEntity> get(final Long playerId) {
        return Observable.create(subscriber -> {
            PlayerEntity playerEntity = provider.getPlayer(playerId);
            if (playerEntity != null) {
                subscriber.onNext(playerEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new MatchNotFoundException());
            }
        });
    }

    @Override
    public Observable<Long> insert(PlayerEntity playerEntity) {
        return Observable.create(subscriber -> {
            subscriber.onNext(provider.saveOrUpdate(playerEntity));
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<List<PlayerEntity>> get() {
        return Observable.create(subscriber -> {
            subscriber.onNext(provider.getPlayers());
            subscriber.onCompleted();
        });
    }
}
