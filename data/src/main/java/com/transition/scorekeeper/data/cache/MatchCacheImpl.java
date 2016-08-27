package com.transition.scorekeeper.data.cache;

import android.content.Context;

import com.transition.scorekeeper.data.cache.database.provider.GoalProvider;
import com.transition.scorekeeper.data.cache.database.provider.MatchProvider;
import com.transition.scorekeeper.data.cache.database.provider.MatchTeamProvider;
import com.transition.scorekeeper.data.cache.database.provider.TeamPlayerProvider;
import com.transition.scorekeeper.data.cache.database.provider.TeamProvider;
import com.transition.scorekeeper.data.entity.GoalEntity;
import com.transition.scorekeeper.data.entity.MatchEntity;
import com.transition.scorekeeper.data.entity.PlayerEntity;
import com.transition.scorekeeper.data.entity.TeamEntity;
import com.transition.scorekeeper.data.exception.MatchNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class MatchCacheImpl extends CacheImpl implements MatchCache {
    private final MatchProvider provider;

    @Inject
    public MatchCacheImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.provider = new MatchProvider();
    }

    @Override
    public Observable<List<MatchEntity>> get() {
        return Observable.create(subscriber -> {
            subscriber.onNext(provider.getMatches());
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<MatchEntity> get(final Long matchId) {
        return Observable.create(subscriber -> {
            MatchEntity matchEntity = provider.getMatch(matchId);
            if (matchEntity != null) {
                subscriber.onNext(matchEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new MatchNotFoundException());
            }
        });
    }

    @Override
    public Observable<Long> insert(MatchEntity matchEntity) {
        return Observable.create(subscriber -> {
            subscriber.onNext(provider.saveOrUpdate(matchEntity));
            subscriber.onCompleted();
        });
    }

    @Override
    public Observable<MatchEntity> update(MatchEntity matchEntity) {
        return Observable.create(subscriber -> {
            provider.saveOrUpdate(matchEntity);
            updateTeams(matchEntity);
            updateGoals(matchEntity);
            MatchEntity matchUpdated = provider.getMatch(matchEntity.getId());
            subscriber.onNext(matchUpdated);
            subscriber.onCompleted();
        });
    }

    private void updateGoals(MatchEntity matchEntity) {
        GoalProvider goalProvider = new GoalProvider();
        for (Object goal : matchEntity.getGoals()) {
            GoalEntity goalEntity = (GoalEntity) goal;
            if (goalEntity.getId() == null) {
                Long idGoal = goalProvider.saveOrUpdate(matchEntity.getId(), goalEntity);
                goalEntity.setId(idGoal);
            }
        }
    }

    private void updateTeams(MatchEntity matchEntity) {
        List<Long> teamsIds = new ArrayList<>();
        MatchTeamProvider matchTeamProvider = new MatchTeamProvider();
        TeamPlayerProvider teamPlayerProvider = new TeamPlayerProvider();
        TeamProvider teamProvider = new TeamProvider();
        for (Object team : matchEntity.getTeams()) {
            TeamEntity teamEntity = (TeamEntity) team;
            HashSet<PlayerEntity> players = teamEntity.getPlayers();
            List<Long> playersIds = getPlayersIds(players);
            Long idTeam = teamProvider.getTeamByPlayers(playersIds);
            if (idTeam == null) {
                teamEntity.setId(null);
                idTeam = teamProvider.saveOrUpdate(teamEntity);
                for (Long playerId : playersIds) {
                    teamPlayerProvider.saveOrUpdate(idTeam, playerId);
                }
            }
            teamsIds.add(idTeam);
        }
        Long matchId = matchEntity.getId();
        List<Long> teamsIdsByMatch = matchTeamProvider.getTeamsIdsByMatch(matchId);
        for (Long teamId : teamsIdsByMatch) {
            if (!teamsIds.contains(teamId)) {
                matchTeamProvider.remove(matchId, teamId);
                teamsIds.remove(teamId);
            }
        }
        for (Long teamId : teamsIds) {
            matchTeamProvider.saveOrUpdate(matchId, teamId);
        }
    }

    public List<Long> getPlayersIds(HashSet<PlayerEntity> players) {
        Iterator<PlayerEntity> iterator = players.iterator();
        List<Long> playersIds = new ArrayList<>();
        while (iterator.hasNext()) {
            PlayerEntity playerEntity = iterator.next();
            Long id = playerEntity.getId();
            if (id != null) {
                playersIds.add(id);
            }
        }
        return playersIds;
    }
}
