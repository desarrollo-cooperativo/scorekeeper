package com.transition.scorekeeper.mobile.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transition.scorekeeper.mobile.internal.di.HasComponent;
import com.transition.scorekeeper.mobile.view.activity.interfaces.IBaseActivity;
import com.transition.scorekeeper.mobile.view.fragment.interfaces.IOnBackPressed;

import butterknife.ButterKnife;

/**
 * @author diego.rotondale
 * @since 14/05/16
 */
public abstract class BaseFragment extends Fragment implements IOnBackPressed {
    protected IBaseActivity callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResID(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayoutResID();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.callback = (IBaseActivity) context;
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
