package com.transition.scorekeeper.mobile.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.internal.di.HasComponent;
import com.transition.scorekeeper.mobile.internal.di.components.DaggerMatchComponent;
import com.transition.scorekeeper.mobile.internal.di.components.MatchComponent;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.view.activity.interfaces.INeedUpdate;
import com.transition.scorekeeper.mobile.view.fragment.MatchFragment;
import com.transition.scorekeeper.mobile.view.fragment.dialog.NumberPickerDialog;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayerDialog;
import com.transition.scorekeeper.mobile.view.fragment.dialog.PlayersDialog;

/**
 * @author diego.rotondale
 * @since 15/05/16
 */
public class MatchActivity extends BaseActivity implements HasComponent<MatchComponent>,
        NumberPickerDialog.INumberPickerDialogListener, PlayersDialog.IPlayersDialogDialogListener,
        PlayerDialog.IPlayerDialogDialogListener {
    private MatchComponent matchComponent;

    public static Intent getCallingIntent(Context context, MatchModel match) {
        Intent intent = new Intent(context, MatchActivity.class);
        intent.putExtra(ViewConstants.ExtraKeys.MATCH, match);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.initializeInjector();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackStackState();
    }

    @Override
    protected void loadFragment() {
        loadFragment(new MatchFragment(), getFragmentResIDContainer(), ViewConstants.FragmentTag.MATCH, false);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_match;
    }

    @Override
    protected int getFragmentResIDContainer() {
        return R.id.match_container;
    }

    private void initializeInjector() {
        this.matchComponent = DaggerMatchComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public MatchComponent getComponent() {
        return matchComponent;
    }

    @Override
    public void finish() {
        Fragment fragment = getFragment();
        if (fragment instanceof INeedUpdate) {
            INeedUpdate needUpdate = (INeedUpdate) fragment;
            if (needUpdate.wasUpdated()) {
                Intent data = new Intent();
                data.putExtra(ViewConstants.ExtraKeys.MATCH, needUpdate.getUpdatedObject());
                setResult(RESULT_OK, data);
            }
        }
        super.finish();
    }

    @Override
    public void onDialogPositiveClick(String tag, int selectedValue) {
        Fragment fragment = getFragment();
        if (fragment instanceof NumberPickerDialog.INumberPickerDialogListener) {
            ((NumberPickerDialog.INumberPickerDialogListener) fragment).onDialogPositiveClick(tag, selectedValue);
        }
    }

    @Override
    public void onDialogNegativeClick(String tag, int selectedValue) {
        Fragment fragment = getFragment();
        if (fragment instanceof NumberPickerDialog.INumberPickerDialogListener) {
            ((NumberPickerDialog.INumberPickerDialogListener) fragment).onDialogNegativeClick(tag, selectedValue);
        }
    }

    @Override
    public void onDialogItemClick(PlayerModel playerModelSource, PlayerModel playerModel) {
        Fragment fragment = getFragment();
        if (fragment instanceof PlayersDialog.IPlayersDialogDialogListener) {
            ((PlayersDialog.IPlayersDialogDialogListener) fragment).onDialogItemClick(playerModelSource, playerModel);
        }
    }

    @Override
    public void onDialogPositiveClick(PlayerModel playerModelSource) {
        Fragment fragment = getFragment();
        if (fragment instanceof PlayersDialog.IPlayersDialogDialogListener) {
            ((PlayersDialog.IPlayersDialogDialogListener) fragment).onDialogPositiveClick(playerModelSource);
        }
    }
}
