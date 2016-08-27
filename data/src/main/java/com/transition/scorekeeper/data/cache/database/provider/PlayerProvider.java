package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.transition.scorekeeper.data.cache.database.contract.PlayerContract;
import com.transition.scorekeeper.data.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class PlayerProvider extends BaseProvider {
    public static final Uri CONTENT_URI = PlayerContract.Entry.CONTENT_URI;

    @Override
    protected String getTableName() {
        return PlayerContract.Entry.TABLE_NAME;
    }

    @Override
    protected UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(PlayerContract.CONTENT_AUTHORITY, PlayerContract.PATH, PLAYER);
        return matcher;
    }

    @Override
    protected Uri getUri(long id) {
        return PlayerContract.Entry.buildUri(id);
    }

    public Long saveOrUpdate(PlayerEntity playerEntity) {
        Long id = playerEntity.getId();
        Uri insert = insert(CONTENT_URI, PlayerContract.getContentValues(playerEntity));
        if (insert != null) {
            id = Long.valueOf(insert.getLastPathSegment());
            playerEntity.setId(id);
        }
        return id;
    }

    public List<PlayerEntity> getPlayers() {
        Cursor cursor = query(CONTENT_URI, null, null, null, null);
        List<PlayerEntity> players = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                players.add(PlayerContract.getPlayerEntity(cursor));
            }
            cursor.close();
        }
        return players;
    }

    public PlayerEntity getPlayer(Long playerId) {
        String selection = PlayerContract.Entry._ID + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(playerId.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);
        PlayerEntity playerEntity = null;

        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                playerEntity = PlayerContract.getPlayerEntity(cursor);
            }
            cursor.close();
        }
        return playerEntity;
    }
}
