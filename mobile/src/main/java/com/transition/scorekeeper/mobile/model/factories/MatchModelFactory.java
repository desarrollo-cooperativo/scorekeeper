package com.transition.scorekeeper.mobile.model.factories;

import com.transition.scorekeeper.domain.Constants;
import com.transition.scorekeeper.mobile.model.MatchModel;

/**
 * @author diego.rotondale
 * @since 05/06/16
 */
public class MatchModelFactory {
    public static MatchModel getNewMatch() {
        MatchModel matchModel = new MatchModel();
        matchModel.setId(100L);
        matchModel.setMaxMinutes(Constants.MatchConstants.MAX_MINUTES);
        matchModel.setMaxGoals(Constants.MatchConstants.MAX_GOALS);
        matchModel.setTeams(TeamModelFactory.getTeams());
        return matchModel;
    }
}
