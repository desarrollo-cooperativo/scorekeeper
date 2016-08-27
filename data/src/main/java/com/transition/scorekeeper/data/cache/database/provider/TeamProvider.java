package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.transition.scorekeeper.data.cache.database.contract.TeamContract;
import com.transition.scorekeeper.data.entity.TeamEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class TeamProvider extends BaseProvider {
    public static final Uri CONTENT_URI = TeamContract.Entry.CONTENT_URI;

    @Override
    protected String getTableName() {
        return TeamContract.Entry.TABLE_NAME;
    }

    @Override
    protected UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(TeamContract.CONTENT_AUTHORITY, TeamContract.PATH, PLAYER);
        return matcher;
    }

    @Override
    protected Uri getUri(long id) {
        return TeamContract.Entry.buildUri(id);
    }

    public Long saveOrUpdate(TeamEntity teamEntity) {
        Long id = teamEntity.getId();
        Uri insert = insert(CONTENT_URI, TeamContract.getContentValues(teamEntity));
        if (insert != null) {
            id = Long.valueOf(insert.getLastPathSegment());
            teamEntity.setId(id);
        }
        return id;
    }

    public List<TeamEntity> getTeams() {
        Cursor cursor = query(CONTENT_URI, null, null, null, null);
        List<TeamEntity> teams = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                teams.add(TeamContract.getTeamEntity(cursor));
            }
            cursor.close();
        }
        return teams;
    }

    public TeamEntity getTeam(Long teamId) {
        String selection = TeamContract.Entry._ID + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(teamId.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        TeamEntity teamEntity = null;

        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                teamEntity = TeamContract.getTeamEntity(cursor);
            }
            cursor.close();
        }
        return teamEntity;
    }

    public Long getTeamByPlayers(List<Long> playersIds) {
        List<String> playersIdsStrings = new ArrayList<>();
        for (Long id : playersIds) {
            playersIdsStrings.add(id.toString());
        }
        String[] ids = new String[playersIdsStrings.size()];
        playersIdsStrings.toArray(ids);
        return new TeamPlayerProvider().getTeamByPlayersIds(ids);
    }
}
