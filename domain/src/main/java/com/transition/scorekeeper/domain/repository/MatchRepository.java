package com.transition.scorekeeper.domain.repository;

import com.transition.scorekeeper.domain.domain.Match;

import java.util.List;

import rx.Observable;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public interface MatchRepository {
    Observable<List<Match>> matches();

    Observable<Long> putMatch(Match match);

    Observable<Match> postMatch(Match match);
}
