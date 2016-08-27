package com.transition.scorekeeper.domain.builder;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.domain.domain.Player;
import com.transition.scorekeeper.domain.domain.Team;
import com.transition.scorekeeper.domain.exception.team.InvalidSizeOfPlayersException;
import com.transition.scorekeeper.domain.exception.team.WithoutPlayersException;

import java.util.HashSet;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class TeamBuilder {
    private HashSet<Player> players = new HashSet<>();

    public Team build() {
        isAValidTeam();
        return new Team(this);
    }

    public TeamBuilder setPlayer(Player player) {
        if (player != null) {
            players.add(player);
        }
        return this;
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public void isAValidTeam() {
        if (players == null || players.isEmpty()) {
            throw new WithoutPlayersException();
        }
        if (!hasValidSizeOfPlayers()) {
            throw new InvalidSizeOfPlayersException();
        }
    }

    private boolean hasValidSizeOfPlayers() {
        return players.size() <= Constants.TeamConstants.MAX_PLAYERS && players.size() >= Constants.TeamConstants.MIN_PLAYERS;
    }
}
