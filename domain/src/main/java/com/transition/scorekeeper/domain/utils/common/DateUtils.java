package com.transition.scorekeeper.domain.utils.common;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class DateUtils {
    public static long getMinutesOfDifference(Date startDate, Date endDate) {
        long duration = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    public static Date addMinutes(Date startDate, int minutes) {
        return org.apache.commons.lang3.time.DateUtils.addMinutes(startDate, minutes);
    }
}
