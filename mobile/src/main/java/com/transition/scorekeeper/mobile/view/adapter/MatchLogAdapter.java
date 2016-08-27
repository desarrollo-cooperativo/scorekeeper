package com.transition.scorekeeper.mobile.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.LogModel;
import com.transition.scorekeeper.mobile.view.adapter.holder.MatchLogHolder;

import java.util.List;

import javax.inject.Inject;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class MatchLogAdapter extends BaseAdapter {
    private Long rightTeamId;

    @Inject
    public MatchLogAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Long teamId = ((LogModel) mDataSet.get(position)).getTeamId();
        if (teamId == null) {
            return Position.BOTH;
        }
        return teamId.equals(rightTeamId) ? Position.RIGHT : Position.LEFT;
    }

    @Override
    public MatchLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resource = R.layout.item_match_log;
        switch (viewType) {
            case Position.LEFT:
                resource = R.layout.item_match_log_left;
                break;
            case Position.RIGHT:
                resource = R.layout.item_match_log_right;
                break;
        }
        View view = LayoutInflater.from(context).inflate(resource, parent, false);
        return new MatchLogHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MatchLogHolder) holder).setData((LogModel) mDataSet.get(position));
    }

    public void initializeList(List<LogModel> objects, Long rightTeamId) {
        this.rightTeamId = rightTeamId;
        mDataSet.addAll(objects);
        notifyDataSetChanged();
    }

    public class Position {
        public static final int BOTH = 0;
        public static final int LEFT = 1;
        public static final int RIGHT = 2;
    }
}
