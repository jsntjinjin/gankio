package com.fastaoe.gankio.component.gank_all;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.utils.LogUtil;
import com.fastaoe.gankio.widget.recycler.DefaultLoadCreator;
import com.fastaoe.gankio.widget.recycler.DefaultRefreshCreator;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.refresh.LoadRefreshRecyclerView;

import butterknife.BindView;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public class GankAllFragment extends BaseFragment implements GankAllContract.View {

    private static final String TAG = "GankAllFragment";
    @BindView(R.id.load_recycle)
    LoadRefreshRecyclerView loadRecycle;

    private GankAllPresenter gankAllPresenter;
    private RecyclerAdapter adapter;

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
        gankAllPresenter = new GankAllPresenter();
        gankAllPresenter.attachView(this);
        loadRecycle.setLayoutManager(new LinearLayoutManager(mContext));
        //loadRecycle.addItemDecoration(new LinearLayoutItemDecoration(this, R.drawable.item_dirver_01));
        loadRecycle.addRefreshViewCreator(new DefaultRefreshCreator());
        loadRecycle.addLoadViewCreator(new DefaultLoadCreator());
        loadRecycle.addEmptyView(new View(mContext));
        loadRecycle.setAdapter(initAdapter());
        loadRecycle.setOnRefreshListener(() -> {
            gankAllPresenter.refreshContent(getArguments().getString("flag"), false);
        });
        loadRecycle.setOnLoadMoreListener(() -> {
            gankAllPresenter.refreshContent(getArguments().getString("flag"), true);
        });
    }

    private RecyclerView.Adapter initAdapter() {
        adapter = new RecyclerAdapter<AllContent.ResultsBean>(mContext, gankAllPresenter.getList(), R.layout.item_gank_all) {
            @Override
            protected void convert(ViewHolder holder, AllContent.ResultsBean data, int position) {
                holder.setText(R.id.item_title, data.getDesc());
            }
        };

        adapter.setOnItemClickListener(position -> {

        });
        return adapter;
    }

    @Override
    protected void initData() {
        gankAllPresenter.refreshContent(getArguments().getString("flag"), false);
    }

    @Override
    protected void destroyData() {
        gankAllPresenter.detachView();
    }

    @Override
    public void refreshContent() {
        if (adapter != null) {
            LogUtil.d(TAG,adapter.getItemCount() + "");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void stopRefresh() {
        loadRecycle.stopRefresh();
    }

    @Override
    public void stopLoadMore() {
        loadRecycle.stopLoad();
    }
}
