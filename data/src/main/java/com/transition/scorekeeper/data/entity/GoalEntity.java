package com.transition.scorekeeper.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 22/05/16
 */
public class GoalEntity {
    @SerializedName("id")
    private Long id;
    @SerializedName("date")
    private Date date;
    @SerializedName("position")
    private int position;
    @SerializedName("player")
    private PlayerEntity player;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }
}
