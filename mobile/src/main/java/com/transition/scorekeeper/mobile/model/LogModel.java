package com.transition.scorekeeper.mobile.model;

import android.content.Context;

import com.transition.scorekeeper.domain.utils.common.DateUtils;
import com.transition.scorekeeper.mobile.utils.MatchUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class LogModel implements Serializable {
    private final String log;
    private final Date date;
    private Long teamId = null;

    public LogModel(GoalModel goalModel) {
        PlayerModel player = goalModel.getPlayer();
        String name = player.getName();
        this.teamId = player.getTeamId();
        this.log = name + " (" + goalModel.getGoalTime() + ")";
        this.date = goalModel.getDate();
    }

    public LogModel(Date date) {
        this.log = MatchUtils.getDate(date);
        this.date = date;
    }

    public LogModel(Context context, Date start, Date end) {
        long duration = end.getTime() - start.getTime();
        this.log = MatchUtils.getDuration(context, duration);
        this.date = DateUtils.addMinutes(end, 1);
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getLog() {
        return log;
    }

    public Date getDate() {
        return date;
    }
}
