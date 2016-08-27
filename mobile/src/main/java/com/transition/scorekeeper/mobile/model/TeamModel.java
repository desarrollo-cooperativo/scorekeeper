package com.transition.scorekeeper.mobile.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class TeamModel implements Serializable {
    private static final int PLAYERS_MIN_SIZE = 1;
    private Long id;
    private int goalsCount;
    private List<PlayerModel> players;

    public int getGoalsCount() {
        return goalsCount;
    }

    public void setGoalsCount(int goalsCount) {
        this.goalsCount = goalsCount;
    }

    public PlayerModel getPlayerTop() {
        sortPlayers();
        return players.get(0);
    }

    public PlayerModel getPlayerBottom() {
        sortPlayers();
        return players.get(1);
    }

    public void addGoal() {
        goalsCount++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getPlayersIds() {
        List<Long> playersIds = null;
        if (players != null) {
            playersIds = new ArrayList<>();
            for (PlayerModel player : players) {
                if (player != null && player.getId() != null) {
                    playersIds.add(player.getId());
                }
            }
        }
        return playersIds;
    }

    public boolean addPlayer(PlayerModel playerModelSource, PlayerModel playerModel) {
        boolean wasAdded = false;
        if (players.contains(playerModelSource)) {
            int indexOf = players.indexOf(playerModelSource);
            players.remove(indexOf);
            playerModel.setTeamInfo(playerModelSource);
            players.add(indexOf, playerModel);
            wasAdded = true;
        }
        return wasAdded;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TeamModel)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        TeamModel rhs = (TeamModel) obj;
        return new EqualsBuilder().
                append(id, rhs.id).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(id).
                toHashCode();
    }

    public boolean hasMinPlayers() {
        return players != null && getPlayersSize() >= PLAYERS_MIN_SIZE;
    }

    private int getPlayersSize() {
        int size = 0;
        for (PlayerModel player : players) {
            if (player.hasPlayer()) {
                size++;
            }
        }
        return size;
    }

    public void addPlayer(PlayerModel playerModel) {
        if (players == null) {
            players = new ArrayList<>();
        }
        players.add(playerModel);
    }

    public void modifyPlayer(PlayerModel playerModelSource, PlayerModel playerModel) {
        if (players.contains(playerModelSource)) {
            int playerPosition = players.indexOf(playerModelSource);
            players.remove(playerPosition);
            players.add(playerPosition, playerModel);
        }
    }

    public List<PlayerModel> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerModel> players) {
        this.players = players;
    }

    private void sortPlayers() {
        Collections.sort(players, new Comparator<PlayerModel>() {
            @Override
            public int compare(PlayerModel lhs, PlayerModel rhs) {
                if (lhs.getId() != null && rhs.getId() != null) {
                    return lhs.getId().compareTo(rhs.getId());
                }
                return 0;
            }
        });
    }
}
