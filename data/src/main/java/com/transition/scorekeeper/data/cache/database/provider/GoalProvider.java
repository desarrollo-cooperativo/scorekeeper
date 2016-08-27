package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.transition.scorekeeper.data.cache.database.contract.GoalContract;
import com.transition.scorekeeper.data.cache.database.contract.TeamContract;
import com.transition.scorekeeper.data.entity.GoalEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class GoalProvider extends BaseProvider {
    public static final Uri CONTENT_URI = GoalContract.Entry.CONTENT_URI;

    @Override
    protected String getTableName() {
        return GoalContract.Entry.TABLE_NAME;
    }

    @Override
    protected UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(GoalContract.CONTENT_AUTHORITY, GoalContract.PATH, GOAL);
        return matcher;
    }

    @Override
    protected Uri getUri(long id) {
        return TeamContract.Entry.buildUri(id);
    }

    public Long saveOrUpdate(Long idMatch, GoalEntity goalEntity) {
        Long id = goalEntity.getId();
        Uri insert = insert(CONTENT_URI, GoalContract.getContentValues(idMatch, goalEntity));
        if (insert != null) {
            id = Long.valueOf(insert.getLastPathSegment());
            goalEntity.setId(id);
        }
        return id;
    }

    public List<GoalEntity> getGoalsByMatch(Long idMatch) {
        List<GoalEntity> goals = new ArrayList<>();
        String selection = GoalContract.Entry.COLUMN_MATCH + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(idMatch.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                GoalEntity goal = GoalContract.getGoal(cursor);
                goals.add(goal);
            }
            cursor.close();
        }
        return goals;
    }
}
