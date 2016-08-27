package com.transition.scorekeeper.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author diego.rotondale
 * @since 22/05/16
 */
public class PlayerEntity {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("preferential_position")
    private int preferentialPosition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreferentialPosition() {
        return preferentialPosition;
    }

    public void setPreferentialPosition(int preferentialPosition) {
        this.preferentialPosition = preferentialPosition;
    }
}
