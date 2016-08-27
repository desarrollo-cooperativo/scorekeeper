package com.transition.scorekeeper.data.cache.database.provider;

import android.content.UriMatcher;
import android.net.Uri;

/**
 * @author diego.rotondale
 * @since 30/07/16
 */
public class BaseProvider extends DbProvider {
    @Override
    protected String getTableName() {
        return "";
    }

    @Override
    protected UriMatcher getUriMatcher() {
        return null;
    }

    @Override
    protected Uri getUri(long id) {
        return null;
    }
}
