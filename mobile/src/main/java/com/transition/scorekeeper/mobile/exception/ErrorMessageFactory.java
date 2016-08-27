package com.transition.scorekeeper.mobile.exception;

import android.content.Context;

import com.transition.scorekeeper.R;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
    }

    public static String create(Context context, Exception exception) {
        return context.getString(R.string.exception_message_generic, exception.getCause());
    }
}
