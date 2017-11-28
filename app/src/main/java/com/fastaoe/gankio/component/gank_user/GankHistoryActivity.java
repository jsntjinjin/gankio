package com.fastaoe.gankio.component.gank_user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;
import com.fastaoe.gankio.model.database.GankItemProfile;
import com.fastaoe.gankio.utils.DateUtil;
import com.fastaoe.gankio.widget.navigationbar.DefaultNavigationBar;
import com.fastaoe.gankio.widget.recycler.LinearLayoutItemDecoration;
import com.fastaoe.gankio.widget.recycler.base.MuliteTypeSupport;
import com.fastaoe.gankio.widget.recycler.base.RecyclerAdapter;
import com.fastaoe.gankio.widget.recycler.base.ViewHolder;
import com.fastaoe.gankio.widget.recycler.wrap.WrapRecyclerView;

import butterknife.BindView;

/**
 * Created by jinjin on 17/11/27.
 * description:
 */

public class GankHistoryActivity extends MyBaseActivity implements GankUserContract.View {

    @BindView(R.id.load_recycle)
    WrapRecyclerView loadRecycle;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private GankUserPresenter gankUserPresenter;
    private RecyclerView.Adapter adapter;

    public static void startGankHistoryActivity(Context context) {
        Intent intent = new Intent(context, GankHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_gank_user_done;
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("我的收藏")
                .builder();
    }

    @Override
    protected void initView() {
        gankUserPresenter = new GankUserPresenter();
        gankUserPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        gankUserPresenter.showContent(GankUserPresenter.QUERY_READED);
        initRecycle();
    }

    private void initRecycle() {
        loadRecycle.setLayoutManager(new LinearLayoutManager(this));
        loadRecycle.addItemDecoration(new LinearLayoutItemDecoration(this, R.drawable.shape_item_dirver_01));
        loadRecycle.addEmptyView(tvEmpty);
        adapter = initAdapter();
        loadRecycle.setAdapter(adapter);
    }

    private RecyclerView.Adapter initAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter<GankItemProfile>(this, gankUserPresenter.getList(), new MuliteTypeSupport<GankItemProfile>() {
            @Override
            public int getLayoutId(GankItemProfile item) {
                if (!TextUtils.isEmpty(item.getImages())) {
                    return R.layout.item_gank_user_image;
                }
                return R.layout.item_gank_user_no_image;
            }
        }) {

            @Override
            protected void convert(ViewHolder holder, GankItemProfile data, int position) {
                if (!TextUtils.isEmpty(data.getImages())) {
                    // has images
                    holder.setText(R.id.tv_saved_time, DateUtil.DateToStr(data.getCollectionedAt()))
                            .setText(R.id.tv_type, data.getType())
                            .setText(R.id.tv_title, data.getDesc() + "(" + data.getWho() + ")")
                            .setImagePath(R.id.iv_images, new ViewHolder.HolderImageLoader(data.getImages()) {
                                @Override
                                public void loadImage(ImageView imageView, String path) {
                                    String imageUrl = path.split("\\|")[0];
                                    Glide.with(GankHistoryActivity.this).load(imageUrl).into(imageView);
                                }
                            });
                } else {
                    // no images
                    holder.setText(R.id.tv_saved_time, DateUtil.DateToStr(data.getCollectionedAt()))
                            .setText(R.id.tv_type, data.getType())
                            .setText(R.id.tv_title, data.getDesc() + "(" + data.getWho() + ")");
                }
            }
        };
        return adapter;
    }

    @Override
    protected void destroyData() {
        gankUserPresenter.detachView();
    }

    @Override
    public void showContent() {
        adapter.notifyDataSetChanged();
    }
}
