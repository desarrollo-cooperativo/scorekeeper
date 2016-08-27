package com.transition.scorekeeper.mobile.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.transition.scorekeeper.mobile.view.component.common.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;

    protected List<Object> mDataSet = new ArrayList<>();

    public RecyclerView.OnItemTouchListener getListener() {
        return new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void add(Object object) {
        mDataSet.add(0, object);
        notifyItemInserted(mDataSet.indexOf(object));
    }

    public void addAll(List<? extends Object> objects) {
        mDataSet.addAll(0, objects);
        notifyItemRangeInserted(0, objects.size());
    }

    public void updateItem(Object object) {
        if (mDataSet.contains(object)) {
            int indexOf = mDataSet.indexOf(object);
            mDataSet.remove(indexOf);
            mDataSet.add(indexOf, object);
            notifyItemChanged(indexOf);
        }
    }
}
