package com.transition.scorekeeper.data.repository.datasource.local;

import com.transition.scorekeeper.data.cache.PlayerCache;
import com.transition.scorekeeper.data.entity.PlayerEntity;
import com.transition.scorekeeper.data.repository.datasource.PlayerDataStore;

import java.util.List;

import rx.Observable;

public class DiskPlayerDataStore implements PlayerDataStore {

    private final PlayerCache playerCache;

    public DiskPlayerDataStore(PlayerCache playerCache) {
        this.playerCache = playerCache;
    }

    @Override
    public Observable<List<PlayerEntity>> playerEntityList() {
        return playerCache.get();
    }

    @Override
    public Observable<Long> insert(PlayerEntity playerEntity) {
        return playerCache.insert(playerEntity);
    }
}
