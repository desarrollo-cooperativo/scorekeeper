package com.transition.scorekeeper.mobile.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class ViewUtils {
    public static void hideKeyboard(IBinder token, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(token, 0);
    }
}