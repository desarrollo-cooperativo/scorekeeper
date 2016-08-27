package com.transition.scorekeeper.domain.domain;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.builder.MatchBuilder;
import com.transition.scorekeeper.domain.exception.match.PlayerIsNotOnTheGameException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class Match {
    private Long id;
    private Date start;
    private Date end;
    private HashSet<Team> teams = new HashSet<>();
    private List<Goal> goals = new ArrayList<>();
    private Integer maxMinutes = Constants.MatchConstants.MAX_MINUTES;
    private Integer maxGoals = Constants.MatchConstants.MAX_GOALS;

    public Match(MatchBuilder matchBuilder) {
        this.teams = matchBuilder.getTeams();
    }

    public Match() {
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(Integer maxMinutes) {
        this.maxMinutes = maxMinutes;
    }

    public Integer getMaxGoals() {
        return maxGoals;
    }

    public void setMaxGoals(Integer maxGoals) {
        this.maxGoals = maxGoals;
    }

    public HashSet<Team> getTeams() {
        return teams;
    }

    public void setTeams(HashSet<Team> teams) {
        this.teams = teams;
    }

    public List<Goal> getGoalsOfATeam(Team team) {
        return getGoals(team);
    }

    private List<Goal> getGoals(Team team) {
        List<Goal> teamGoals = new ArrayList<>();
        for (Goal goal : goals) {
            Player player = goal.getPlayer();
            if (team.containsPlayer(player)) {
                teamGoals.add(goal);
            }
        }
        return teamGoals;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public Long getTeamId(Player player) {
        return getTeam(player).getId();
    }

    public Team getTeam(Player player) {
        for (Team team : teams) {
            if (team.containsPlayer(player)) {
                return team;
            }
        }
        throw new PlayerIsNotOnTheGameException();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}