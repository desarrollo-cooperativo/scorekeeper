package com.transition.scorekeeper.mobile.model.mapper;

import com.transition.scorekeeper.domain.domain.Goal;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.domain.Team;
import com.transition.scorekeeper.mobile.model.TeamModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 23/05/16
 */
public class TeamModelDataMapper {
    public static TeamModel transform(Team team, List<Goal> goals) {
        if (team == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        TeamModel teamModel = new TeamModel();
        teamModel.setId(team.getId());
        teamModel.setPlayers(PlayerModelDataMapper.transform(team));
        teamModel.setGoalsCount(goals.size());
        return teamModel;
    }

    public static List<TeamModel> transform(Match match) {
        ArrayList<TeamModel> teamModels = new ArrayList<>();
        ArrayList<Team> teams = new ArrayList<>(match.getTeams());
        for (Team team : teams) {
            teamModels.add(TeamModelDataMapper.transform(team, match.getGoalsOfATeam(team)));
        }
        return teamModels;
    }

    public static Team transform(TeamModel teamModel) {
        Team team = new Team();
        team.setId(teamModel.getId());
        team.setPlayers(PlayerModelDataMapper.transformToPlayer(teamModel.getPlayers()));
        return team;
    }

    public static HashSet<Team> transformAll(List<TeamModel> teams) {
        HashSet<Team> teamHashSet = new HashSet<>();
        if (teams != null) {
            for (TeamModel team : teams) {
                teamHashSet.add(transform(team));
            }
        }
        return teamHashSet;
    }
}
