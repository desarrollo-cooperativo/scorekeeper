package com.transition.scorekeeper.data.entity.mapper;

import com.transition.scorekeeper.data.entity.GoalEntity;
import com.transition.scorekeeper.domain.domain.Goal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoalEntityDataMapper {

    @Inject
    public GoalEntityDataMapper() {
    }

    public static Goal transform(GoalEntity goalEntity) {
        Goal goal = null;
        if (goalEntity != null) {
            goal = new Goal();
            goal.setId(goalEntity.getId());
            goal.setDate(goalEntity.getDate());
            goal.setPlayer(PlayerEntityDataMapper.transform(goalEntity.getPlayer()));
            goal.setPosition(goalEntity.getPosition());
        }
        return goal;
    }

    public static List<Goal> transform(List<GoalEntity> goalsEntityCollection) {
        List<Goal> goals = new ArrayList<>();
        Goal goal;
        for (GoalEntity goalEntity : goalsEntityCollection) {
            goal = transform(goalEntity);
            if (goal != null) {
                goals.add(goal);
            }
        }
        return goals;
    }

    public static List<GoalEntity> transformFromGoal(List<Goal> goals) {
        ArrayList<GoalEntity> goalEntities = new ArrayList<>();
        for (Goal goal : goals) {
            GoalEntity goalEntity = new GoalEntity();
            goalEntity.setId(goal.getId());
            goalEntity.setPlayer(PlayerEntityDataMapper.transform(goal.getPlayer()));
            goalEntity.setDate(goal.getDate());
            goalEntity.setPosition(goal.getPosition());
            goalEntities.add(goalEntity);
        }
        return goalEntities;
    }
}
