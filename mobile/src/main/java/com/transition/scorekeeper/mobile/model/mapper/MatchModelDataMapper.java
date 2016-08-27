package com.transition.scorekeeper.mobile.model.mapper;

import android.content.Context;

import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.mobile.internal.di.PerActivity;
import com.transition.scorekeeper.mobile.model.MatchModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
@PerActivity
public class MatchModelDataMapper {

    @Inject
    public MatchModelDataMapper() {
    }

    public static Match transform(MatchModel matchModel) {
        Match match = new Match();
        match.setId(matchModel.getId());
        match.setStart(matchModel.getMatchStart());
        match.setEnd(matchModel.getMatchEnd());
        match.setMaxGoals(matchModel.getMaxGoals());
        match.setMaxMinutes(matchModel.getMaxMinutes());
        match.setTeams(TeamModelDataMapper.transformAll(matchModel.getTeams()));
        match.setGoals(GoalModelDataMapper.transformAll(matchModel.getGoals()));
        return match;
    }

    public MatchModel transform(Context context, Match match) {
        if (match == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        MatchModel matchModel = new MatchModel();
        matchModel.setId(match.getId());
        matchModel.setMatchStart(match.getStart());
        matchModel.setMatchEnd(match.getEnd());
        matchModel.setTeams(TeamModelDataMapper.transform(match));
        matchModel.setMaxGoals(match.getMaxGoals());
        matchModel.setMaxMinutes(match.getMaxMinutes());
        matchModel.setGoals(GoalModelDataMapper.transform(context, match));
        return matchModel;
    }

    public Collection<MatchModel> transform(Context context, Collection<Match> matchCollection) {
        Collection<MatchModel> matchModelsCollection;

        if (matchCollection != null && !matchCollection.isEmpty()) {
            matchModelsCollection = new ArrayList<>();
            for (Match match : matchCollection) {
                matchModelsCollection.add(transform(context, match));
            }
        } else {
            matchModelsCollection = Collections.emptyList();
        }

        return matchModelsCollection;
    }
}
