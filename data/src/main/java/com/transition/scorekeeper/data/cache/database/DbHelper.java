package com.transition.scorekeeper.data.cache.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.transition.scorekeeper.data.cache.database.contract.GoalContract;
import com.transition.scorekeeper.data.cache.database.contract.MatchContract;
import com.transition.scorekeeper.data.cache.database.contract.MatchTeamContract;
import com.transition.scorekeeper.data.cache.database.contract.PlayerContract;
import com.transition.scorekeeper.data.cache.database.contract.TeamContract;
import com.transition.scorekeeper.data.cache.database.contract.TeamPlayerContract;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "scorekeeper_db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MatchContract.Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamContract.Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MatchTeamContract.Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GoalContract.Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlayerContract.Entry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TeamPlayerContract.Entry.TABLE_NAME);
        onCreate(db);
    }

    public void onDeleteDatabase() {
        final SQLiteDatabase database = this.getWritableDatabase();
        database.delete(MatchContract.Entry.TABLE_NAME, null, null);
        database.delete(TeamContract.Entry.TABLE_NAME, null, null);
        database.delete(MatchTeamContract.Entry.TABLE_NAME, null, null);
        database.delete(GoalContract.Entry.TABLE_NAME, null, null);
        database.delete(PlayerContract.Entry.TABLE_NAME, null, null);
        database.delete(TeamPlayerContract.Entry.TABLE_NAME, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MatchContract.Entry.getCreateExecSQL());
        db.execSQL(TeamContract.Entry.getCreateExecSQL());
        db.execSQL(MatchTeamContract.Entry.getCreateExecSQL());
        db.execSQL(GoalContract.Entry.getCreateExecSQL());
        db.execSQL(PlayerContract.Entry.getCreateExecSQL());
        db.execSQL(TeamPlayerContract.Entry.getCreateExecSQL());
    }
}
