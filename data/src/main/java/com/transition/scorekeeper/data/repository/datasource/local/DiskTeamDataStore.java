package com.transition.scorekeeper.data.repository.datasource.local;

import com.transition.scorekeeper.data.cache.TeamCache;
import com.transition.scorekeeper.data.entity.TeamEntity;
import com.transition.scorekeeper.data.repository.datasource.TeamDataStore;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 15/06/16
 */
public class DiskTeamDataStore implements TeamDataStore {
    private TeamCache teamCache;

    public DiskTeamDataStore(TeamCache teamCache) {
        this.teamCache = teamCache;
    }

    @Override
    public Observable<Long> insert(TeamEntity teamEntity) {
        return teamCache.insert(teamEntity);
    }
}
