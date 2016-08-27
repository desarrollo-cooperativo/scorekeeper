package com.transition.scorekeeper.dto;

import com.transition.scorekeeper.Builder;
import com.transition.scorekeeper.domain.builder.MatchBuilder;
import com.transition.scorekeeper.domain.exception.match.InvalidSizeOfTeamsException;
import com.transition.scorekeeper.domain.exception.match.WithoutTeamsException;

import org.junit.Test;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class MatchUT {

    @Test(expected = WithoutTeamsException.class)
    public void matchWithoutTeam() {
        new MatchBuilder()
                .setTeams(null, null)
                .build();
    }

    @Test(expected = InvalidSizeOfTeamsException.class)
    public void matchWithOnlyOneTeam() {
        new MatchBuilder()
                .setTeams(Builder.getHomeTeam(), null)
                .build();
    }

    @Test(expected = InvalidSizeOfTeamsException.class)
    public void matchWithSameTeam() {
        new MatchBuilder()
                .setTeams(Builder.getHomeTeam(), Builder.getHomeTeam())
                .build();
    }
}
