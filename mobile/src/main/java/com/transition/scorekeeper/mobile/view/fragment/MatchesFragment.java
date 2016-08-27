package com.transition.scorekeeper.mobile.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.ViewConstants;
import com.transition.scorekeeper.mobile.internal.di.components.MatchComponent;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.navigation.Navigator;
import com.transition.scorekeeper.mobile.presenter.MatchesPresenter;
import com.transition.scorekeeper.mobile.view.activity.interfaces.IFilterMatches;
import com.transition.scorekeeper.mobile.view.adapter.MatchAdapter;
import com.transition.scorekeeper.mobile.view.component.common.EmptyView;
import com.transition.scorekeeper.mobile.view.component.common.RecyclerViewWithEmptyView;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.MatchesView;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchesFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MatchAdapter.OnMatchClickListener, MatchesView, IFilterMatches {
    @Bind(R.id.matches_list)
    protected RecyclerViewWithEmptyView rv;
    @Bind(R.id.matches_empty)
    protected EmptyView empty;
    @Bind(R.id.matches_swipe_refresh_layout)
    protected SwipeRefreshLayout swipe;

    @Inject
    protected MatchAdapter adapter;
    @Inject
    protected MatchesPresenter matchesPresenter;
    private MatchesFragmentListener matchesListener;

    public MatchesFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(MatchComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initializeList();
        initializeSwipeView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.matchesPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadMatches();
        }
    }

    private void loadMatches() {
        this.matchesPresenter.initialize();
    }

    @Override
    public boolean canDoBack() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_matches;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(View view, MatchModel matchModel) {
        if (MatchesFragment.this.matchesPresenter != null && matchModel != null) {
            MatchesFragment.this.matchesPresenter.onMatchClicked(matchModel);
        }
    }

    @Override
    public void showLoading() {
        setSwipeRefreshing(true);
        empty.setMessage(getString(R.string.empty_loading_matches));
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
        empty.setMessage(getString(R.string.empty_matches));
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MatchesFragmentListener) {
            this.matchesListener = (MatchesFragmentListener) context;
        }
    }

    @Override
    public void viewMatch(MatchModel matchModel) {
        if (matchesListener != null) {
            matchesListener.onMatchClicked(matchModel);
            Navigator.navigateToMatch(this, matchModel);
        }
    }

    @Override
    public void renderMatches(Collection<MatchModel> matchModelCollection) {
        int selectTab = 1;
        if (matchesListener != null) {
            selectTab = matchesListener.getSelectTab();
        }
        this.adapter.initializeList(matchModelCollection, selectTab);
    }

    @Override
    public void onFabClick() {
        matchesPresenter.onMatchClicked(null);
    }

    @Override
    public void createMatch(MatchModel matchModel) {
        if (adapter != null) {
            adapter.createMatch(matchModel);
        }
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
        adapter.setOnMatchClickListener(this);
        rv.addOnItemTouchListener(adapter.getListener());
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.matchesPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.matchesPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.matchesPresenter.destroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ViewConstants.Result.MATCH: {
                    adapter.updateItem(data.getSerializableExtra(ViewConstants.ExtraKeys.MATCH));
                    break;
                }
            }
        }
    }

    @Override
    public void filterBy(int position) {
        if (adapter != null) {
            adapter.filterBy(position);
        }
    }

    public interface MatchesFragmentListener {
        void onMatchClicked(final MatchModel matchModel);

        int getSelectTab();
    }
}
