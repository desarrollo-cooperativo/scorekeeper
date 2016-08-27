package com.transition.scorekeeper.mobile.view.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author diego.rotondale
 * @since 04/06/16
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(getDialogResID(), null);
        ButterKnife.bind(this, view);
        return getBuilder(savedInstanceState, view).create();
    }

    protected abstract AlertDialog.Builder getBuilder(Bundle savedInstanceState, View view);

    protected abstract int getDialogResID();
}
