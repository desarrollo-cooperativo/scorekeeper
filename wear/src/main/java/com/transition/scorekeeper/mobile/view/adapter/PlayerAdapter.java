package com.transition.scorekeeper.mobile.view.adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.Player;
import com.transition.scorekeeper.mobile.view.adapter.holder.PlayerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 16/08/16
 */
public class PlayerAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private List<Player> mDataSet = new ArrayList<>();

    public PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlayerHolder(mInflater.inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        PlayerHolder playerHolder = (PlayerHolder) holder;
        Player player = mDataSet.get(position);
        playerHolder.setData(player);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addAll(ArrayList<Player> players) {
        this.mDataSet.addAll(players);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mDataSet.clear();
    }
}
