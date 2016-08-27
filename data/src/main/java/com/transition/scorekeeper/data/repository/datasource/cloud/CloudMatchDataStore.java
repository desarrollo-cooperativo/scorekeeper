package com.transition.scorekeeper.data.repository.datasource.cloud;

import com.transition.scorekeeper.data.entity.MatchEntity;
import com.transition.scorekeeper.data.net.RestApiMatch;
import com.transition.scorekeeper.data.repository.datasource.MatchDataStore;

import java.util.List;

import rx.Observable;

public class CloudMatchDataStore implements MatchDataStore {
    private final RestApiMatch restApi;

    public CloudMatchDataStore(RestApiMatch restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<List<MatchEntity>> matchEntityList() {
        return this.restApi.matchesEntities();
    }

    @Override
    public Observable<Long> insert(MatchEntity matchEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Observable<MatchEntity> update(MatchEntity matchEntity) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
