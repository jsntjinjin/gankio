package com.fastaoe.gankio.component.gank_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;
import com.fastaoe.gankio.model.beans.GankContent;
import com.fastaoe.gankio.widget.navigationbar.DefaultNavigationBar;
import com.fastaoe.gankio.widget.recycler.base.ItemClickListener;
import com.fastaoe.gankio.widget.recycler.base.MuliteTypeSupport;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.wrap.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class GankDetailActivity extends MyBaseActivity implements GankDetailContract.View {

    @BindView(R.id.load_recycle)
    WrapRecyclerView loadRecycle;

    private ArrayList<String> date;
    private GankDetailPresenter gankDetailPresenter;
    private ImageView ivMeizi;

    public static void startGankDetailActivity(Context context, String year, String month, String day) {
        Intent intent = new Intent(context, GankDetailActivity.class);
        ArrayList<String> strings = new ArrayList<>();
        strings.add(year);
        strings.add(month);
        strings.add(day);
        intent.putStringArrayListExtra("date", strings);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initTitle() {
        gankDetailPresenter = new GankDetailPresenter();
        gankDetailPresenter.attachView(this);
        date = getIntent().getStringArrayListExtra("date");
        String title = date.get(0) + "/" + date.get(1) + "/" + date.get(2);
        new DefaultNavigationBar.Builder(this)
                .setTitle(title).builder();
    }

    @Override
    protected void initView() {
        initRecycle();
    }

    private void initRecycle() {
        loadRecycle.setLayoutManager(new LinearLayoutManager(this));
        loadRecycle.setAdapter(initAdapter());

        View header = LayoutInflater.from(this).inflate(R.layout.item_gank_detail_header, loadRecycle, false);
        ivMeizi = header.findViewById(R.id.iv_meizi);
        loadRecycle.addHeaderView(header);
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter<GankContent.ResultsBean.ContentBean>(this,
                gankDetailPresenter.getList(),
                data -> {
                    if (((GankContent.ResultsBean.ContentBean) data).isFirstItem()) {
                        return R.layout.item_gank_detail_first;
                    }
                    return R.layout.item_gank_detail;
                }) {
            @Override
            protected void convert(ViewHolder holder, GankContent.ResultsBean.ContentBean data, int position) {
                if (data.isFirstItem()) {
                    holder.setText(R.id.tv_type, data.getType())
                            .setText(R.id.tv_title, data.getDesc());
                } else {
                    holder.setText(R.id.tv_title, data.getDesc());
                }
            }
        };
        adapter.setOnItemClickListener(position -> {
            // TODO: 17/11/28 跳转gank详情
            gankDetailPresenter.getList().get(position);
        });
        return adapter;
    }

    @Override
    protected void initData() {
        gankDetailPresenter.getContent(date);
    }

    @Override
    protected void destroyData() {
        gankDetailPresenter.detachView();
    }

    @Override
    public void refreshContent() {
        if (gankDetailPresenter.getImage() != null) {
            Glide.with(this).load(gankDetailPresenter.getImage()).into(ivMeizi);
        }
        loadRecycle.getAdapter().notifyDataSetChanged();
    }
}
