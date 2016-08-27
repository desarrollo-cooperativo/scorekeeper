package com.transition.scorekeeper.dto;

import com.transition.scorekeeper.Builder;
import com.transition.scorekeeper.domain.builder.TeamBuilder;
import com.transition.scorekeeper.domain.domain.Team;
import com.transition.scorekeeper.domain.exception.team.InvalidSizeOfPlayersException;
import com.transition.scorekeeper.domain.exception.team.WithoutPlayersException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class TeamUT {
    @Test
    public void teamHappyPath() {
        Team team = Builder.getHomeTeam();
        Assert.assertEquals(team.getSize(), 2);
        Assert.assertEquals(team.getPlayers().contains(Builder.getPlayerA()), true);
        Assert.assertEquals(team.getPlayers().contains(Builder.getPlayerB()), true);
    }

    @Test
    public void teamWithSamePlayer() {
        new TeamBuilder()
                .setPlayer(Builder.getPlayerA())
                .setPlayer(Builder.getPlayerA())
                .build();
    }

    @Test
    public void teamWithOnlyOnePlayer() {
        Team team = new TeamBuilder()
                .setPlayer(Builder.getPlayerA())
                .setPlayer(null)
                .build();
        Assert.assertEquals(team.getSize(), 1);
    }

    @Test(expected = WithoutPlayersException.class)
    public void teamWithoutPlayers() {
        new TeamBuilder()
                .setPlayer(null)
                .setPlayer(null)
                .build();
    }

    @Test
    public void teamEquals() {
        Team teamA = new TeamBuilder()
                .setPlayer(Builder.getPlayerB())
                .setPlayer(Builder.getPlayerA())
                .build();
        Assert.assertEquals(teamA, teamA);
        Team teamB = new TeamBuilder()
                .setPlayer(Builder.getPlayerA())
                .setPlayer(Builder.getPlayerB())
                .build();
        Assert.assertEquals(teamA, teamB);
    }


    @Test(expected = InvalidSizeOfPlayersException.class)
    public void teamWithToMuchPlayers() {
        new TeamBuilder()
                .setPlayer(Builder.getPlayerA())
                .setPlayer(Builder.getPlayerB())
                .setPlayer(Builder.getPlayerC())
                .build();
    }
}