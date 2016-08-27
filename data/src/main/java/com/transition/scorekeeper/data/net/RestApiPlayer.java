package com.transition.scorekeeper.data.net;

import com.transition.scorekeeper.data.entity.PlayerEntity;

import java.util.List;

import rx.Observable;

public interface RestApiPlayer extends RestApi {

    String API_URL_GET_PLAYERS = API_BASE_URL + "players.json";

    Observable<List<PlayerEntity>> playersEntities();
}
