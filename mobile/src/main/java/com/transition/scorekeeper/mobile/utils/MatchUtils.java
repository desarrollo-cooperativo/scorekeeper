package com.transition.scorekeeper.mobile.utils;

import android.content.Context;

import com.transition.scorekeeper.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author diego.rotondale
 * @since 27/05/16
 */
public class MatchUtils {
    public static String getDuration(Context context, long duration) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
        return context.getString(R.string.duration,
                String.format(Locale.ENGLISH, "%02d", minutes), String.format(Locale.ENGLISH, "%02d", seconds));
    }

    public static String getDate(Date date) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        return df.format(date);
    }
}
