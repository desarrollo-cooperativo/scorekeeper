package com.transition.scorekeeper.mobile.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.view.component.MatchStatusView;
import com.transition.scorekeeper.mobile.view.component.MatchTeamPlayersView;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchHolder extends RecyclerView.ViewHolder {

    private final MatchStatusView result;
    private final MatchTeamPlayersView leftTeam;
    private final MatchTeamPlayersView rightTeam;

    public MatchHolder(View itemView) {
        super(itemView);
        leftTeam = (MatchTeamPlayersView) itemView.findViewById(R.id.team_left);
        rightTeam = (MatchTeamPlayersView) itemView.findViewById(R.id.team_right);
        result = (MatchStatusView) itemView.findViewById(R.id.match_status);
    }

    public void setData(MatchModel matchModel) {
        leftTeam.setData(matchModel.getLeftTeam(), matchModel.needTheMachStart());
        rightTeam.setData(matchModel.getRightTeam(), matchModel.needTheMachStart());
        result.setData(matchModel);
    }

    public void setOnMatchFinishListener(MatchStatusView.OnMatchFinishListener onMatchFinishListener) {
        if (result != null) {
            result.setOnMatchFinishListener(onMatchFinishListener);
        }
    }
}
