package com.transition.scorekeeper.data.net;

import android.content.Context;

import com.transition.scorekeeper.data.entity.MatchEntity;
import com.transition.scorekeeper.data.entity.mapper.json.MatchEntityJsonMapper;
import com.transition.scorekeeper.data.exception.NetworkConnectionException;

import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;

public class RestApiMatchImpl extends RestApiImpl implements RestApiMatch {
    private final MatchEntityJsonMapper matchEntityJsonMapper;

    public RestApiMatchImpl(Context context, MatchEntityJsonMapper matchEntityJsonMapper) {
        if (context == null || matchEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.matchEntityJsonMapper = matchEntityJsonMapper;
    }

    @Override
    public Observable<List<MatchEntity>> matchesEntities() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseMatchesEntities = getMatchEntitiesFromApi();
                    if (responseMatchesEntities != null) {
                        subscriber.onNext(matchEntityJsonMapper.transformMatchEntityCollection(
                                responseMatchesEntities));
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

    private String getMatchEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApiMatch.API_URL_GET_MATCHES).requestSyncCall();
    }
}
