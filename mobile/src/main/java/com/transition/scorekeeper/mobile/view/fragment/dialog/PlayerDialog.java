package com.transition.scorekeeper.mobile.view.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.internal.di.HasComponent;
import com.transition.scorekeeper.mobile.internal.di.components.MatchComponent;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.presenter.PlayerPresenter;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.PlayerView;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author diego.rotondale
 * @since 12/06/16
 */
public class PlayerDialog extends BaseDialogFragment implements PlayerView {
    @Bind(R.id.player_name)
    protected EditText name;

    @Inject
    protected PlayerPresenter playerPresenter;
    private IPlayerDialogDialogListener callback;

    public PlayerDialog() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MatchComponent.class).inject(this);
    }

    @Override
    protected AlertDialog.Builder getBuilder(Bundle savedInstanceState, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        this.playerPresenter.setView(this);
        builder.setTitle(R.string.player_dialog_title);
        builder.setPositiveButton(R.string.player_dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder;
    }


    @Override
    protected int getDialogResID() {
        return R.layout.dialog_player;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context context() {
        return getDialog().getContext();
    }

    @Override
    public void onPlayerCreated(PlayerModel playerModel) {
        callback.onDialogItemClick(getSourcePlayer(), playerModel);
        getDialog().dismiss();
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onResume() {
        super.onResume();
        playerPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerPresenter.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        playerPresenter.pause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (IPlayerDialogDialogListener) context;
    }

    private PlayerModel getSourcePlayer() {
        return (PlayerModel) getArguments().getSerializable(ViewConstants.ExtraKeys.PLAYER);
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            final Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerPresenter.createPlayer(name.getText().toString());
                    positiveButton.setOnClickListener(null);
                    positiveButton.setEnabled(false);
                }
            });
        }
    }

    public interface IPlayerDialogDialogListener {
        void onDialogItemClick(PlayerModel playerModelSource, PlayerModel playerModel);
    }
}
