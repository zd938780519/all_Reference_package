package com.mchsdk.paysdk.mylibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ListFragment extends BaseFragment{
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    protected boolean isPrepared = false; //标志位，标志已经初始化完成
    protected boolean mHasLoadedOnce = false; //是否已被加载过一次，第二次就不再去请求数据了
    public View mFragmentView;
    public Unbinder unbinder;

    protected abstract void lazyLoadData();

    protected abstract void initView();

    protected abstract int setLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == mFragmentView) {
            mFragmentView = inflater.inflate(setLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            initView();
        }
        return mFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }


    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        lazyLoadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
}
