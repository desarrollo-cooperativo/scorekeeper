package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.transition.scorekeeper.data.cache.database.contract.MatchContract;
import com.transition.scorekeeper.data.entity.MatchEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class MatchProvider extends BaseProvider {
    public static final Uri CONTENT_URI = MatchContract.Entry.CONTENT_URI;

    @Override
    protected String getTableName() {
        return MatchContract.Entry.TABLE_NAME;
    }

    @Override
    protected UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MatchContract.CONTENT_AUTHORITY, MatchContract.PATH, MATCH);
        return matcher;
    }

    @Override
    protected Uri getUri(long id) {
        return MatchContract.Entry.buildUri(id);
    }

    public Long saveOrUpdate(MatchEntity matchEntity) {
        Long id = matchEntity.getId();
        Uri insert = insert(CONTENT_URI, MatchContract.getContentValues(matchEntity));
        if (insert != null) {
            id = Long.valueOf(insert.getLastPathSegment());
            matchEntity.setId(id);
        }
        return id;
    }

    public List<MatchEntity> getMatches() {
        GoalProvider goalProvider = new GoalProvider();
        MatchTeamProvider matchTeamProvider = new MatchTeamProvider();
        Cursor cursor = query(CONTENT_URI, null, null, null, null);
        List<MatchEntity> matches = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MatchEntity matchEntity = MatchContract.getMatchEntity(cursor);
                matchEntity.setTeams(matchTeamProvider.getTeamsByMatch(matchEntity.getId()));
                matchEntity.setGoals(goalProvider.getGoalsByMatch(matchEntity.getId()));
                matches.add(matchEntity);
            }
            cursor.close();
        }
        return matches;
    }

    public MatchEntity getMatch(Long matchId) {
        MatchTeamProvider matchTeamProvider = new MatchTeamProvider();
        GoalProvider goalProvider = new GoalProvider();

        String selection = MatchContract.Entry._ID + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(matchId.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        MatchEntity matchEntity = null;

        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                matchEntity = MatchContract.getMatchEntity(cursor);
                matchEntity.setTeams(matchTeamProvider.getTeamsByMatch(matchEntity.getId()));
                matchEntity.setGoals(goalProvider.getGoalsByMatch(matchEntity.getId()));
            }
            cursor.close();
        }
        return matchEntity;
    }
}
