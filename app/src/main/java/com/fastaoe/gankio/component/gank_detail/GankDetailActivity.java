package com.fastaoe.gankio.component.gank_detail;

import android.content.Context;
import android.content.Intent;

import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class GankDetailActivity extends MyBaseActivity {

    public static void newInstance(Context context) {
        context.startActivity(new Intent(context, GankDetailActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_gank_detail;
    }

    @Override
    protected void initTitle() {

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
}
