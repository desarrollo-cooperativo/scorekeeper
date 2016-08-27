package com.transition.scorekeeper.mobile.view.component.common;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;

/**
 * @author diego.rotondale
 * @since 28/05/16
 */
public class ChronometerElapsed extends android.widget.Chronometer {

    public long msElapsed;
    public boolean isRunning = false;

    public ChronometerElapsed(Context context) {
        super(context);
    }

    public ChronometerElapsed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChronometerElapsed(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMsElapsed(long ms) {
        setBase(getBase() - ms);
        msElapsed = ms;
    }

    @Override
    public void start() {
        super.start();
        setBase(SystemClock.elapsedRealtime() - msElapsed);
        isRunning = true;
    }

    @Override
    public void stop() {
        super.stop();
        if (isRunning) {
            msElapsed = (int) (SystemClock.elapsedRealtime() - this.getBase());
        }
        isRunning = false;
    }
}