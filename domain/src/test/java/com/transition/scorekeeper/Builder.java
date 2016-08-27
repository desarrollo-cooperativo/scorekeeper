package com.transition.scorekeeper;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.builder.GoalBuilder;
import com.transition.scorekeeper.domain.builder.MatchBuilder;
import com.transition.scorekeeper.domain.builder.PlayerBuilder;
import com.transition.scorekeeper.domain.builder.TeamBuilder;
import com.transition.scorekeeper.domain.domain.Goal;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.domain.Team;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class Builder {
    public static final String PLAYER_A_NAME = "Diego";
    public static final String PLAYER_B_NAME = "Martin";
    public static final String PLAYER_C_NAME = "Nelson";
    public static final String PLAYER_D_NAME = "Pedro";
    public static final int PLAYER_A_POSITION = Constants.Position.DEFENDER;
    public static final int PLAYER_B_POSITION = Constants.Position.FORWARD;
    private static final String PLAYER_E_NAME = "Santiago";

    public static Player getPlayerA() {
        return new PlayerBuilder()
                .setName(PLAYER_A_NAME)
                .setPreferentialPosition(PLAYER_A_POSITION)
                .build();
    }

    public static Player getPlayerB() {
        return new PlayerBuilder()
                .setName(PLAYER_B_NAME)
                .setPreferentialPosition(PLAYER_B_POSITION)
                .build();
    }

    public static Player getPlayerC() {
        return new PlayerBuilder()
                .setName(PLAYER_C_NAME)
                .build();
    }

    public static Player getPlayerD() {
        return new PlayerBuilder()
                .setName(PLAYER_D_NAME)
                .build();
    }

    public static Player getPlayerE() {
        return new PlayerBuilder()
                .setName(PLAYER_E_NAME)
                .build();
    }

    public static Team getHomeTeam() {
        return new TeamBuilder()
                .setPlayer(getPlayerA())
                .setPlayer(getPlayerB())
                .build();
    }

    public static Team getAwayTeam() {
        return new TeamBuilder()
                .setPlayer(getPlayerC())
                .setPlayer(getPlayerD())
                .build();
    }

    public static Match getMatch() {
        return new MatchBuilder()
                .setTeams(getHomeTeam(), getAwayTeam())
                .build();
    }

    public static Goal getGoalWithoutPosition(Player player) {
        return getGoalWithoutPosition(player, new Date());
    }

    public static Goal getGoalWithoutPosition(Player player, Date date) {
        return new GoalBuilder()
                .setPlayer(player)
                .setDate(date)
                .build();
    }

    public static Goal getGoalWithPosition(Player player) {
        Date date = new Date();
        return getGoalWithPosition(player, date);
    }

    public static Goal getGoalWithPosition(Player player, Date date) {
        return new GoalBuilder()
                .setPlayer(player)
                .setPosition(Constants.Position.FORWARD)
                .setDate(date)
                .build();
    }
}
