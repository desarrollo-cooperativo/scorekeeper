package com.transition.scorekeeper.domain.builder;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.domain.Goal;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.exception.goal.InvalidGoalException;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class GoalBuilder {
    private Long id;
    private Player player;
    private int position = Constants.Position.NONE;
    private Date date;

    public Goal build() {
        isValid();
        return new Goal(this);
    }

    public Player getPlayer() {
        return player;
    }

    public GoalBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public int getPosition() {
        return position;
    }

    public GoalBuilder setPosition(int position) {
        this.position = position;
        return this;
    }

    public void isValid() {
        if (player == null) {
            throw new InvalidGoalException();
        }
        if (date == null) {
            throw new InvalidGoalException();
        }
        if (position == Constants.Position.NONE) {
            position = player.getPreferentialPosition();
        }
        if (position == Constants.Position.NONE) {
            throw new InvalidGoalException();
        }
    }

    public Date getDate() {
        return date;
    }

    public GoalBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public Long getId() {
        return id;
    }

    public GoalBuilder setId(Long id) {
        this.id = id;
        return this;
    }
}
