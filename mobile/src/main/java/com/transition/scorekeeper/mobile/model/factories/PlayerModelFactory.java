package com.transition.scorekeeper.mobile.model.factories;

import com.transition.scorekeeper.mobile.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 05/06/16
 */
public class PlayerModelFactory {
    public static List<PlayerModel> getPlayers(Long teamId) {
        List<PlayerModel> players = new ArrayList<>();
        players.add(0, getPlayer(0, teamId));
        players.add(1, getPlayer(1, teamId));
        return players;
    }

    private static PlayerModel getPlayer(int positionOnView, Long teamId) {
        PlayerModel playerModel = new PlayerModel();
        playerModel.setTeamInfo(teamId, positionOnView);
        return playerModel;
    }
}
