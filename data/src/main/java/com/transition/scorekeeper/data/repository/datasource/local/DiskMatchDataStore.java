package com.transition.scorekeeper.data.repository.datasource.local;

import com.transition.scorekeeper.data.cache.MatchCache;
import com.transition.scorekeeper.data.entity.MatchEntity;
import com.transition.scorekeeper.data.repository.datasource.MatchDataStore;

import java.util.List;

import rx.Observable;

public class DiskMatchDataStore implements MatchDataStore {

    private final MatchCache matchCache;

    public DiskMatchDataStore(MatchCache matchCache) {
        this.matchCache = matchCache;
    }

    @Override
    public Observable<List<MatchEntity>> matchEntityList() {
        return matchCache.get();
    }

    @Override
    public Observable<Long> insert(MatchEntity matchEntity) {
        return matchCache.insert(matchEntity);
    }

    @Override
    public Observable<MatchEntity> update(MatchEntity matchEntity) {
        return matchCache.update(matchEntity);
    }
}
