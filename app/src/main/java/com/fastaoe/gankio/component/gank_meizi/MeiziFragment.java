package com.fastaoe.gankio.component.gank_meizi;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.model.beans.RandomData;
import com.fastaoe.gankio.utils.DensityUtil;
import com.fastaoe.gankio.widget.recycler.GridLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;

import butterknife.BindView;

/**
 * Created by jinjin on 2017/11/11.
 * description:
 */

public class MeiziFragment extends BaseFragment implements MeiziContract.View {

    @BindView(R.id.load_recycle)
    RecyclerView loadRecycle;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshLayout;

    private MeiziPresenter meiziPresenter;
    private RecyclerView.Adapter adapter;

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
        //        ViewGroup.LayoutParams layoutParams = loadRecycle.getLayoutParams();
        //        layoutParams.width = DensityUtil.getScreenWidth(mContext);
        //        layoutParams.height = DensityUtil.getScreenWidth(mContext);
        //        loadRecycle.setLayoutParams(layoutParams);
        initRefreshView();
        initRecycleView();
    }

    private void initRefreshView() {
        refreshLayout.setOnRefreshListener(() -> meiziPresenter.refreshContent());
    }

    private void initRecycleView() {
        loadRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        loadRecycle.addItemDecoration(new GridLayoutItemDecoration(mContext, R.drawable.shape_item_dirver_02));
        adapter = initAdapter();
        loadRecycle.setAdapter(adapter);
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter<RandomData.ResultsBean>(mContext, meiziPresenter.getList(), R.layout.item_gank_meizi) {

            @Override
            protected void convert(ViewHolder holder, RandomData.ResultsBean data, int position) {
                ImageView view = holder.getView(R.id.image_view);
                Glide.with(mContext).load(data.getUrl()).apply(new RequestOptions().fitCenter()).into(view);
            }
        };

        adapter.setOnItemClickListener(position -> {

        });
        return adapter;
    }


    @Override
    protected void initData() {
        meiziPresenter.refreshContent();
    }

    @Override
    protected void destroyData() {
        meiziPresenter.detachView();
    }

    @Override
    public void refreshContent() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void stopLoadMore() {

    }
}
