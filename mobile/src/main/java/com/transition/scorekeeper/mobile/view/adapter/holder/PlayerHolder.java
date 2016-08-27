package com.transition.scorekeeper.mobile.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.transition.scorekeeper.mobile.model.PlayerModel;

/**
 * @author diego.rotondale
 * @since 29/05/16
 */
public class PlayerHolder extends RecyclerView.ViewHolder {

    private final TextView player;

    public PlayerHolder(View itemView) {
        super(itemView);
        player = (TextView) itemView;
    }

    public void setData(PlayerModel playerModel) {
        player.setText(playerModel.getName());
    }
}
