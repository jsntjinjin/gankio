package com.fastaoe.gankio.widget.recycler.refresh;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jinjin on 17/6/12.
 * description:
 */

public abstract class RefreshViewCreator {
    /**
     * 获取下拉刷新的View
     */
    public abstract View getRefreshView(Context context, ViewGroup parent);

    /**
     * 正在下拉
     * @param currentDragHeight   当前拖动的高度
     * @param refreshViewHeight  总的刷新高度
     * @param currentRefreshStatus 当前状态
     */
    public abstract void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus);

    /**
     * 正在刷新中
     */
    public abstract void onRefreshing();

    /**
     * 停止刷新
     */
    public abstract void onStopRefresh();
}
