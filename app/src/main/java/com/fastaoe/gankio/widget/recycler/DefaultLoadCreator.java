package com.fastaoe.gankio.widget.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.fastaoe.gankio.R;
import com.fastaoe.gankio.widget.recycler.refresh.LoadViewCreator;


/**
 * Created by jinjin on 2017/6/12.
 */

public class DefaultLoadCreator extends LoadViewCreator {

    public static final String LOAD_RESULT_TEXT_LOAD_MORE = "加载更多~~~";
    public static final String LOAD_RESULT_TEXT_TO_BOTTOM = "到达底部~~~";
    public static final String LOAD_RESULT_TEXT_LOADING = "正在加载更多~~~";
    public static final String LOAD_RESULT_TEXT_LOAD_ERROR = "加载错误~~~";

    // 加载数据的ImageView
    private TextView mRefreshIv;

    @Override
    public View getLoadView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_load_view, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.refresh_tv);
        return refreshView;
    }

    @Override
    public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
        mRefreshIv.setText(LOAD_RESULT_TEXT_LOADING);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onStopLoad(String text) {
        // 停止加载的时候清除动画
        mRefreshIv.setText(text);
    }
}
