package com.transition.scorekeeper.data.cache;

import com.transition.scorekeeper.data.entity.MatchEntity;

import java.util.List;

import rx.Observable;

public interface MatchCache extends BaseCache {
    Observable<List<MatchEntity>> get();

    Observable<MatchEntity> get(final Long matchId);

    Observable<Long> insert(MatchEntity matchEntity);

    Observable<MatchEntity> update(MatchEntity matchEntity);
}
