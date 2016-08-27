package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.transition.scorekeeper.data.cache.database.contract.MatchTeamContract;
import com.transition.scorekeeper.data.entity.TeamEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class MatchTeamProvider extends BaseProvider {
    public static final Uri CONTENT_URI = MatchTeamContract.Entry.CONTENT_URI;

    @Override
    protected String getTableName() {
        return MatchTeamContract.Entry.TABLE_NAME;
    }

    @Override
    protected UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MatchTeamContract.CONTENT_AUTHORITY, MatchTeamContract.PATH, MATCH_TEAM);
        return matcher;
    }

    @Override
    protected Uri getUri(long id) {
        return MatchTeamContract.Entry.buildUri(id);
    }

    public Long saveOrUpdate(Long idMatch, Long idTeam) {
        Long id = null;
        Uri insert = insert(CONTENT_URI, MatchTeamContract.getContentValues(idMatch, idTeam));
        if (insert != null) {
            id = Long.valueOf(insert.getLastPathSegment());
        }
        return id;
    }

    public HashSet<TeamEntity> getTeamsByMatch(Long matchId) {
        TeamPlayerProvider teamPlayerProvider = new TeamPlayerProvider();
        HashSet<TeamEntity> teams = new HashSet<>();
        String selection = MatchTeamContract.Entry.COLUMN_MATCH + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(matchId.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Long idTeam = MatchTeamContract.getTeamId(cursor);
                TeamEntity teamEntity = new TeamEntity();
                teamEntity.setId(idTeam);
                teamEntity.setPlayers(teamPlayerProvider.getPlayersByTeam(idTeam));
                teams.add(teamEntity);
            }
            cursor.close();
        }
        return teams;
    }

    public List<Long> getTeamsIdsByMatch(Long matchId) {
        List<Long> teams = new ArrayList<>();
        String selection = MatchTeamContract.Entry.COLUMN_MATCH + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(matchId.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Long idTeam = MatchTeamContract.getTeamId(cursor);
                teams.add(idTeam);
            }
            cursor.close();
        }
        return teams;
    }

    public void remove(Long matchId, Long teamId) {
        String selection = MatchTeamContract.Entry.COLUMN_MATCH + "= ? AND " +
                MatchTeamContract.Entry.COLUMN_TEAM + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(matchId.toString());
        list.add(teamId.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        delete(CONTENT_URI, selection, selectionArgs);
    }
}
