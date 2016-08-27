package com.transition.scorekeeper.mobile.view.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.MatchModel;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class MatchHeaderView extends RelativeLayout implements View.OnClickListener {

    private MatchStatusView result;
    private TextView goal;
    private TextView time;
    private OnGoalInfoListener onGoalInfoListener;
    private OnTimeInfoListener onTimeInfoListener;

    public MatchHeaderView(Context context) {
        super(context);
        initialize();
    }

    public MatchHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MatchHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public MatchHeaderView initialize() {
        inflate(getContext(), R.layout.view_match_header, this);
        result = (MatchStatusView) findViewById(R.id.match_header_result);
        goal = (TextView) findViewById(R.id.match_header_info_goal);
        goal.setOnClickListener(this);
        time = (TextView) findViewById(R.id.match_header_info_time);
        time.setOnClickListener(this);
        return this;
    }

    public void setData(MatchModel matchModel) {
        result.setData(matchModel);
        setInfo(matchModel);
    }

    private void setInfo(MatchModel matchModel) {
        goal.setText(getValue(matchModel.getMaxGoals(), R.string.match_info_goal));
        time.setText(getValue(matchModel.getMaxMinutes(), R.string.match_info_time));
    }

    @NonNull
    private String getValue(Integer max, int resource) {
        if (max == null || max == 0) {
            return getContext().getString(R.string.match_info_not_limit);
        } else {
            return getContext().getString(resource, String.valueOf(max));
        }
    }

    public void updateResult(MatchModel matchModel) {
        result.setData(matchModel);
    }

    public void setOnMatchFinishListener(MatchStatusView.OnMatchFinishListener onMatchFinishListener) {
        if (result != null) {
            result.setOnMatchFinishListener(onMatchFinishListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.match_header_info_goal:
                if (onGoalInfoListener != null) {
                    onGoalInfoListener.onGoalInfoClick();
                }
                break;
            case R.id.match_header_info_time:
                if (onTimeInfoListener != null) {
                    onTimeInfoListener.onTimeInfoClick();
                }
                break;
        }
    }

    public void setOnGoalInfoListener(OnGoalInfoListener onGoalInfoListener) {
        this.onGoalInfoListener = onGoalInfoListener;
    }

    public void setOnTimeInfoListener(OnTimeInfoListener onTimeInfoListener) {
        this.onTimeInfoListener = onTimeInfoListener;
    }

    public void updateInfo(MatchModel matchModel) {
        setInfo(matchModel);
    }

    public interface OnGoalInfoListener {
        void onGoalInfoClick();
    }

    public interface OnTimeInfoListener {
        void onTimeInfoClick();
    }
}
