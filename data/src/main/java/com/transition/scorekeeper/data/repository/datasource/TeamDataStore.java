package com.transition.scorekeeper.data.repository.datasource;

import com.transition.scorekeeper.data.entity.TeamEntity;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 15/06/16
 */
public interface TeamDataStore {
    Observable<Long> insert(TeamEntity teamEntity);
}
