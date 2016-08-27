package com.transition.scorekeeper.data.repository.datasource.cloud;

import com.transition.scorekeeper.data.cache.PlayerCache;
import com.transition.scorekeeper.data.entity.PlayerEntity;
import com.transition.scorekeeper.data.net.RestApiPlayer;
import com.transition.scorekeeper.data.repository.datasource.PlayerDataStore;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class CloudPlayerDataStore implements PlayerDataStore {

    private final RestApiPlayer restApi;
    private final PlayerCache playerCache;

    private final Action1<PlayerEntity> saveToCacheAction = playerEntity -> {
        if (playerEntity != null) {
            CloudPlayerDataStore.this.playerCache.insert(playerEntity);
        }
    };

    public CloudPlayerDataStore(RestApiPlayer restApi, PlayerCache playerCache) {
        this.restApi = restApi;
        this.playerCache = playerCache;
    }

    @Override
    public Observable<List<PlayerEntity>> playerEntityList() {
        return this.restApi.playersEntities();
    }

    @Override
    public Observable<Long> insert(PlayerEntity playerEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
