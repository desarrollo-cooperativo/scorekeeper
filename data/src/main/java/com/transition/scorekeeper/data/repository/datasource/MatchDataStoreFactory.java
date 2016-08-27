package com.transition.scorekeeper.data.repository.datasource;

import android.content.Context;

import com.transition.scorekeeper.data.cache.MatchCache;
import com.transition.scorekeeper.data.entity.mapper.json.MatchEntityJsonMapper;
import com.transition.scorekeeper.data.net.RestApiMatch;
import com.transition.scorekeeper.data.net.RestApiMatchImpl;
import com.transition.scorekeeper.data.repository.datasource.cloud.CloudMatchDataStore;
import com.transition.scorekeeper.data.repository.datasource.local.DiskMatchDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MatchDataStoreFactory {

    private final Context context;
    private final MatchCache matchCache;

    @Inject
    public MatchDataStoreFactory(Context context, MatchCache matchCache) {
        if (context == null || matchCache == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.matchCache = matchCache;
    }

    public MatchDataStore createCloudDataStore() {
        MatchEntityJsonMapper matchEntityJsonMapper = new MatchEntityJsonMapper();
        RestApiMatch restApi = new RestApiMatchImpl(this.context, matchEntityJsonMapper);
        return new CloudMatchDataStore(restApi);
    }

    public MatchDataStore createDiskDataStore() {
        return new DiskMatchDataStore(this.matchCache);
    }
}
