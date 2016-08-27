package com.transition.scorekeeper.data.cache;

import com.transition.scorekeeper.data.entity.PlayerEntity;

import java.util.List;

import rx.Observable;

public interface PlayerCache extends BaseCache {
    Observable<PlayerEntity> get(final Long playerId);

    Observable<Long> insert(PlayerEntity playerEntity);

    Observable<List<PlayerEntity>> get();
}
