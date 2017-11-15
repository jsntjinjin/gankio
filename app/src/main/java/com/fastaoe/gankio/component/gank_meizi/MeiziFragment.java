package com.fastaoe.gankio.component.gank_meizi;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.widget.recycler.DefaultLoadCreator;
import com.fastaoe.gankio.widget.recycler.DefaultRefreshCreator;
import com.fastaoe.gankio.widget.recycler.LinearLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.refresh.LoadRefreshRecyclerView;

import butterknife.BindView;

/**
 * Created by jinjin on 2017/11/11.
 * description:
 */

public class MeiziFragment extends BaseFragment implements MeiziContract.View {

    @BindView(R.id.load_recycle)
    RecyclerView loadRecycle;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

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
        initRecycleView();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            meiziPresenter.refreshContent(false);
        });

    }

    @Override
    protected void initData() {
        meiziPresenter.refreshContent(false);
    }

    //用来标记是否正在向最后一个滑动，既是否向下滑动
    boolean isSlidingToLast = false;

    private void initRecycleView() {
        //        loadRecycle.setLayoutManager(new GridLayoutManager(mContext, 2));
        loadRecycle.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                loadRecycle.addItemDecoration(new LinearLayoutItemDecoration(mContext, R.drawable.shape_item_dirver_01));
//                loadRecycle.addRefreshViewCreator(new DefaultRefreshCreator());
//                loadRecycle.addLoadViewCreator(new DefaultLoadCreator());
//                loadRecycle.addEmptyView(new View(mContext));
        loadRecycle.setAdapter(initAdapter());
        loadRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int[] lastVisiblePositions = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
                    int lastVisiblePos = getMaxElem(lastVisiblePositions);
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部
                    if (lastVisiblePos == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        meiziPresenter.refreshContent(true);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }
        });
    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter<AllContent.ResultsBean> adapter = new RecyclerAdapter<AllContent.ResultsBean>(mContext, meiziPresenter.getList(), R.layout.item_gank_meizi) {
            @Override
            protected void convert(ViewHolder holder, AllContent.ResultsBean data, int position) {
                ImageView view = holder.getView(R.id.image_view);
                Glide.with(mContext).load(data.getUrl()).apply(new RequestOptions().centerCrop()).into(view);
            }
        };

        adapter.setOnItemClickListener(position -> {

        });
        return adapter;
    }

    @Override
    protected void destroyData() {
        meiziPresenter.detachView();
    }

    @Override
    public void refreshContent() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void stopLoadMore() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
