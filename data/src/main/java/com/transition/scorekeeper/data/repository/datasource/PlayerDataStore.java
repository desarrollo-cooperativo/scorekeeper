package com.transition.scorekeeper.data.repository.datasource;

import com.transition.scorekeeper.data.entity.PlayerEntity;

import java.util.List;

import rx.Observable;

public interface PlayerDataStore {
    Observable<List<PlayerEntity>> playerEntityList();

    Observable<Long> insert(PlayerEntity playerEntity);
}