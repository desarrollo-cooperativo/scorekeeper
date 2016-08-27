package com.transition.scorekeeper.mobile.view.adapter.holder;

import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.Player;

/**
 * @author diego.rotondale
 * @since 16/08/16
 */
public class PlayerHolder extends WearableListView.ViewHolder {
    private TextView textView;
    private View view;

    public PlayerHolder(View view) {
        super(view);
        this.view = view;
        textView = (TextView) itemView.findViewById(R.id.name);
    }

    public void setData(Player player) {
        textView.setText(player.getName());
        view.setTag(player.getId());
    }
}
