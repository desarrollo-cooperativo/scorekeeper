package com.transition.scorekeeper.dto;

import com.transition.scorekeeper.Builder;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.exception.goal.InvalidGoalException;
import com.transition.scorekeeper.domain.exception.match.MatchEndException;
import com.transition.scorekeeper.domain.exception.match.MatchNotStartException;
import com.transition.scorekeeper.domain.exception.match.PlayerIsNotOnTheGameException;
import com.transition.scorekeeper.domain.utils.MatchUtils;
import com.transition.scorekeeper.domain.utils.common.DateUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class GoalUT {
    @Test(expected = MatchNotStartException.class)
    public void goalInMatchNotStart() {
        Match match = Builder.getMatch();
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA()));
    }

    @Test(expected = MatchNotStartException.class)
    public void goalBeforeMatchStart() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        Date goalDate = DateUtils.addMinutes(match.getStart(), -10);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), goalDate));
    }

    @Test(expected = MatchEndException.class)
    public void goalWithMatchEnd() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        match.setEnd(new Date());
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA()));
    }

    @Test(expected = MatchEndException.class)
    public void goalAfterMatchEnd() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        Date goalDate = DateUtils.addMinutes(match.getStart(), match.getMaxMinutes() + 1);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), goalDate));
    }

    @Test(expected = MatchEndException.class)
    public void goalAfterMatchEndByMaxGoals() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        match.setMaxGoals(2);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), DateUtils.addMinutes(match.getStart(), 1)));
        MatchUtils.addGoal(match, Builder.getGoalWithPosition(Builder.getPlayerC(), DateUtils.addMinutes(match.getStart(), 2)));
        MatchUtils.addGoal(match, Builder.getGoalWithPosition(Builder.getPlayerD(), DateUtils.addMinutes(match.getStart(), 2)));
        Date lastGoal = DateUtils.addMinutes(match.getStart(), 3);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), lastGoal));
        Assert.assertEquals(match.getEnd(), lastGoal);
    }

    @Test
    public void goalHappyPath() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA()));
    }

    @Test
    public void goalWithoutPositionAndPlayerWithPreferentialPosition() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA()));
    }

    @Test(expected = InvalidGoalException.class)
    public void goalWithoutPositionAndPlayerWithoutPreferentialPosition() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerC()));
    }

    @Test(expected = PlayerIsNotOnTheGameException.class)
    public void goalPlayerNotInGame() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        MatchUtils.addGoal(match, Builder.getGoalWithPosition(Builder.getPlayerE()));
    }

    @Test
    public void happyPathGoal() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        Date goalDate = DateUtils.addMinutes(match.getStart(), 1);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), goalDate));
    }

    @Test
    public void happyPathEndGameByMaxGoals() {
        Match match = Builder.getMatch();
        match.setStart(new Date());
        match.setMaxGoals(2);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), DateUtils.addMinutes(match.getStart(), 1)));
        MatchUtils.addGoal(match, Builder.getGoalWithPosition(Builder.getPlayerC(), DateUtils.addMinutes(match.getStart(), 2)));
        Date lastGoal = DateUtils.addMinutes(match.getStart(), 3);
        MatchUtils.addGoal(match, Builder.getGoalWithoutPosition(Builder.getPlayerA(), lastGoal));
        Assert.assertEquals(match.getEnd(), lastGoal);
    }
}
