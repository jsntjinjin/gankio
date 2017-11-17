package com.fastaoe.gankio.component.gank_meizi;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.model.beans.RandomData;
import com.fastaoe.gankio.widget.recycler.GridLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.wrap.WrapRecyclerView;

import butterknife.BindView;

/**
 * Created by jinjin on 2017/11/11.
 * description:
 */

public class MeiziFragment extends BaseFragment implements MeiziContract.View {

    @BindView(R.id.load_recycle)
    WrapRecyclerView loadRecycle;

    private MeiziPresenter meiziPresenter;
    private RecyclerView.Adapter adapter;
    private TextView saveAll;

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
        initFooterView();
    }

    private void initFooterView() {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.item_gank_meizi_footer, loadRecycle, false);

        footer.findViewById(R.id.tv_refresh).setOnClickListener(view -> meiziPresenter.refreshContent());
        saveAll = footer.findViewById(R.id.tv_save_all);
        saveAll.setOnClickListener(view -> {
            // 全部保存
            meiziPresenter.saveMeizi(meiziPresenter.getList());
        });

        loadRecycle.addFooterView(footer);
    }

    private void initRecycleView() {
        loadRecycle.setLayoutManager(new GridLayoutManager(mContext, 3));
        loadRecycle.addItemDecoration(new GridLayoutItemDecoration(mContext, R.drawable.shape_item_dirver_02));
        adapter = initAdapter();
        loadRecycle.setAdapter(adapter);
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter<RandomData.ResultsBean>(mContext, meiziPresenter.getList(), R.layout.item_gank_meizi) {

            @Override
            protected void convert(ViewHolder holder, RandomData.ResultsBean data, int position) {
                ImageView view = holder.getView(R.id.image_view);
                Glide.with(mContext).load(data.getUrl()).apply(new RequestOptions().centerCrop()).into(view);
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
    public void saveTextChanged(String msg) {
        saveAll.setText(msg);
    }
}
