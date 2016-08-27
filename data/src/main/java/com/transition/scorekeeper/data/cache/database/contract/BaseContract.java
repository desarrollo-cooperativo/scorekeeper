package com.transition.scorekeeper.data.cache.database.contract;

import android.net.Uri;

import com.transition.scorekeeper.data.BuildConfig;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public abstract class BaseContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
}
