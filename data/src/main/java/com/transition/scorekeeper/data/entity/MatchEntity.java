package com.transition.scorekeeper.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 20/05/16
 */
public class MatchEntity {
    @SerializedName("id")
    private Long id;
    @SerializedName("teams")
    private HashSet<TeamEntity> teams = new HashSet<>();
    @SerializedName("goals")
    private List<GoalEntity> goals = new ArrayList<>();
    @SerializedName("max_minutes")
    private Integer maxMinutes;
    @SerializedName("max_goals")
    private Integer maxGoals;
    @SerializedName("start")
    private Date start;
    @SerializedName("end")
    private Date end;

    public MatchEntity() {
    }

    public MatchEntity(Long id) {
        this.id = id;
    }

    public MatchEntity(Long id, Date start, Date end, Integer maxMinutes, Integer maxGoals) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.maxMinutes = maxMinutes;
        this.maxGoals = maxGoals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HashSet<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(HashSet<TeamEntity> teams) {
        this.teams = teams;
    }

    public List<GoalEntity> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalEntity> goals) {
        this.goals = goals;
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

    public Long getStartTime() {
        if (getStart() == null) {
            return null;
        }
        return getStart().getTime();
    }

    public Long getEndTime() {
        if (getEnd() == null) {
            return null;
        }
        return getEnd().getTime();
    }
}
