package com.transition.scorekeeper.data.cache.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.transition.scorekeeper.data.cache.database.DbHelper;
import com.transition.scorekeeper.data.cache.database.contract.GoalContract;
import com.transition.scorekeeper.data.cache.database.contract.MatchContract;
import com.transition.scorekeeper.data.cache.database.contract.MatchTeamContract;
import com.transition.scorekeeper.data.cache.database.contract.PlayerContract;
import com.transition.scorekeeper.data.cache.database.contract.TeamContract;
import com.transition.scorekeeper.data.cache.database.contract.TeamPlayerContract;

public abstract class DbProvider extends ContentProvider {
    public static final int MATCH = 100;
    public static final int PLAYER = 101;
    public static final int TEAM = 102;
    public static final int TEAM_PLAYER = 103;
    public static final int MATCH_TEAM = 104;
    public static final int GOAL = 105;

    private static final String LOG_TAG = DbProvider.class.getSimpleName();

    protected static DbHelper dbHelper;
    protected static Context context;

    protected SQLiteOpenHelper getDbHelper() {
        if (dbHelper == null) {
            context = getContext();
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    @Override
    public boolean onCreate() {
        getDbHelper();
        return false;
    }

    protected abstract String getTableName();

    @Override
    public String getType(Uri uri) {
        final int match = getUriMatcher().match(uri);
        switch (match) {
            case MATCH:
                return MatchContract.Entry.CONTENT_TYPE;
            case PLAYER:
                return PlayerContract.Entry.CONTENT_TYPE;
            case TEAM_PLAYER:
                return TeamPlayerContract.Entry.CONTENT_TYPE;
            case TEAM:
                return TeamContract.Entry.CONTENT_TYPE;
            case MATCH_TEAM:
                return MatchTeamContract.Entry.CONTENT_TYPE;
            case GOAL:
                return GoalContract.Entry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    protected abstract UriMatcher getUriMatcher();

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = getDbHelper().getWritableDatabase();
        final int match = getUriMatcher().match(uri);
        Uri returnUri;
        switch (match) {
            case MATCH_TEAM:
            case TEAM_PLAYER:
            case TEAM:
            case PLAYER:
            case GOAL:
            case MATCH: {
                long _id = db.insertWithOnConflict(getTableName(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
                if (_id > 0) {
                    returnUri = getUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        context.getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    protected abstract Uri getUri(long id);

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = getDbHelper().getWritableDatabase();
        final int match = getUriMatcher().match(uri);
        switch (match) {
            case MATCH_TEAM:
            case TEAM_PLAYER:
            case TEAM:
            case PLAYER:
            case GOAL:
            case MATCH: {
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        if (value != null) {
                            long _id = db.insertWithOnConflict(getTableName(), null, value, SQLiteDatabase.CONFLICT_REPLACE);
                            if (_id != -1) {
                                returnCount++;
                            }
                        }
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage());
                } finally {
                    db.endTransaction();
                }
                context.getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return query(uri, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        Cursor retCursor;
        switch (getUriMatcher().match(uri)) {
            case MATCH_TEAM:
            case TEAM_PLAYER:
            case TEAM:
            case PLAYER:
            case GOAL:
            case MATCH:
                retCursor = getDbHelper().getReadableDatabase().query(
                        getTableName(),
                        projection,
                        selection,
                        selectionArgs,
                        groupBy,
                        having,
                        orderBy,
                        limit
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(context.getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = getDbHelper().getWritableDatabase();

        int deleteCount = db.delete(getTableName(), selection, selectionArgs);

        context.getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }
}
