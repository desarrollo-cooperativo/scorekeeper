package com.transition.scorekeeper.mobile.model.mapper;

import android.content.Context;

import com.transition.scorekeeper.domain.domain.Goal;
import com.transition.scorekeeper.domain.domain.Match;
import com.transition.scorekeeper.mobile.model.GoalModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 27/05/16
 */
public class GoalModelDataMapper {
    private static GoalModel transform(Context context, Match match, Goal goal) {
        return new GoalModel.GoalModelBuilder(context)
                .setId(goal.getId())
                .setDate(goal.getDate())
                .setPosition(goal.getPosition())
                .setMatchStart(match.getStart())
                .setPlayer(PlayerModelDataMapper.transform(match, goal.getPlayer()))
                .build();
    }

    public static List<GoalModel> transform(Context context, Match match) {
        List<GoalModel> goalModelCollection;
        List<Goal> goals = match.getGoals();
        if (goals != null && !goals.isEmpty()) {
            goalModelCollection = new ArrayList<>();
            for (Goal goal : goals) {
                goalModelCollection.add(transform(context, match, goal));
            }
        } else {
            goalModelCollection = new ArrayList<>();
        }
        return goalModelCollection;
    }

    public static List<Goal> transformAll(List<GoalModel> goals) {
        ArrayList<Goal> goalModelCollection;
        if (goals != null && !goals.isEmpty()) {
            goalModelCollection = new ArrayList<>();
            for (GoalModel goalModel : goals) {
                goalModelCollection.add(transform(goalModel));
            }
        } else {
            goalModelCollection = new ArrayList<>();
        }
        return goalModelCollection;
    }

    private static Goal transform(GoalModel goalModel) {
        Goal goal = new Goal();
        goal.setId(goalModel.getId());
        goal.setDate(goalModel.getDate());
        goal.setPlayer(PlayerModelDataMapper.transform(goalModel.getPlayer()));
        goal.setPosition(goalModel.getPlayerPosition());
        return goal;
    }
}
