package com.transition.scorekeeper.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;

/**
 * @author diego.rotondale
 * @since 22/05/16
 */
public class TeamEntity {
    @SerializedName("id")
    private Long id;
    @SerializedName("players")
    private HashSet<PlayerEntity> players;

    public HashSet<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(HashSet<PlayerEntity> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
