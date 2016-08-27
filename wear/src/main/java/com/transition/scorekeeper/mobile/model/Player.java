package com.transition.scorekeeper.mobile.model;

/**
 * @author diego.rotondale
 * @since 16/08/16
 */
public class Player {
    private Long id;
    private String name;

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
