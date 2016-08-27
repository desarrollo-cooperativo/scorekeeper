package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.transition.scorekeeper.data.cache.database.contract.MatchTeamContract;
import com.transition.scorekeeper.data.cache.database.contract.TeamPlayerContract;
import com.transition.scorekeeper.data.entity.PlayerEntity;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author diego.rotondale
 * @since 01/08/16
 */
public class TeamPlayerProvider extends BaseProvider {
    public static final Uri CONTENT_URI = TeamPlayerContract.Entry.CONTENT_URI;

    @Override
    protected String getTableName() {
        return TeamPlayerContract.Entry.TABLE_NAME;
    }

    @Override
    protected UriMatcher getUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MatchTeamContract.CONTENT_AUTHORITY, TeamPlayerContract.PATH, TEAM_PLAYER);
        return matcher;
    }

    @Override
    protected Uri getUri(long id) {
        return MatchTeamContract.Entry.buildUri(id);
    }

    public Long saveOrUpdate(Long idTeam, Long idPlayer) {
        Long id = null;
        Uri insert = insert(CONTENT_URI, TeamPlayerContract.getContentValues(idTeam, idPlayer));
        if (insert != null) {
            id = Long.valueOf(insert.getLastPathSegment());
        }
        return id;
    }

    public HashSet<PlayerEntity> getPlayersByTeam(Long idTeam) {
        PlayerProvider playerProvider = new PlayerProvider();
        HashSet<PlayerEntity> players = new HashSet<>();
        String selection = TeamPlayerContract.Entry.COLUMN_TEAM + "= ?";
        final ArrayList<String> list = new ArrayList<>();
        list.add(idTeam.toString());
        final String[] selectionArgs = list.toArray(new String[list.size()]);

        Cursor cursor = query(CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Long idPlayer = TeamPlayerContract.getPlayerId(cursor);
                players.add(playerProvider.getPlayer(idPlayer));
            }
            cursor.close();
        }
        return players;
    }

    public Long getTeamByPlayersIds(String[] playersIds) {
        Long id = null;
        String selection = TeamPlayerContract.Entry.COLUMN_PLAYER + " IN (?)";
        int size = playersIds.length;
        String groupBy = TeamPlayerContract.Entry.COLUMN_TEAM;
        String having = "COUNT(DISTINCT " + TeamPlayerContract.Entry.COLUMN_TEAM + ") = " + size;

        String[] ids = new String[1];
        ids[0] = TextUtils.join(",", playersIds);

        Cursor cursor = query(CONTENT_URI, null, selection, ids, groupBy, having, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                id = TeamPlayerContract.getPlayerId(cursor);
            }
            cursor.close();
        }
        return id;
    }
}
