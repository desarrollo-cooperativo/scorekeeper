package com.transition.scorekeeper.domain.builder;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.domain.domain.Team;
import com.transition.scorekeeper.domain.exception.match.InvalidSizeOfTeamsException;
import com.transition.scorekeeper.domain.exception.match.WithoutTeamsException;

import java.util.HashSet;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class MatchBuilder {
    private HashSet<Team> teams = new HashSet<>();

    public Match build() {
        isValid();
        return new Match(this);
    }

    public MatchBuilder setTeams(Team homeTeam, Team awayTeam) {
        if (homeTeam != null) {
            this.teams.add(homeTeam);
        }
        if (awayTeam != null) {
            this.teams.add(awayTeam);
        }
        return this;
    }

    public HashSet<Team> getTeams() {
        return teams;
    }

    public void isValid() {
        if (teams == null || teams.isEmpty()) {
            throw new WithoutTeamsException();
        } else {
            if (!hasValidSizeOfTeams()) {
                throw new InvalidSizeOfTeamsException();
            }
        }
    }

    private boolean hasValidSizeOfTeams() {
        return teams.size() <= Constants.MatchConstants.MAX_TEAMS && teams.size() >= Constants.MatchConstants.MIN_TEAMS;
    }

}
