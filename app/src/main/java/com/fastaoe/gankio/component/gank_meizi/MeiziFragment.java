package com.fastaoe.gankio.component.gank_meizi;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.baselibrary.dialog.AlertDialog;
import com.fastaoe.baselibrary.permission.PermissionFailure;
import com.fastaoe.baselibrary.permission.PermissionHelper;
import com.fastaoe.baselibrary.permission.PermissionSuccess;
import com.fastaoe.baselibrary.permission.PermissionUtils;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.Constants;
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
    private TextView refreshAll;

    private int savePosition = 0;
    private AlertDialog meiziDialog;

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
        refreshAll = footer.findViewById(R.id.tv_refresh);
        saveAll.setOnClickListener(view -> {
            // 全部保存
            meiziPermission(Constants.PERMISSION_READ_WRITE_STORAGE_MEIZI_LIST);
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

        adapter.setOnItemClickListener(position -> meiziPresenter.showMeiziDialog(position));
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
    public void refreshContent(int savedSize) {
        adapter.notifyDataSetChanged();
        saveAll.setClickable(true);
        if (savedSize > 0) {
            savePosition = savedSize;
            saveAll.setText("继续保存（" + savedSize + "/" + meiziPresenter.getList().size() + "）");
        } else {
            savePosition = 0;
            saveAll.setText("存储到本地");
        }
    }

    @Override
    public void saveTextChanged(String msg) {
        saveAll.setText(msg);
    }

    @Override
    public void saveMeiziStart() {
        refreshAll.setClickable(false);
        saveAll.setClickable(false);
    }

    @Override
    public void saveMeiziEnd() {
        if (savePosition != 9) {
            saveAll.setClickable(true);
        }
        refreshAll.setClickable(true);
    }

    @Override
    public int setSavePosition() {
        return ++savePosition;
    }

    @Override
    public void showMeizi(RandomData.ResultsBean resultsBean) {
        if (meiziDialog != null) {
            meiziDialog.setImage(R.id.iv_meizi, resultsBean.getUrl());
            meiziDialog.setTextColor(R.id.itv_save, resultsBean.isSaved() ? R.color.text_orange : R.color.text_caption);
            meiziDialog.show();
            return;
        }

        meiziDialog = new AlertDialog.Builder(mContext)
                .setContentView(R.layout.dialog_meizi)
                .setImage(R.id.iv_meizi, resultsBean.getUrl())
                .setTextColor(R.id.itv_save, resultsBean.isSaved() ? R.color.text_orange : R.color.text_caption)
                .fullWidth()
                .show();

        meiziDialog.setOnClickListener(R.id.iv_meizi, view -> meiziDialog.dismiss());
        meiziDialog.setOnClickListener(R.id.itv_save, view -> meiziPermission(Constants.PERMISSION_READ_WRITE_STORAGE_MEIZI));
    }

    @Override
    public void dialogRefresh(boolean saved) {
        if (meiziDialog != null) {
            meiziDialog.setTextColor(R.id.itv_save, saved ? R.color.text_orange : R.color.text_caption);
        }
    }

    private void meiziPermission(int requestCode) {
        PermissionHelper.with(this)
                .requestCode(requestCode)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions);
    }

    @PermissionSuccess(requestCode = Constants.PERMISSION_READ_WRITE_STORAGE_MEIZI_LIST)
    private void callMeiziListSuccess() {
        meiziPresenter.saveMeizi(meiziPresenter.getList());
    }

    @PermissionFailure(requestCode = Constants.PERMISSION_READ_WRITE_STORAGE_MEIZI_LIST)
    private void callMeiziListFailure() {
        showToast("请在系统中允许读写SD卡权限");
    }

    @PermissionSuccess(requestCode = Constants.PERMISSION_READ_WRITE_STORAGE_MEIZI)
    private void callMeiziSuccess() {
        meiziPresenter.saveMeizi(meiziPresenter.getShowMeizi());
    }

    @PermissionFailure(requestCode = Constants.PERMISSION_READ_WRITE_STORAGE_MEIZI)
    private void callMeiziFailure() {
        showToast("请在系统中允许读写SD卡权限");
    }

}
