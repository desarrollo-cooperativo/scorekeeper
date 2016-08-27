package com.transition.scorekeeper.mobile.view.fragment.interfaces;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.transition.scorekeeper.mobile.model.LogModel;
import com.transition.scorekeeper.mobile.model.MatchModel;

import java.util.List;

/**
 * @author diego.rotondale
 * @since 19/05/16
 */
public interface MatchView {

    void showSnackBar(int length, String message, View.OnClickListener onUndoListener);

    void toast(int length, String message);

    void addLog(LogModel matchLog);

    void addLogs(List<LogModel> matchLogs);

    void updateResult(MatchModel matchModel);

    Context getContext();

    void updatePlayers(MatchModel matchModel);

    void onMatchEndSuccessfully();

    void onMatchStartSuccessfully();

    FragmentManager getFragmentManager();

    void updateInfo(MatchModel matchModel);

    void showError(String errorMessage);

    void hideViewLoading();

    void showLoading();

    void updateMatchOnView();

    void showWearableMenu();
}
