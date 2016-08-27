package com.transition.scorekeeper.domain.domain;

import com.transition.scorekeeper.domain.builder.PlayerBuilder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class Player {
    private Long id;
    private String name;
    private int preferentialPosition;

    public Player(PlayerBuilder playerBuilder) {
        this.name = playerBuilder.getName();
        this.preferentialPosition = playerBuilder.getPreferentialPosition();
    }

    public Player() {

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Player)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Player rhs = (Player) obj;
        return new EqualsBuilder().
                append(name, rhs.name).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(name).
                toHashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
