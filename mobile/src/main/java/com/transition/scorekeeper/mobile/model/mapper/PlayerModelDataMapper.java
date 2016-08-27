package com.transition.scorekeeper.mobile.model.mapper;

import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.domain.Team;
import com.transition.scorekeeper.mobile.model.PlayerModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

/**
 * @author diego.rotondale
 * @since 23/05/16
 */
public class PlayerModelDataMapper {
    private static final int MAX_PLAYERS = 2;

    @Inject
    public PlayerModelDataMapper() {
    }

    public static List<PlayerModel> transform(Team team) {
        List<PlayerModel> playerModels = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>(team.getPlayers());
        for (int position = 0; position < MAX_PLAYERS; position++) {
            PlayerModel playerModel;
            if (players.size() > position) {
                playerModel = transform(players.get(position), position, team.getId());
            } else {
                playerModel = transform(null, position, team.getId());
            }
            playerModels.add(playerModel);
        }
        return playerModels;
    }

    private static PlayerModel transform(Player player, int positionOnView, Long teamId) {
        PlayerModel playerModel = new PlayerModel();
        if (player != null) {
            playerModel.setId(player.getId());
            playerModel.setName(player.getName());
        }
        playerModel.setTeamInfo(teamId, positionOnView);
        return playerModel;
    }

    public static PlayerModel transform(Match match, Player player) {
        Team team = match.getTeam(player);
        List<PlayerModel> players = transform(team);
        for (PlayerModel playerModel : players) {
            if (playerModel.getId() != null && player.getId().equals(playerModel.getId())) {
                return playerModel;
            }
        }
        return null;
    }

    public static Player transform(PlayerModel playerModel) {
        Player player = new Player();
        player.setId(playerModel.getId());
        player.setName(playerModel.getName());
        player.setPreferentialPosition(playerModel.getPreferentialPosition());
        return player;
    }

    public static HashSet<Player> transformToPlayer(List<PlayerModel> playerModels) {
        HashSet<Player> players = new HashSet<>();
        for (PlayerModel playerModel : playerModels) {
            players.add(transform(playerModel));
        }
        return players;
    }

    public Collection<PlayerModel> transform(List<Player> playersCollection) {
        Collection<PlayerModel> playerModelsCollection;

        if (playersCollection != null && !playersCollection.isEmpty()) {
            playerModelsCollection = new ArrayList<>();
            for (Player player : playersCollection) {
                playerModelsCollection.add(transform(player));
            }
        } else {
            playerModelsCollection = Collections.emptyList();
        }

        return playerModelsCollection;
    }

    private PlayerModel transform(Player player) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setId(player.getId());
        playerModel.setName(player.getName());
        return playerModel;
    }
}