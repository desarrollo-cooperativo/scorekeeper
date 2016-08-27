package com.transition.scorekeeper.mobile.model.factories;

import com.transition.scorekeeper.mobile.model.TeamModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 05/06/16
 */
public class TeamModelFactory {
    private static TeamModel getTeamModel(Long id) {
        TeamModel teamModel = new TeamModel();
        teamModel.setId(id);
        teamModel.setPlayers(PlayerModelFactory.getPlayers(id));
        return teamModel;
    }

    public static List<TeamModel> getTeams() {
        List<TeamModel> teams = new ArrayList<>();
        teams.add(getTeamModel(0L));
        teams.add(getTeamModel(1L));
        return teams;
    }
}
