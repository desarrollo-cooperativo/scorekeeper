package com.transition.scorekeeper.data.cache.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.transition.scorekeeper.data.entity.TeamEntity;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class TeamContract extends BaseContract {
    public static final String PATH = "team";

    public static TeamEntity getTeamEntity(Cursor cursor) {
        TeamEntity teamEntity = new TeamEntity();
        int index = cursor.getColumnIndex(Entry._ID);
        if (index != -1) {
            teamEntity.setId(cursor.getLong(index));
        }
        return teamEntity;
    }

    public static ContentValues getContentValues(TeamEntity teamEntity) {
        ContentValues cv = new ContentValues();
        cv.put(Entry._ID, teamEntity.getId());
        return cv;
    }

    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "team";

        public static String getCreateExecSQL() {
            return "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY);";
        }

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
