package com.transition.scorekeeper.data.cache.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.transition.scorekeeper.data.cache.database.provider.PlayerProvider;
import com.transition.scorekeeper.data.entity.GoalEntity;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class GoalContract extends BaseContract {
    public static final String PATH = "goal";

    public static ContentValues getContentValues(Long idMatch, GoalEntity goalEntity) {
        ContentValues cv = new ContentValues();
        cv.put(Entry.COLUMN_DATE, goalEntity.getDate().getTime());
        cv.put(Entry.COLUMN_POSITION, goalEntity.getPosition());
        cv.put(Entry.COLUMN_PLAYER, goalEntity.getPlayer().getId());
        cv.put(Entry.COLUMN_MATCH, idMatch);
        return cv;
    }

    public static GoalEntity getGoal(Cursor cursor) {
        GoalEntity goalEntity = new GoalEntity();
        int index = cursor.getColumnIndex(Entry._ID);
        if (index != -1) {
            goalEntity.setId(cursor.getLong(index));
        }
        index = cursor.getColumnIndex(Entry.COLUMN_DATE);
        if (index != -1) {
            long milliseconds = cursor.getLong(index);
            if (milliseconds != 0) {
                goalEntity.setDate(new Date(milliseconds));
            }
        }
        index = cursor.getColumnIndex(Entry.COLUMN_POSITION);
        if (index != -1) {
            goalEntity.setPosition(cursor.getInt(index));
        }
        index = cursor.getColumnIndex(Entry.COLUMN_PLAYER);
        if (index != -1) {
            goalEntity.setPlayer(new PlayerProvider().getPlayer(cursor.getLong(index)));
        }
        return goalEntity;
    }

    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "goal";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_POSITION = "position";
        public static final String COLUMN_MATCH = "id_match";
        public static final String COLUMN_PLAYER = "id_player";

        public static String getCreateExecSQL() {
            return "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_DATE + " LONG, " +
                    Entry.COLUMN_POSITION + " INTEGER, " +
                    Entry.COLUMN_PLAYER + " INTEGER, " +
                    Entry.COLUMN_MATCH + " INTEGER);";
        }

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
