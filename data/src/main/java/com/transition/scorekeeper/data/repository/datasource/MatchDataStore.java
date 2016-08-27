package com.transition.scorekeeper.data.repository.datasource;

import com.transition.scorekeeper.data.entity.MatchEntity;

import java.util.List;

import rx.Observable;

public interface MatchDataStore {
    Observable<List<MatchEntity>> matchEntityList();

    Observable<Long> insert(MatchEntity matchEntity);

    Observable<MatchEntity> update(MatchEntity matchEntity);
}