package com.transition.scorekeeper.data.cache.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class MatchTeamContract extends BaseContract {
    public static final String PATH = "match_team";

    public static ContentValues getContentValues(Long idMatch, Long idTeam) {
        ContentValues cv = new ContentValues();
        cv.put(Entry.COLUMN_MATCH, idMatch);
        cv.put(Entry.COLUMN_TEAM, idTeam);
        return cv;
    }

    public static Long getTeamId(Cursor cursor) {
        Long idTeam = null;
        int index = cursor.getColumnIndex(Entry.COLUMN_TEAM);
        if (index != -1) {
            idTeam = cursor.getLong(index);
        }
        return idTeam;
    }

    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "match_team";

        public static final String COLUMN_MATCH = "id_match";
        public static final String COLUMN_TEAM = "id_team";

        public static String getCreateExecSQL() {
            return "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_MATCH + " LONG, " +
                    Entry.COLUMN_TEAM + " LONG);";
        }

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
