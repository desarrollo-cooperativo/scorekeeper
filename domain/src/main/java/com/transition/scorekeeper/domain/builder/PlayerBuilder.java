package com.transition.scorekeeper.domain.builder;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.exception.player.InvalidNameException;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class PlayerBuilder {
    private String name;
    private int preferentialPosition = Constants.Position.NONE;

    public Player build() {
        isValid();
        return new Player(this);
    }

    public String getName() {
        return name;
    }

    public PlayerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public int getPreferentialPosition() {
        return preferentialPosition;
    }

    public PlayerBuilder setPreferentialPosition(int preferentialPosition) {
        this.preferentialPosition = preferentialPosition;
        return this;
    }

    private boolean isAValidName() {
        return name == null
                || name.isEmpty()
                || name.length() < Constants.PlayerConstants.MIN_LENGTH
                || name.length() > Constants.PlayerConstants.MAX_LENGTH;
    }

    public void isValid() {
        if (isAValidName()) {
            throw new InvalidNameException();
        }
    }
}
