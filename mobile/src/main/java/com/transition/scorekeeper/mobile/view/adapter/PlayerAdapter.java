package com.transition.scorekeeper.mobile.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.transition.scorekeeper.R;
import com.transition.scorekeeper.mobile.model.PlayerModel;
import com.transition.scorekeeper.mobile.view.adapter.holder.PlayerHolder;
import com.transition.scorekeeper.mobile.view.component.common.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public class PlayerAdapter extends BaseAdapter implements Filterable {
    private final LayoutInflater layoutInflater;
    protected List<PlayerModel> mDataSetWithoutFilter = new ArrayList<>();
    private List<Long> lastFilter;
    private OnPlayerClickListener onPlayerClickListener;

    @Inject
    public PlayerAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDataSet = new ArrayList<>();
    }

    @Override
    public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_player, parent, false);
        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((PlayerHolder) holder).setData((PlayerModel) mDataSet.get(position));
    }

    @Override
    public RecyclerView.OnItemTouchListener getListener() {
        return new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != RecyclerView.NO_POSITION) {
                    PlayerModel player = (PlayerModel) mDataSet.get(position);
                    if (onPlayerClickListener != null) {
                        onPlayerClickListener.onItemClick(view, player);
                    }
                }
            }
        });
    }

    public void initializeList(Collection<PlayerModel> playerModels) {
        validateCollection(playerModels);
        mDataSet.addAll(playerModels);
        mDataSetWithoutFilter.addAll(playerModels);
        notifyDataSetChanged();
    }

    private void validateCollection(Collection<PlayerModel> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public void filterBy(List<Long> playersIds) {
        lastFilter = playersIds;
        getFilter().filter("");
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Predicate<PlayerModel> predicate = new Predicate<PlayerModel>() {
                    public boolean apply(PlayerModel playerModel) {
                        return lastFilter != null && !lastFilter.contains(playerModel.getId());
                    }
                };
                Collection<PlayerModel> filter = Collections2.filter(mDataSetWithoutFilter, predicate);
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

    public void setOnPlayerClickListener(OnPlayerClickListener onPlayerClickListener) {
        this.onPlayerClickListener = onPlayerClickListener;
    }

    public interface OnPlayerClickListener {
        void onItemClick(View view, PlayerModel playerModel);
    }
}
