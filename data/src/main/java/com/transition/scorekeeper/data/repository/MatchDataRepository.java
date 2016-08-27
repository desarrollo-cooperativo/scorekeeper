package com.transition.scorekeeper.data.repository;

import com.transition.scorekeeper.data.entity.mapper.MatchEntityDataMapper;
import com.transition.scorekeeper.data.repository.datasource.MatchDataStore;
import com.transition.scorekeeper.data.repository.datasource.MatchDataStoreFactory;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.repository.MatchRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class MatchDataRepository implements MatchRepository {
    private final MatchDataStoreFactory matchDataStoreFactory;
    private final MatchEntityDataMapper matchEntityDataMapper;

    @Inject
    public MatchDataRepository(MatchDataStoreFactory matchDataStoreFactory,
                               MatchEntityDataMapper matchEntityDataMapper) {
        this.matchDataStoreFactory = matchDataStoreFactory;
        this.matchEntityDataMapper = matchEntityDataMapper;
    }

    @Override
    public Observable<List<Match>> matches() {
        final MatchDataStore matchDataStore = this.matchDataStoreFactory.createDiskDataStore();
        return matchDataStore.matchEntityList()
                .map(this.matchEntityDataMapper::transform);
    }

    @Override
    public Observable<Long> putMatch(Match match) {
        final MatchDataStore matchDataStore = this.matchDataStoreFactory.createDiskDataStore();
        return matchDataStore.insert(matchEntityDataMapper.transform(match));
    }

    @Override
    public Observable<Match> postMatch(Match match) {
        final MatchDataStore matchDataStore = this.matchDataStoreFactory.createDiskDataStore();
        return matchDataStore.update(matchEntityDataMapper.transform(match))
                .map(this.matchEntityDataMapper::transform);
    }
}
