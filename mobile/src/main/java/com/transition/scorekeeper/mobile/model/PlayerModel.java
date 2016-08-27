package com.transition.scorekeeper.mobile.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author diego.rotondale
 * @since 23/05/16
 */
public class PlayerModel implements Serializable {
    private Long id;
    private String name;
    private int preferentialPosition;
    private TeamInfo teamInfo;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return teamInfo.getTeamId();
    }

    public void setTeamInfo(Long teamId, int positionOnView) {
        teamInfo = new TeamInfo();
        teamInfo.setTeamId(teamId);
        teamInfo.setPositionOnView(positionOnView);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PlayerModel)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        PlayerModel rhs = (PlayerModel) obj;
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

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(PlayerModel playerModel) {
        this.teamInfo = playerModel.getTeamInfo();
    }

    public boolean hasPlayer() {
        return name != null;
    }

    public class TeamInfo implements Serializable {
        private Long teamId;
        private int positionOnView;

        public Long getTeamId() {
            return teamId;
        }

        public void setTeamId(Long teamId) {
            this.teamId = teamId;
        }

        public int getPositionOnView() {
            return positionOnView;
        }

        public void setPositionOnView(int positionOnView) {
            this.positionOnView = positionOnView;
        }
    }

}
