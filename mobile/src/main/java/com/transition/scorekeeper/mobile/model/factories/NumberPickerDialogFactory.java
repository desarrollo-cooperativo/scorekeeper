package com.transition.scorekeeper.mobile.model.factories;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.model.dto.InfoModel;
import com.transition.scorekeeper.mobile.view.fragment.dialog.NumberPickerDialog;

import java.io.Serializable;

/**
 * @author diego.rotondale
 * @since 04/06/16
 */
public class NumberPickerDialogFactory {
    public static DialogFragment getGoalDialog(Integer actualGoals) {
        return getNumberPickerDialog(getGoalInfo(actualGoals));
    }

    public static DialogFragment getTimeDialog(Integer actualTime) {
        return getNumberPickerDialog(getTimeInfo(actualTime));
    }

    @NonNull
    private static NumberPickerDialog getNumberPickerDialog(Serializable infoModel) {
        NumberPickerDialog dialog = new NumberPickerDialog();
        Bundle args = new Bundle();
        args.putSerializable(ViewConstants.ExtraKeys.INFO, infoModel);
        dialog.setArguments(args);
        return dialog;
    }

    public static Serializable getGoalInfo(Integer actualGoals) {
        if (actualGoals == null) {
            actualGoals = 0;
        }
        return new InfoModel.InfoModelBuilder()
                .setTitle(R.string.title_goal_info_dialog)
                .setMessage(R.string.message_goal_info_dialog)
                .setActualValue(actualGoals)
                .setMinValue(0)
                .setMaxValue(20)
                .build();
    }

    public static Serializable getTimeInfo(Integer actualTime) {
        if (actualTime == null) {
            actualTime = 0;
        }
        return new InfoModel.InfoModelBuilder()
                .setTitle(R.string.title_time_info_dialog)
                .setMessage(R.string.message_time_info_dialog)
                .setActualValue(actualTime)
                .setMinValue(0)
                .setMaxValue(60)
                .build();
    }
}
