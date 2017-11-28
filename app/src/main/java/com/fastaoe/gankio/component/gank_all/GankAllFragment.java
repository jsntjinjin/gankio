package com.fastaoe.gankio.component.gank_all;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.component.gank_detail.GankDetailActivity;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.utils.DateUtil;
import com.fastaoe.gankio.widget.recycler.DefaultLoadCreator;
import com.fastaoe.gankio.widget.recycler.DefaultRefreshCreator;
import com.fastaoe.gankio.widget.recycler.GridLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.LinearLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.refresh.LoadRefreshRecyclerView;

import java.util.Date;

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

    public static GankAllFragment newInstance() {
        GankAllFragment fragment = new GankAllFragment();
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
        initRecycleView();
    }

    private void initRecycleView() {
        loadRecycle.setLayoutManager(new GridLayoutManager(mContext, 2));
        //        loadRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        loadRecycle.addItemDecoration(new LinearLayoutItemDecoration(mContext, R.drawable.shape_item_dirver_01));
        loadRecycle.addRefreshViewCreator(new DefaultRefreshCreator());
        loadRecycle.addLoadViewCreator(new DefaultLoadCreator());
        //        loadRecycle.addEmptyView(new View(mContext));
        loadRecycle.setAdapter(initAdapter());
        loadRecycle.setOnRefreshListener(() -> {
            gankAllPresenter.refreshContent(false);
        });
        loadRecycle.setOnLoadMoreListener(() -> {
            gankAllPresenter.refreshContent(true);
        });
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter<AllContent.ResultsBean> adapter = new RecyclerAdapter<AllContent.ResultsBean>(mContext, gankAllPresenter.getList(), R.layout.item_gank_all) {
            @Override
            protected void convert(ViewHolder holder, AllContent.ResultsBean data, int position) {
                holder.setText(R.id.tv_title, data.getDesc());
                ImageView view = holder.getView(R.id.iv_meizi);
                Glide.with(mContext).load(data.getUrl()).apply(new RequestOptions().centerCrop()).into(view);
            }
        };

        adapter.setOnItemClickListener(position -> {
            // TODO: 17/11/14 添加跳转gankDetailActivity
            Date publishedAt = gankAllPresenter.getList().get(position).getPublishedAt();
            GankDetailActivity.startGankDetailActivity(mContext,
                    DateUtil.getDateYear(publishedAt) + "",
                    DateUtil.getDateMonth(publishedAt) + "",
                    DateUtil.getDateDay(publishedAt) + "");
        });
        return adapter;
    }

    @Override
    protected void initData() {
        gankAllPresenter.refreshContent(false);
    }

    @Override
    protected void destroyData() {
        gankAllPresenter.detachView();
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
