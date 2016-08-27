package com.transition.scorekeeper.dto;

import com.transition.scorekeeper.Builder;
import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.builder.PlayerBuilder;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.exception.player.InvalidNameException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class PlayerUT {
    @Test(expected = InvalidNameException.class)
    public void playerWithoutName() {
        new PlayerBuilder()
                .setName(null)
                .build();
    }

    @Test(expected = InvalidNameException.class)
    public void playerWithEmptyName() {
        new PlayerBuilder()
                .setName(StringUtils.EMPTY)
                .build();
    }

    @Test(expected = InvalidNameException.class)
    public void playerWithShortName() {
        new PlayerBuilder()
                .setName(generateAName(Constants.PlayerConstants.MIN_LENGTH - 1))
                .build();
    }

    @Test(expected = InvalidNameException.class)
    public void playerWithBigName() {
        new PlayerBuilder()
                .setName(generateAName(Constants.PlayerConstants.MAX_LENGTH + 1))
                .build();
    }

    @Test
    public void playerWithoutPosition() {
        Player player = new PlayerBuilder()
                .setName(Builder.PLAYER_A_NAME)
                .build();
        Assert.assertEquals(player.getPreferentialPosition(), Constants.Position.NONE);
    }

    private String generateAName(int nameMinLength) {
        String name = StringUtils.EMPTY;
        for (int i = 0; i < nameMinLength; i++) {
            name = name + i;
        }
        return name;
    }
}