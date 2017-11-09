package com.fastaoe.gankio.component.gank_all;

import android.os.Bundle;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.component.MainActivity;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public class GankAllFragment extends BaseFragment implements GankAllContract.View {

    private GankAllPresenter gankAllPresenter;

    public static GankAllFragment newInstance(String item) {
        GankAllFragment fragment = new GankAllFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_gank_all;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        gankAllPresenter = new GankAllPresenter();
        gankAllPresenter.attachView(this);
        gankAllPresenter.refreshContent(getArguments().getString("flag"), "10", "1");
    }

    @Override
    protected void destroyData() {
        gankAllPresenter.detachView();
    }

    @Override
    public void showContent() {

    }
}
