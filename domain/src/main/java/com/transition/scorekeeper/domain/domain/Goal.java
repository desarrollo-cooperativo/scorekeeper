package com.transition.scorekeeper.domain.domain;

import com.transition.scorekeeper.domain.builder.GoalBuilder;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class Goal {
    private Player player;
    private int position;
    private Date date;
    private Long id;

    public Goal(GoalBuilder goalBuilder) {
        this.id = goalBuilder.getId();
        this.player = goalBuilder.getPlayer();
        this.position = goalBuilder.getPosition();
        this.date = goalBuilder.getDate();
    }

    public Goal() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
