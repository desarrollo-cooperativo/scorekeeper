package com.transition.scorekeeper.data.entity.mapper;

import com.transition.scorekeeper.data.entity.MatchEntity;
import com.transition.scorekeeper.domain.domain.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MatchEntityDataMapper {

    @Inject
    public MatchEntityDataMapper() {
    }

    public Match transform(MatchEntity matchEntity) {
        Match match = null;
        if (matchEntity != null) {
            match = new Match();
            match.setId(matchEntity.getId());
            match.setTeams(TeamEntityDataMapper.transform(matchEntity.getTeams()));
            match.setGoals(GoalEntityDataMapper.transform(matchEntity.getGoals()));
            match.setMaxMinutes(matchEntity.getMaxMinutes());
            match.setMaxGoals(matchEntity.getMaxGoals());
            match.setStart(matchEntity.getStart());
            match.setEnd(matchEntity.getEnd());
        }
        return match;
    }

    public List<Match> transform(Collection<MatchEntity> matchEntityCollection) {
        List<Match> matches = new ArrayList<>();
        Match match;
        for (MatchEntity matchEntity : matchEntityCollection) {
            match = transform(matchEntity);
            if (match != null) {
                matches.add(match);
            }
        }
        return matches;
    }

    public MatchEntity transform(Match match) {
        MatchEntity matchEntity = new MatchEntity();
        if (match != null) {
            matchEntity.setId(match.getId());
            matchEntity.setStart(match.getStart());
            matchEntity.setEnd(match.getEnd());
            matchEntity.setMaxMinutes(match.getMaxMinutes());
            matchEntity.setMaxGoals(match.getMaxGoals());
            matchEntity.setTeams(TeamEntityDataMapper.transformFromTeam(match.getTeams()));
            matchEntity.setGoals(GoalEntityDataMapper.transformFromGoal(match.getGoals()));
        }
        return matchEntity;
    }
}
