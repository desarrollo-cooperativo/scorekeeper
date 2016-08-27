package com.transition.scorekeeper.data.cache.database.contract;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.transition.scorekeeper.data.entity.PlayerEntity;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class PlayerContract extends BaseContract {
    public static final String PATH = "player";

    public static ContentValues getContentValues(PlayerEntity playerEntity) {
        ContentValues cv = new ContentValues();
        cv.put(Entry._ID, playerEntity.getId());
        cv.put(Entry.COLUMN_NAME, playerEntity.getName());
        cv.put(Entry.COLUMN_PREFERENTIAL_POSITION, playerEntity.getPreferentialPosition());
        return cv;
    }

    public static PlayerEntity getPlayerEntity(Cursor cursor) {
        PlayerEntity playerEntity = new PlayerEntity();
        int index = cursor.getColumnIndex(Entry._ID);
        if (index != -1) {
            playerEntity.setId(cursor.getLong(index));
        }
        index = cursor.getColumnIndex(Entry.COLUMN_NAME);
        if (index != -1) {
            playerEntity.setName(cursor.getString(index));
        }
        index = cursor.getColumnIndex(Entry.COLUMN_PREFERENTIAL_POSITION);
        if (index != -1) {
            playerEntity.setPreferentialPosition(cursor.getInt(index));
        }
        return playerEntity;
    }

    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "player";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PREFERENTIAL_POSITION = "preferential_position";

        public static String getCreateExecSQL() {
            return "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY, " +
                    Entry.COLUMN_NAME + " TEXT, " +
                    Entry.COLUMN_PREFERENTIAL_POSITION + " INTEGER);";
        }

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
