package com.fastaoe.gankio.component.other;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.component.gank_user.GankCollectionActivity;
import com.fastaoe.gankio.component.gank_user.GankHistoryActivity;
import com.fastaoe.gankio.component.gank_user.GankLaterReaderActivity;
import com.fastaoe.gankio.widget.CommonTextItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by jinjin on 17/11/27.
 * description:
 */

public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void destroyData() {

    }
    @BindView(R.id.cti_gank_collection)
    CommonTextItem ctiGankCollection;
    @BindView(R.id.cti_gank_later_reader)
    CommonTextItem ctiGankLaterReader;
    @BindView(R.id.cti_gank_readed)
    CommonTextItem ctiGankReaded;

    @OnClick(R.id.cti_gank_collection)
    void onGankCollection() {
        GankCollectionActivity.startGankCollectionActivity(mContext);
    }

    @OnClick(R.id.cti_gank_later_reader)
    void onGankLaterReader() {
        GankLaterReaderActivity.startGankLaterReaderActivity(mContext);
    }

    @OnClick(R.id.cti_gank_readed)
    void onGankReader() {
        GankHistoryActivity.startGankHistoryActivity(mContext);
    }
}
