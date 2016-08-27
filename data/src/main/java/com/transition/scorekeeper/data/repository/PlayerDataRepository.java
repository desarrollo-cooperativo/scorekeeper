package com.transition.scorekeeper.data.repository;

import com.transition.scorekeeper.data.entity.mapper.PlayerEntityDataMapper;
import com.transition.scorekeeper.data.repository.datasource.PlayerDataStore;
import com.transition.scorekeeper.data.repository.datasource.PlayerDataStoreFactory;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.repository.PlayerRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class PlayerDataRepository implements PlayerRepository {
    private final PlayerDataStoreFactory playerDataStoreFactory;
    private final PlayerEntityDataMapper playerEntityDataMapper;

    @Inject
    public PlayerDataRepository(PlayerDataStoreFactory playerDataStoreFactory,
                                PlayerEntityDataMapper playerEntityDataMapper) {
        this.playerDataStoreFactory = playerDataStoreFactory;
        this.playerEntityDataMapper = playerEntityDataMapper;
    }

    @Override
    public Observable<List<Player>> players() {
        final PlayerDataStore playerDataStore = this.playerDataStoreFactory.createDiskDataStore();
        return playerDataStore.playerEntityList()
                .map(this.playerEntityDataMapper::transform);
    }

    @Override
    public Observable<Long> putPlayer(Player player) {
        final PlayerDataStore playerDataStore = this.playerDataStoreFactory.createDiskDataStore();
        return playerDataStore.insert(PlayerEntityDataMapper.transform(player));
    }
}
