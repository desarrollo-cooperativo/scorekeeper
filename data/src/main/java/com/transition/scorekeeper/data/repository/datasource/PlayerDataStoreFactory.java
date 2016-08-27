package com.transition.scorekeeper.data.repository.datasource;

import android.content.Context;

import com.transition.scorekeeper.data.cache.PlayerCache;
import com.transition.scorekeeper.data.entity.mapper.json.PlayerEntityJsonMapper;
import com.transition.scorekeeper.data.net.RestApiPlayer;
import com.transition.scorekeeper.data.net.RestApiPlayerImpl;
import com.transition.scorekeeper.data.repository.datasource.cloud.CloudPlayerDataStore;
import com.transition.scorekeeper.data.repository.datasource.local.DiskPlayerDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayerDataStoreFactory {
    private final Context context;
    private final PlayerCache playerCache;

    @Inject
    public PlayerDataStoreFactory(Context context, PlayerCache playerCache) {
        if (context == null || playerCache == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.playerCache = playerCache;
    }

    public PlayerDataStore createCloudDataStore() {
        PlayerEntityJsonMapper playerEntityJsonMapper = new PlayerEntityJsonMapper();
        RestApiPlayer restApi = new RestApiPlayerImpl(this.context, playerEntityJsonMapper);
        return new CloudPlayerDataStore(restApi, this.playerCache);
    }

    public PlayerDataStore createDiskDataStore() {
        return new DiskPlayerDataStore(this.playerCache);
    }
}
