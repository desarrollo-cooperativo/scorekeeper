package com.transition.scorekeeper.domain.repository;

import com.transition.scorekeeper.domain.domain.Player;

import java.util.List;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 29/05/16
 */
public interface PlayerRepository {
    Observable<List<Player>> players();

    Observable<Long> putPlayer(Player player);
}
