package com.transition.scorekeeper.data.entity.mapper;

import com.transition.scorekeeper.data.entity.TeamEntity;
import com.transition.scorekeeper.domain.domain.Team;

import java.util.HashSet;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TeamEntityDataMapper {

    @Inject
    public TeamEntityDataMapper() {
    }

    public static Team transform(TeamEntity teamEntity) {
        Team team = null;
        if (teamEntity != null) {
            team = new Team();
            team.setId(teamEntity.getId());
            team.setPlayers(PlayerEntityDataMapper.transform(teamEntity.getPlayers()));
        }
        return team;
    }

    public static HashSet<Team> transform(HashSet<TeamEntity> teamEntityCollection) {
        HashSet<Team> teams = new HashSet<>();
        Team team;
        for (TeamEntity teamEntity : teamEntityCollection) {
            team = transform(teamEntity);
            if (team != null) {
                teams.add(team);
            }
        }
        return teams;
    }

    public static HashSet<TeamEntity> transformFromTeam(HashSet<Team> teamCollection) {
        HashSet<TeamEntity> teams = new HashSet<>();
        TeamEntity teamEntity;
        for (Team team : teamCollection) {
            teamEntity = transform(team);
            teams.add(teamEntity);
        }
        return teams;
    }

    public static TeamEntity transform(Team team) {
        TeamEntity teamEntity = new TeamEntity();
        if (team != null) {
            teamEntity.setId(team.getId());
            teamEntity.setPlayers(PlayerEntityDataMapper.transformFromPlayer(team.getPlayers()));
        }
        return teamEntity;
    }
}
