package com.transition.scorekeeper.data.cache.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.transition.scorekeeper.data.entity.MatchEntity;

import java.util.Date;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class MatchContract extends BaseContract {
    public static final String PATH = "match";

    public static ContentValues getContentValues(MatchEntity matchEntity) {
        ContentValues cv = new ContentValues();
        cv.put(Entry._ID, matchEntity.getId());
        cv.put(Entry.COLUMN_START, matchEntity.getStartTime());
        cv.put(Entry.COLUMN_END, matchEntity.getEndTime());
        cv.put(Entry.COLUMN_MAX_MINUTES, matchEntity.getMaxMinutes());
        cv.put(Entry.COLUMN_MAX_GOALS, matchEntity.getMaxGoals());
        return cv;
    }

    public static MatchEntity getMatchEntity(Cursor cursor) {
        MatchEntity matchEntity = new MatchEntity();
        int index = cursor.getColumnIndex(Entry._ID);
        if (index != -1) {
            matchEntity.setId(cursor.getLong(index));
        }
        index = cursor.getColumnIndex(Entry.COLUMN_START);
        if (index != -1) {
            long milliseconds = cursor.getLong(index);
            if (milliseconds != 0) {
                matchEntity.setStart(new Date(milliseconds));
            }
        }
        index = cursor.getColumnIndex(Entry.COLUMN_END);
        if (index != -1) {
            long milliseconds = cursor.getLong(index);
            if (milliseconds != 0) {
                matchEntity.setEnd(new Date(milliseconds));
            }
        }
        index = cursor.getColumnIndex(Entry.COLUMN_MAX_MINUTES);
        if (index != -1) {
            int maxMinutes = cursor.getInt(index);
            if (maxMinutes != 0) {
                matchEntity.setMaxMinutes(maxMinutes);
            }
        }
        index = cursor.getColumnIndex(Entry.COLUMN_MAX_GOALS);
        if (index != -1) {
            int maxGoals = cursor.getInt(index);
            if (maxGoals != 0) {
                matchEntity.setMaxGoals(cursor.getInt(index));
            }
        }
        return matchEntity;
    }

    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "match";

        public static final String COLUMN_START = "start";
        public static final String COLUMN_END = "end";
        public static final String COLUMN_MAX_MINUTES = "max_minutes";
        public static final String COLUMN_MAX_GOALS = "max_goals";

        public static String getCreateExecSQL() {
            return "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_START + " LONG, " +
                    Entry.COLUMN_END + " LONG, " +
                    Entry.COLUMN_MAX_MINUTES + " INTEGER, " +
                    Entry.COLUMN_MAX_GOALS + " INTEGER);";
        }

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
