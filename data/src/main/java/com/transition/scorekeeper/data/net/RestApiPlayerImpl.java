package com.transition.scorekeeper.data.net;

import android.content.Context;

import com.transition.scorekeeper.data.entity.PlayerEntity;
import com.transition.scorekeeper.data.entity.mapper.json.PlayerEntityJsonMapper;
import com.transition.scorekeeper.data.exception.NetworkConnectionException;

import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;

public class RestApiPlayerImpl extends RestApiImpl implements RestApiPlayer {
    private final PlayerEntityJsonMapper playerEntityJsonMapper;

    public RestApiPlayerImpl(Context context, PlayerEntityJsonMapper playerEntityJsonMapper) {
        if (context == null || playerEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.playerEntityJsonMapper = playerEntityJsonMapper;
    }

    @Override
    public Observable<List<PlayerEntity>> playersEntities() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responsePlayersEntities = getPlayerEntitiesFromApi();
                    if (responsePlayersEntities != null) {
                        subscriber.onNext(playerEntityJsonMapper.transformPlayerEntityCollection(
                                responsePlayersEntities));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getMessage(), e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    private String getPlayerEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApiPlayer.API_URL_GET_PLAYERS).requestSyncCall();
    }
}
