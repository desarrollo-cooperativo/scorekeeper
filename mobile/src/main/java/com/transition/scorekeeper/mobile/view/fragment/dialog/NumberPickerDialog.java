package com.transition.scorekeeper.mobile.view.fragment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.model.dto.InfoModel;

/**
 * @author diego.rotondale
 * @since 04/06/16
 */
public class NumberPickerDialog extends BaseDialogFragment {
    private NumberPicker np;
    private INumberPickerDialogListener callback;

    @Override
    protected AlertDialog.Builder getBuilder(Bundle savedInstanceState, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        InfoModel infoModel = (InfoModel) getArguments().getSerializable(ViewConstants.ExtraKeys.INFO);
        setData(view, infoModel);
        if (infoModel != null) {
            builder.setTitle(infoModel.getTitle());
            builder.setMessage(infoModel.getMessage());
        }
        builder.setPositiveButton(R.string.number_picker_dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callback.onDialogPositiveClick(NumberPickerDialog.this.getTag(), np.getValue());
            }
        });
        builder.setNegativeButton(R.string.number_picker_dialog_negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callback.onDialogNegativeClick(NumberPickerDialog.this.getTag(), np.getValue());
            }
        });
        return builder;
    }

    @Override
    protected int getDialogResID() {
        return R.layout.dialog_number_picker;
    }

    public void setData(View view, InfoModel infoModel) {
        np = (NumberPicker) view.findViewById(R.id.number_picker);
        np.setMaxValue(infoModel.getMaxValue());
        np.setMinValue(infoModel.getMinValue());
        np.setValue(infoModel.getActualValue());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (INumberPickerDialogListener) context;
    }

    public interface INumberPickerDialogListener {
        void onDialogPositiveClick(String tag, int selectedValue);

        void onDialogNegativeClick(String tag, int selectedValue);
    }

}
