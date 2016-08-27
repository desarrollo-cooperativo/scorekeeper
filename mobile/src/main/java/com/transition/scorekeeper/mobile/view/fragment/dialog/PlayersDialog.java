package com.transition.scorekeeper.mobile.view.fragment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.internal.di.HasComponent;
import com.transition.scorekeeper.mobile.internal.di.components.MatchComponent;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.presenter.PlayersPresenter;
import com.transition.scorekeeper.mobile.view.adapter.PlayerAdapter;
import com.transition.scorekeeper.mobile.view.component.common.EmptyView;
import com.transition.scorekeeper.mobile.view.component.common.RecyclerViewWithEmptyView;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.PlayersView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author diego.rotondale
 * @since 29/05/16
 */
public class PlayersDialog extends BaseDialogFragment implements PlayersView, SwipeRefreshLayout.OnRefreshListener, PlayerAdapter.OnPlayerClickListener {
    @Bind(R.id.players_list)
    protected RecyclerViewWithEmptyView rv;
    @Bind(R.id.players_empty)
    protected EmptyView empty;
    @Bind(R.id.players_swipe_refresh_layout)
    protected SwipeRefreshLayout swipe;
    @Inject
    protected PlayerAdapter adapter;
    @Inject
    protected PlayersPresenter playersPresenter;
    private IPlayersDialogDialogListener callback;

    public PlayersDialog() {
        setRetainInstance(true);
    }

    private void loadPlayers() {
        this.playersPresenter.initialize();
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
        builder.setTitle(R.string.players_dialog_title);
        builder.setPositiveButton(R.string.players_dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callback.onDialogPositiveClick(getSourcePlayer());
            }
        });
        this.playersPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadPlayers();
        }
        return builder;
    }

    @Override
    protected int getDialogResID() {
        return R.layout.dialog_players;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeSwipeView();
        initializeList();
    }

    private void initializeSwipeView() {
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeResources(R.color.primary_dark, R.color.primary,
                R.color.primary_light, R.color.primary_dark);
        swipe.setEnabled(false);
    }

    private void initializeList() {
        rv.setHasFixedSize(true);
        rv.setEmptyView(empty);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnPlayerClickListener(this);
        rv.addOnItemTouchListener(adapter.getListener());
        rv.setAdapter(adapter);
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onResume() {
        super.onResume();
        playersPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playersPresenter.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        playersPresenter.pause();
    }

    @Override
    public void renderPlayers(Collection<PlayerModel> playerModelCollection) {
        adapter.initializeList(playerModelCollection);
        adapter.filterBy((List<Long>) getArguments().getSerializable(ViewConstants.ExtraKeys.PLAYERS_ON_MATCH));
    }

    @Override
    public void showLoading() {
        setSwipeRefreshing(true);
        empty.setMessage(getString(R.string.empty_loading_players));
    }

    private void setSwipeRefreshing(final boolean value) {
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(value);
            }
        });
    }

    @Override
    public void hideLoading() {
        setSwipeRefreshing(false);
        empty.setMessage(getString(R.string.empty_players));
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
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onItemClick(View view, PlayerModel playerModel) {
        callback.onDialogItemClick(getSourcePlayer(), playerModel);
        dismiss();
    }

    private PlayerModel getSourcePlayer() {
        return (PlayerModel) getArguments().getSerializable(ViewConstants.ExtraKeys.PLAYER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (IPlayersDialogDialogListener) context;
    }

    public interface IPlayersDialogDialogListener {
        void onDialogItemClick(PlayerModel playerModelSource, PlayerModel playerModel);

        void onDialogPositiveClick(PlayerModel playerModelSource);
    }
}
