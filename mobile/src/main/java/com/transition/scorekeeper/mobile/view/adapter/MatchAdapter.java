package com.transition.scorekeeper.mobile.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.MatchModel;
import com.transition.scorekeeper.mobile.view.adapter.holder.MatchHolder;
import com.transition.scorekeeper.mobile.view.component.MatchStatusView;
import com.transition.scorekeeper.mobile.view.component.common.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class MatchAdapter extends BaseAdapter implements Filterable, MatchStatusView.OnMatchFinishListener {
    private final LayoutInflater layoutInflater;
    public Predicate<MatchModel> playedPredicate = new Predicate<MatchModel>() {
        public boolean apply(MatchModel matchModel) {
            return matchModel.isTheMatchEnd();
        }
    };
    public Predicate<MatchModel> toPlayPredicate = new Predicate<MatchModel>() {
        public boolean apply(MatchModel matchModel) {
            return matchModel.needTheMachStart();
        }
    };
    public Predicate<MatchModel> playingPredicate = new Predicate<MatchModel>() {
        public boolean apply(MatchModel matchModel) {
            return matchModel.isTheMatchInProgress();
        }
    };
    private OnMatchClickListener onMatchClickListener;
    private List<MatchModel> mDataSetWithoutFilter = new ArrayList<>();
    private int lastFilter = 0;

    @Inject
    public MatchAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDataSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_match, parent, false);
        return new MatchHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MatchModel matchModel = (MatchModel) mDataSet.get(position);
        Predicate<MatchModel> predicate = getMatchModelPredicate(lastFilter);
        boolean apply = predicate.apply(matchModel);
        if (apply) {
            MatchHolder matchHolder = (MatchHolder) holder;
            matchHolder.setData(matchModel);
            matchHolder.setOnMatchFinishListener(this);
        } else {
            applyLastFilter();
        }
    }

    @Override
    public RecyclerView.OnItemTouchListener getListener() {
        return new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != RecyclerView.NO_POSITION) {
                    MatchModel match = (MatchModel) mDataSet.get(position);
                    if (onMatchClickListener != null) {
                        onMatchClickListener.onItemClick(view, match);
                    }
                }
            }
        });
    }

    public void initializeList(Collection<MatchModel> matchModels, int selectedTabPosition) {
        validateCollection(matchModels);
        mDataSet.addAll(matchModels);
        mDataSetWithoutFilter.addAll(matchModels);
        lastFilter = selectedTabPosition;
        filterBy(selectedTabPosition);
    }

    private void validateCollection(Collection<MatchModel> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void setOnMatchClickListener(OnMatchClickListener onMatchClickListener) {
        this.onMatchClickListener = onMatchClickListener;
    }

    public void filterBy(int position) {
        lastFilter = position;
        getFilter().filter(String.valueOf(position));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Integer filterType = Integer.valueOf(String.valueOf(constraint));
                Predicate<MatchModel> predicate = getMatchModelPredicate(filterType);
                Collection<MatchModel> filter = Collections2.filter(mDataSetWithoutFilter, predicate);
                FilterResults results = new FilterResults();
                results.count = filter.size();
                results.values = new ArrayList<>(filter);
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Object values = results.values;
                if (values != null) {
                    mDataSet = (List<Object>) values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    @Nullable
    private Predicate<MatchModel> getMatchModelPredicate(Integer filterType) {
        Predicate<MatchModel> predicate = null;
        switch (filterType) {
            case FilterType.PLAYING:
                predicate = playingPredicate;
                break;
            case FilterType.TO_PLAY:
                predicate = toPlayPredicate;
                break;
            case FilterType.PLAYED:
                predicate = playedPredicate;
                break;
        }
        return predicate;
    }

    @Override
    public void updateItem(Object object) {
        boolean needFilter = false;
        if (mDataSet.contains(object)) {
            int indexOf = mDataSet.indexOf(object);
            MatchModel matchModel = (MatchModel) mDataSet.get(indexOf);
            if (matchModel.matchStatusChange((MatchModel) object)) {
                needFilter = true;
            }
            mDataSet.remove(matchModel);
            mDataSet.add(indexOf, object);
            notifyItemChanged(indexOf);
        }
        int indexOnAll = mDataSetWithoutFilter.indexOf(object);
        mDataSetWithoutFilter.remove(indexOnAll);
        mDataSetWithoutFilter.add(indexOnAll, (MatchModel) object);
        if (needFilter) {
            applyLastFilter();
        }
    }

    @Override
    public void matchEndByTime() {
        applyLastFilter();
    }

    private void applyLastFilter() {
        filterBy(lastFilter);
    }

    public void createMatch(MatchModel matchModel) {
        mDataSetWithoutFilter.add(matchModel);
        applyLastFilter();
    }

    public interface OnMatchClickListener {
        void onItemClick(View view, MatchModel match);
    }

    public static class FilterType {
        public static final int PLAYING = 0;
        public static final int TO_PLAY = 1;
        public static final int PLAYED = 2;
    }
}
