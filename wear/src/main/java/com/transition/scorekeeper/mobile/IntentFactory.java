package com.transition.scorekeeper.mobile;

import android.content.Context;
import android.content.Intent;
import android.support.wearable.activity.ConfirmationActivity;

/**
 * @author diego.rotondale
 * @since 22/08/16
 */
public class IntentFactory {
    public static Intent getConfirmation(Context context, String message) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
        if (message != null) {
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, message);
        }
        return intent;
    }
}
