package com.fastaoe.gankio.component.gank_meizi;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;

/**
 * Created by jinjin on 2017/11/11.
 * description:
 */

public class MeiziFragment extends BaseFragment implements MeiziContract.View {

    private MeiziPresenter meiziPresenter;

    public static MeiziFragment newInstance() {
        MeiziFragment fragment = new MeiziFragment();
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_gank_meizi;
    }

    @Override
    protected void initView() {
        meiziPresenter = new MeiziPresenter();
        meiziPresenter.attachView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void destroyData() {
        meiziPresenter.detachView();
    }

    @Override
    public void refreshContent() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void stopLoadMore() {

    }
}
