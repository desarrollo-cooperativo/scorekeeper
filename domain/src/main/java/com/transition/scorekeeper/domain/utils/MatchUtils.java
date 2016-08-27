package com.transition.scorekeeper.domain.utils;

import com.transition.scorekeeper.domain.domain.Goal;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.domain.Team;
import com.transition.scorekeeper.domain.exception.goal.InvalidGoalException;
import com.transition.scorekeeper.domain.exception.match.MatchEndException;
import com.transition.scorekeeper.domain.exception.match.MatchNotStartException;
import com.transition.scorekeeper.domain.exception.match.PlayerIsNotOnTheGameException;
import com.transition.scorekeeper.domain.utils.common.DateUtils;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchUtils {

    public static void addGoal(Match match, Goal goal) {
        theGameIsOn(match, goal);
        Team teamWithANewGoal = thePlayerIsOnTheGame(match, goal);
        boolean matchEndWithTheGoal = theGoalIsValid(match, teamWithANewGoal);
        boolean add = match.getGoals().add(goal);
        if (add && matchEndWithTheGoal) {
            match.setEnd(goal.getDate());
        }
    }

    private static boolean theGoalIsValid(Match match, Team teamWithANewGoal) {
        boolean matchEndWithTheGoal = false;
        for (Team team : match.getTeams()) {
            if (!team.equals(teamWithANewGoal)) {
                if (match.getGoalsOfATeam(team).size() >= match.getMaxGoals()) {
                    throw new MatchEndException();
                }
            } else {
                int goals = match.getGoalsOfATeam(team).size() + 1;
                if (goals > match.getMaxGoals()) {
                    throw new InvalidGoalException();
                } else {
                    if (goals == match.getMaxGoals()) {
                        matchEndWithTheGoal = true;
                    }
                }
            }
        }
        return matchEndWithTheGoal;
    }

    private static Team thePlayerIsOnTheGame(Match match, Goal goal) {
        Team team = getPlayerTeam(match, goal.getPlayer());
        if (team == null) {
            throw new PlayerIsNotOnTheGameException();
        }
        return team;
    }

    private static Team getPlayerTeam(Match match, Player player) {
        Iterable<? extends Team> teams = match.getTeams();
        for (Team team : teams) {
            if (team.containsPlayer(player)) {
                return team;
            }
        }
        return null;
    }

    private static void theGameIsOn(Match match, Goal goal) {
        if (match.getStart() == null) {
            throw new MatchNotStartException();
        }
        if (match.getEnd() != null) {
            throw new MatchEndException();
        }

        long minutesOfDifference = DateUtils.getMinutesOfDifference(match.getStart(), goal.getDate());
        if (minutesOfDifference < 0) {
            throw new MatchNotStartException();
        }
        if (minutesOfDifference > match.getMaxMinutes()) {
            throw new MatchEndException();
        }
    }
}
