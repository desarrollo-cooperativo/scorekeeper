package com.transition.scorekeeper.mobile.view.component;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.view.component.common.ChronometerElapsed;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchStatusView extends RelativeLayout {

    MatchCountDownTimer counter;
    private TextView goals;
    private TextView message;
    private OnMatchFinishListener onMatchFinishListener;
    private ChronometerElapsed chronometerElapsed;

    public MatchStatusView(Context context) {
        super(context);
        initialize();
    }

    public MatchStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MatchStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public MatchStatusView initialize() {
        inflate(getContext(), R.layout.view_status, this);
        goals = (TextView) findViewById(R.id.status_goals);
        message = (TextView) findViewById(R.id.status_message);
        chronometerElapsed = (ChronometerElapsed) findViewById(R.id.status_chronometer);
        return this;
    }

    public void setData(MatchModel matchModel) {
        goals.setText(getGoalsValue(matchModel));
        cancelActiveCounter();
        message.setTextColor(getResources().getColor(android.R.color.black));
        if (matchModel.isTheMatchInProgress()) {
            goals.setVisibility(VISIBLE);
            if (matchModel.getMaxMinutes() != null) {
                message.setVisibility(VISIBLE);
                chronometerElapsed.setVisibility(GONE);
                counter = new MatchCountDownTimer(matchModel.getTimeToEndGame(), TimeUnit.SECONDS.toMillis(1));
                counter.start();
                message.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                chronometerElapsed.setVisibility(VISIBLE);
                message.setVisibility(GONE);
                chronometerElapsed.setMsElapsed(matchModel.getMatchElapsedTime());
                chronometerElapsed.start();
            }
        } else {
            goals.setVisibility(matchModel.needTheMachStart() ? GONE : VISIBLE);
            message.setVisibility(VISIBLE);
            chronometerElapsed.setVisibility(GONE);
            message.setText(matchModel.getStatusMessage(getContext()));
        }
    }

    private String getGoalsValue(MatchModel matchModel) {
        return matchModel.getGoals(getContext());
    }

    private void cancelActiveCounter() {
        if (counter != null) {
            counter.cancel();
        }
        chronometerElapsed.stop();
    }

    private void setFinishStatus() {
        message.setTextColor(getResources().getColor(android.R.color.black));
        message.setText(getContext().getString(R.string.match_status_end));
        if (onMatchFinishListener != null) {
            onMatchFinishListener.matchEndByTime();
        }
    }

    public void setOnMatchFinishListener(OnMatchFinishListener onMatchFinishListener) {
        this.onMatchFinishListener = onMatchFinishListener;
    }

    public interface OnMatchFinishListener {
        void matchEndByTime();
    }

    public class MatchCountDownTimer extends CountDownTimer {

        public MatchCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            setFinishStatus();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes);
            message.setText(getContext().getString(R.string.match_status_in_progress,
                    String.format(Locale.ENGLISH, "%02d", minutes), String.format(Locale.ENGLISH, "%02d", seconds)));
        }
    }
}
