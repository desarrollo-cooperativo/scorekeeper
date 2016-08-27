package com.transition.scorekeeper.data.repository.datasource;

import android.content.Context;

import com.transition.scorekeeper.data.cache.TeamCache;
import com.transition.scorekeeper.data.repository.datasource.local.DiskTeamDataStore;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author diego.rotondale
 * @since 15/06/16
 */
@Singleton
public class TeamDataStoreFactory {
    private final Context context;
    private final TeamCache teamCache;

    @Inject
    public TeamDataStoreFactory(Context context, TeamCache teamCache) {
        if (context == null || teamCache == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.teamCache = teamCache;
    }

    public TeamDataStore createDiskDataStore() {
        return new DiskTeamDataStore(this.teamCache);
    }
}
