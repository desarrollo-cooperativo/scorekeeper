package com.transition.scorekeeper.data.net;

import com.transition.scorekeeper.data.entity.MatchEntity;

import java.util.List;

import rx.Observable;

public interface RestApiMatch extends RestApi {
    String API_URL_GET_MATCHES = API_BASE_URL + "matches.json";

    Observable<List<MatchEntity>> matchesEntities();
}
