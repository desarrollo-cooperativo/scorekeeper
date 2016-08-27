package com.transition.scorekeeper.data.cache;

import com.transition.scorekeeper.data.entity.TeamEntity;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 15/06/16
 */
public interface TeamCache extends BaseCache {
    Observable<Long> insert(TeamEntity teamEntity);
}
