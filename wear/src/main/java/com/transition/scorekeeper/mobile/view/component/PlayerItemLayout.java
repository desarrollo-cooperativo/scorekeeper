package com.transition.scorekeeper.mobile.view.component;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transition.scorekeeper.R;

/**
 * @author diego.rotondale
 * @since 16/08/16
 */
public class PlayerItemLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {
    private final float mFadedTextAlpha;
    private TextView mName;

    public PlayerItemLayout(Context context) {
        this(context, null);
    }

    public PlayerItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerItemLayout(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);

        mFadedTextAlpha = getResources()
                .getInteger(R.integer.action_text_faded_alpha) / 100f;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mName = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setAlpha(1f);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setAlpha(mFadedTextAlpha);
    }
}
