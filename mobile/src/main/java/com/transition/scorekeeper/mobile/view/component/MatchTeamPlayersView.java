package com.transition.scorekeeper.mobile.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.model.TeamModel;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchTeamPlayersView extends LinearLayout implements View.OnClickListener {
    private TextView top;
    private TextView bottom;
    private View separator;
    private IOnPlayerClick onPlayerClickListener;

    public MatchTeamPlayersView(Context context) {
        super(context);
        initialize();
    }

    public MatchTeamPlayersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MatchTeamPlayersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public MatchTeamPlayersView initialize() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_match_team_players, this);
        top = (TextView) findViewById(R.id.player_top);
        top.setOnClickListener(this);
        bottom = (TextView) findViewById(R.id.player_bottom);
        bottom.setOnClickListener(this);
        separator = findViewById(R.id.players_separators);
        return this;
    }

    public void setData(TeamModel teamModel, boolean showEmpty) {
        setName(top, teamModel, showEmpty);
        setName(bottom, teamModel, showEmpty);
    }

    private void setName(TextView textView, TeamModel teamModel, boolean showEmpty) {
        String name = null;
        PlayerModel playerModel = null;
        if (teamModel != null) {
            playerModel = getPlayerModel(textView, teamModel);
            name = playerModel.getName();
            textView.setText(name);
        } else {
            playerModel = new PlayerModel();
            playerModel.setTeamInfo(null, getPosition(textView));
            textView.setText("");
        }
        textView.setTag(playerModel);
        boolean showAllPlayers = showEmpty || name != null;
        textView.setVisibility(showAllPlayers ? View.VISIBLE : View.GONE);
        separator.setVisibility(showAllPlayers ? View.VISIBLE : View.GONE);
    }

    public int getPosition(TextView textView) {
        int position;
        switch (textView.getId()) {
            case R.id.player_top:
                position = 0;
                break;
            default:
                position = 1;
                break;
        }
        return position;
    }

    private PlayerModel getPlayerModel(TextView textView, TeamModel teamModel) {
        PlayerModel playerModel;
        switch (textView.getId()) {
            case R.id.player_top:
                playerModel = teamModel.getPlayerTop();
                break;
            default:
                playerModel = teamModel.getPlayerBottom();
                break;
        }
        return playerModel;
    }

    public void setOnPlayerClickListener(IOnPlayerClick onPlayerClickListener) {
        this.onPlayerClickListener = onPlayerClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player_top:
            case R.id.player_bottom:
                onPlayerClick((PlayerModel) v.getTag());
                break;
        }
    }

    private void onPlayerClick(PlayerModel playerModel) {
        if (onPlayerClickListener != null) {
            onPlayerClickListener.onPlayerClick(playerModel);
        }
    }

    public void updateData(TeamModel team, boolean showEmpty) {
        setData(team, showEmpty);
    }

    public interface IOnPlayerClick {
        void onPlayerClick(PlayerModel playerModel);
    }
}
