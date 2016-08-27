package com.transition.scorekeeper.domain.domain;

import com.transition.scorekeeper.domain.builder.TeamBuilder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;

/**
 * @author diego.rotondale
 * @since 13/05/16
 */
public class Team {
    private Long id;
    private HashSet<Player> players = new HashSet<>();

    public Team(TeamBuilder teamBuilder) {
        this.players = teamBuilder.getPlayers();
    }

    public Team() {
    }

    public int getSize() {
        return players.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Team)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Team rhs = (Team) obj;
        return new EqualsBuilder().
                append(players, rhs.players).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(players).
                toHashCode();
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public void setPlayers(HashSet<Player> players) {
        this.players = players;
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
