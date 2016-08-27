package com.transition.scorekeeper.mobile.model;

import android.content.Context;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.mobile.utils.MatchUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author diego.rotondale
 * @since 25/05/16
 */
public class GoalModel implements Serializable {
    private final String description;
    private final Date date;
    private final PlayerModel player;
    private final int playerPosition;
    private final String goalTime;
    private Long id;

    public GoalModel(GoalModelBuilder builder) {
        date = builder.date;
        player = builder.player;
        description = builder.description;
        playerPosition = builder.position;
        goalTime = builder.goalTime;
        id = builder.id;
    }

    public String getDescription() {
        return description;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public Date getDate() {
        return date;
    }

    public LogModel getLog() {
        return new LogModel(this);
    }

    public String getGoalTime() {
        return goalTime;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public Long getId() {
        return id;
    }

    public static class GoalModelBuilder {
        public Date date;
        public Context context;
        public PlayerModel player;
        public int position;
        public String playerPositionValue = "";
        private String goalTime = "";
        private String description;
        private Long id;

        public GoalModelBuilder(Context context) {
            this.context = context;
            this.date = new Date();
        }

        public GoalModel build() {
            description = context.getString(R.string.snack_bar_goal, player.getName(),
                    playerPositionValue, goalTime);
            return new GoalModel(this);
        }

        public GoalModelBuilder setMatchStart(Date matchStart) {
            long duration = date.getTime() - matchStart.getTime();
            this.goalTime = MatchUtils.getDuration(context, duration);
            return this;
        }

        public GoalModelBuilder setPlayer(PlayerModel player) {
            this.player = player;
            return this;
        }

        public GoalModelBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public GoalModelBuilder setPosition(int position) {
            this.position = position;
            setPlayerPositionValue(position);
            return this;
        }

        private void setPlayerPositionValue(int position) {
            switch (position) {
                case Constants.Position.DEFENDER:
                    playerPositionValue = context.getString(R.string.defender);
                    break;
                case Constants.Position.FORWARD:
                    playerPositionValue = context.getString(R.string.forward);
                    break;
            }
        }

        public GoalModelBuilder setDate(Date date) {
            this.date = date;
            return this;
        }
    }

}
