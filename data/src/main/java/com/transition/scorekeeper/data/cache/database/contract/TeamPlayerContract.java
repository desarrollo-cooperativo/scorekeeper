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
public class TeamPlayerContract extends BaseContract {
    public static final String PATH = "team_player";

    public static ContentValues getContentValues(Long idTeam, Long idPlayer) {
        ContentValues cv = new ContentValues();
        cv.put(Entry.COLUMN_TEAM, idTeam);
        cv.put(Entry.COLUMN_PLAYER, idPlayer);
        return cv;
    }

    public static Long getPlayerId(Cursor cursor) {
        Long idPlayer = null;
        int index = cursor.getColumnIndex(Entry.COLUMN_PLAYER);
        if (index != -1) {
            idPlayer = cursor.getLong(index);
        }
        return idPlayer;
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

        public static final String TABLE_NAME = "team_player";

        public static final String COLUMN_TEAM = "id_team";
        public static final String COLUMN_PLAYER = "id_player";

        public static String getCreateExecSQL() {
            return "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_TEAM + " LONG, " +
                    Entry.COLUMN_PLAYER + " LONG);";
        }

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
