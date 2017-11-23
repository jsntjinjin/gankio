package com.fastaoe.gankio.component.gank_other;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.widget.recycler.DefaultLoadCreator;
import com.fastaoe.gankio.widget.recycler.DefaultRefreshCreator;
import com.fastaoe.gankio.widget.recycler.LinearLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.refresh.LoadRefreshRecyclerView;

import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class GankOtherFragment extends BaseFragment implements GankOtherContract.View {

    private static final String TAG = "GankOtherFragment";
    @BindView(R.id.load_recycle)
    LoadRefreshRecyclerView loadRecycle;

    private GankOtherPresenter gankOtherPresenter;
    private String item;

    public static GankOtherFragment newInstance(String item) {
        GankOtherFragment fragment = new GankOtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_gank_other;
    }

    @Override
    protected void initView() {
        gankOtherPresenter = new GankOtherPresenter();
        gankOtherPresenter.attachView(this);
        initRecycleView();
    }

    private void initRecycleView() {
        loadRecycle.setLayoutManager(new LinearLayoutManager(mContext));
        loadRecycle.addItemDecoration(new LinearLayoutItemDecoration(mContext, R.drawable.shape_item_dirver_01));
        loadRecycle.addRefreshViewCreator(new DefaultRefreshCreator());
        loadRecycle.addLoadViewCreator(new DefaultLoadCreator());
        //        loadRecycle.addEmptyView(new View(mContext));
        loadRecycle.setAdapter(initAdapter());
        loadRecycle.setOnRefreshListener(() -> {
            gankOtherPresenter.refreshContent(false, item);
        });
        loadRecycle.setOnLoadMoreListener(() -> {
            gankOtherPresenter.refreshContent(true, item);
        });
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter<AllContent.ResultsBean> adapter
                = new RecyclerAdapter<AllContent.ResultsBean>(mContext, gankOtherPresenter.getList(), R.layout.item_gank_other) {
            @Override
            protected void convert(ViewHolder holder, AllContent.ResultsBean data, int position) {
                holder.setText(R.id.tv_title, data.getDesc())
                        .setText(R.id.tv_create_time, new SimpleDateFormat("yyyy-MM-dd").format(data.getPublishedAt()))
                        .setText(R.id.tv_author, data.getWho());
                holder.getView(R.id.iv_collection).setOnClickListener(view -> {
                    // TODO: 17/11/14 收藏功能
                    if (gankOtherPresenter.setCollectionOrNot(position)) {
                        // 收藏成功
                        ((TextView) holder.getView(R.id.tv_collection)).setText("已收藏");
                    } else {
                        // 收藏失败
                        ((TextView) holder.getView(R.id.tv_collection)).setText("收藏");
                    }
                });
                holder.getView(R.id.iv_later_read).setOnClickListener(view -> {
                    // TODO: 17/11/14 稍后阅读功能
                    if (gankOtherPresenter.setLaterReaderOrNot(position)) {
                        // 设置稍后阅读
                        ((TextView) holder.getView(R.id.tv_later_read)).setText("已稍后阅读");
                    } else {
                        // 取消稍后阅读
                        ((TextView) holder.getView(R.id.tv_later_read)).setText("稍后阅读");
                    }
                });
            }
        };

        adapter.setOnItemClickListener(position -> {
            // TODO: 17/11/14 跳转单个文章
            gankOtherPresenter.setReaded(position);
        });
        return adapter;
    }

    @Override
    protected void initData() {
        item = getArguments().getString("flag");
        gankOtherPresenter.refreshContent(false, item);
    }

    @Override
    protected void destroyData() {
        gankOtherPresenter.detachView();
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
