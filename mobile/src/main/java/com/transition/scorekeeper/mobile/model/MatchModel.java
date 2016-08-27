package com.transition.scorekeeper.mobile.model;

import android.content.Context;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.domain.utils.common.DateUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchModel implements Serializable {
    private static final int TEAMS_SIZE = 2;
    private Long id;
    private Date matchEnd;
    private Integer maxGoals;
    private Integer maxMinutes;
    private Date matchStart;
    private List<GoalModel> goals = new ArrayList<>();
    private List<LogModel> log;
    private List<TeamModel> teams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMatchEnd() {
        return matchEnd;
    }

    public void setMatchEnd(Date matchEnd) {
        this.matchEnd = matchEnd;
    }

    public String getGoals(Context context) {
        String leftTeamGoals;
        String rightTeamGoals;
        if (needTheMachStart()) {
            String emptyValue = context.getString(R.string.empty_goals);
            leftTeamGoals = emptyValue;
            rightTeamGoals = emptyValue;
        } else {
            leftTeamGoals = String.valueOf(getTeamLeftGoals());
            rightTeamGoals = String.valueOf(getTeamRightGoals());
        }
        return context.getString(R.string.match_status_result, leftTeamGoals, rightTeamGoals);
    }

    public String getStatusMessage(Context context) {
        if (matchStart == null) {
            return context.getString(R.string.match_status_to_start);
        }
        if (matchEnd != null) {
            return context.getString(R.string.match_status_end);
        }
        return null;
    }

    public boolean isTheMatchInProgress() {
        return matchStart != null && !isTheMatchEnd();
    }

    public long getTimeToEndGame() {
        long maxMillis = TimeUnit.MINUTES.toMillis(maxMinutes);
        long diff = new Date().getTime() - matchStart.getTime();
        return maxMillis - diff;
    }

    public TeamModel getLeftTeam() {
        return getTeam(0);
    }

    public TeamModel getRightTeam() {
        return getTeam(1);
    }

    private TeamModel getTeam(int index) {
        TeamModel team = null;
        if (teams != null && !teams.isEmpty() && teams.size() > index) {
            sortTeams();
            team = teams.get(index);
        }
        return team;
    }

    private void sortTeams() {
        Collections.sort(teams, new Comparator<TeamModel>() {
            @Override
            public int compare(TeamModel lhs, TeamModel rhs) {
                if (lhs.getId() != null && rhs.getId() != null) {
                    return lhs.getId().compareTo(rhs.getId());
                }
                return 0;
            }
        });
    }

    public Integer getMaxMinutes() {
        return maxMinutes;
    }

    public void setMaxMinutes(Integer maxMinutes) {
        this.maxMinutes = maxMinutes;
    }

    public Integer getMaxGoals() {
        return maxGoals;
    }

    public void setMaxGoals(Integer maxGoals) {
        this.maxGoals = maxGoals;
    }

    public boolean isTheMatchEnd() {
        return matchEnd != null || theMatchFinishedByTime();
    }

    public boolean needTheMachStart() {
        return matchStart == null;
    }

    public List<LogModel> getMatchLog(Context context) {
        if (log == null || log.isEmpty()) {
            log = initializeLog(context);
        }
        return log;
    }

    private List<LogModel> initializeLog(Context context) {
        ArrayList<LogModel> log = new ArrayList<>();
        if (!needTheMachStart()) {
            addMatchEndLog(context, log);
            for (GoalModel goalModel : goals) {
                log.add(new LogModel(goalModel));
            }
            log.add(new LogModel(matchStart));
        }
        Collections.sort(log, new DateComparator());
        return log;
    }

    private void addMatchEndLog(Context context, List<LogModel> log) {
        if (isTheMatchEnd()) {
            log.add(new LogModel(context, matchStart, matchEnd));
            log.add(new LogModel(matchEnd));
        }
    }

    public Date getMatchStart() {
        return matchStart;
    }

    public void setMatchStart(Date matchStart) {
        this.matchStart = matchStart;
    }

    public int getTeamLeftGoals() {
        return getLeftTeam().getGoalsCount();
    }

    public int getTeamRightGoals() {
        return getRightTeam().getGoalsCount();
    }

    public boolean addGoal(GoalModel goalModel) {
        boolean wasAdded = false;
        if (isTheMatchInProgress()) {
            TeamModel team = getTeam(goalModel.getPlayer());
            if (team != null) {
                int newGoals = team.getGoalsCount() + 1;
                if (hasNotGoalsLimit() || newGoals < maxGoals) {
                    wasAdded = true;
                } else {
                    if (newGoals == maxGoals) {
                        wasAdded = true;
                        matchEnd = goalModel.getDate();
                    }
                }
                if (wasAdded) {
                    wasAdded = goals.add(goalModel);
                    team.addGoal();
                }
            }
        }
        return wasAdded;
    }

    private boolean hasNotGoalsLimit() {
        return maxGoals == null || maxGoals == 0;
    }

    private boolean theMatchFinishedByTime() {
        boolean theMatchFinishedByTime = false;
        if (!hasNotTimeLimit() && matchStart != null) {
            long minutesOfDifference = DateUtils.getMinutesOfDifference(matchStart, new Date());
            if (minutesOfDifference >= maxMinutes) {
                theMatchFinishedByTime = true;
                matchEnd = DateUtils.addMinutes(matchStart, maxMinutes);
            }
        }
        return theMatchFinishedByTime;
    }

    private boolean hasNotTimeLimit() {
        return maxMinutes == null || maxMinutes == 0;
    }

    public List<LogModel> getMatchEndLog(Context context) {
        List<LogModel> matchLog = null;
        if (isTheMatchEnd()) {
            matchLog = new ArrayList<>();
            addMatchEndLog(context, matchLog);
        }
        return matchLog;
    }

    private TeamModel getTeam(PlayerModel player) {
        if (teams != null) {
            for (TeamModel team : teams) {
                if (player.getTeamId() != null && player.getTeamId().equals(team.getId())) {
                    return team;
                }
            }
        }
        return null;
    }

    public long getMatchElapsedTime() {
        return new Date().getTime() - getMatchStart().getTime();
    }

    public List<TeamModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamModel> teams) {
        this.teams = teams;
    }

    public void addLog(List<LogModel> logs) {
        log.addAll(logs);
        Collections.sort(log, new DateComparator());
    }

    public void addLog(LogModel newLog) {
        log.add(newLog);
        Collections.sort(log, new DateComparator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MatchModel)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        MatchModel rhs = (MatchModel) obj;
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

    public boolean matchStatusChange(MatchModel matchModel) {
        return !(isTheMatchInProgress() && matchModel.isTheMatchInProgress()
                || isTheMatchEnd() && matchModel.isTheMatchEnd()
                || needTheMachStart() && matchModel.needTheMachStart());
    }

    public Long getRightTeamId() {
        Long rightTeamId = null;
        if (getRightTeam() != null) {
            rightTeamId = getRightTeam().getId();
        }
        return rightTeamId;
    }

    public String unableToStart(Context context) {
        String canStart = null;
        if (teams == null || teams.isEmpty()
                || teams.size() != TEAMS_SIZE || !hasTeamsMinPlayer()) {
            canStart = String.format(context.getString(R.string.match_can_not_start), TEAMS_SIZE);
        }
        return canStart;
    }

    private boolean hasTeamsMinPlayer() {
        boolean hasMinPlayers = true;
        for (TeamModel team : teams) {
            if (!team.hasMinPlayers()) {
                hasMinPlayers = false;
            }
        }
        return hasMinPlayers;
    }

    public List<Long> getPlayersIds() {
        List<Long> playersIds = new ArrayList<>();
        if (teams != null) {
            for (TeamModel team : teams) {
                playersIds.addAll(team.getPlayersIds());
            }
        }
        return playersIds;
    }

    public List<PlayerModel> getPlayers() {
        List<PlayerModel> players = new ArrayList<>();
        if (teams != null) {
            for (TeamModel team : teams) {
                List<PlayerModel> teamPlayers = team.getPlayers();
                for (PlayerModel player : teamPlayers) {
                    if (player != null && player.getId() != null) {
                        players.add(player);
                    }
                }
            }
        }
        return players;
    }

    public boolean addPlayer(PlayerModel playerModelSource, PlayerModel playerModel) {
        boolean wasAdded = false;
        TeamModel team = getTeam(playerModelSource);
        if (team != null) {
            wasAdded = team.addPlayer(playerModelSource, playerModel);
        }
        return wasAdded;
    }

    public TeamModel getTeam(PlayerModel playerModelSource, PlayerModel playerModel) {
        TeamModel team = getTeam(playerModelSource);
        if (team == null) {
            team = new TeamModel();
            team.addPlayer(playerModel);
        } else {
            team.modifyPlayer(playerModelSource, playerModel);
        }
        return team;
    }

    public void addTeam(TeamModel teamModel) {
        if (teams == null) {
            teams = new ArrayList<>();
        }
        if (teams.contains(teamModel)) {
            int teamPosition = teams.indexOf(teamModel);
            teams.remove(teamPosition);
            teams.add(teamPosition, teamModel);
        } else {
            teams.add(teamModel);
        }
    }

    public List<GoalModel> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalModel> goals) {
        this.goals = goals;
    }

    public PlayerModel getPlayerById(Long playerId) {
        PlayerModel playerModel = null;
        List<PlayerModel> players = getPlayers();
        PlayerModel playerToSearch = new PlayerModel();
        playerToSearch.setId(playerId);
        if (players.contains(playerToSearch)) {
            playerModel = players.get(players.indexOf(playerToSearch));
        }
        return playerModel;
    }

    private static class DateComparator implements Comparator<LogModel> {
        @Override
        public int compare(LogModel obj1, LogModel obj2) {
            return obj1.getDate().compareTo(obj2.getDate());
        }
    }
}
