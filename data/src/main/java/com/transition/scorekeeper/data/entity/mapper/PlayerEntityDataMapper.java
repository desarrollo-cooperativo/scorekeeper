package com.transition.scorekeeper.data.entity.mapper;

import com.transition.scorekeeper.data.entity.PlayerEntity;
import com.transition.scorekeeper.domain.domain.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayerEntityDataMapper {

    @Inject
    public PlayerEntityDataMapper() {
    }

    public static HashSet<Player> transform(HashSet<PlayerEntity> playersEntityCollection) {
        HashSet<Player> players = new HashSet<>();
        Player player;
        for (PlayerEntity playerEntity : playersEntityCollection) {
            player = transform(playerEntity);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }

    public static Player transform(PlayerEntity playerEntity) {
        Player player = null;
        if (playerEntity != null) {
            player = new Player();
            player.setId(playerEntity.getId());
            player.setName(playerEntity.getName());
            player.setPreferentialPosition(playerEntity.getPreferentialPosition());
        }
        return player;
    }

    public static PlayerEntity transform(Player player) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(player.getId());
        playerEntity.setName(player.getName());
        playerEntity.setPreferentialPosition(player.getPreferentialPosition());
        return playerEntity;
    }

    public static HashSet<PlayerEntity> transformFromPlayer(HashSet<Player> players) {
        HashSet<PlayerEntity> playerEntities = new HashSet<>();
        for (Player player : players) {
            playerEntities.add(transform(player));
        }
        return playerEntities;
    }

    public List<Player> transform(List<PlayerEntity> playerEntityCollection) {
        List<Player> players = new ArrayList<>();
        Player player;
        for (PlayerEntity playerEntity : playerEntityCollection) {
            player = transform(playerEntity);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }
}
