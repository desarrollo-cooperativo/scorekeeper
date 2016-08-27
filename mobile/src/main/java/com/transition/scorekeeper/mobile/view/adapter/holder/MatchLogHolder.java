package com.transition.scorekeeper.mobile.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.LogModel;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class MatchLogHolder extends RecyclerView.ViewHolder {

    private TextView matchLog;

    public MatchLogHolder(View itemView) {
        super(itemView);
        this.matchLog = (TextView) itemView.findViewById(R.id.match_log);
    }

    public void setData(LogModel logModel) {
        matchLog.setText(logModel.getLog());
    }
}
