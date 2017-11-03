package com.fastaoe.baselibrary.basemvp;

/**
 * Created by jinjin on 17/10/31.
 * description: presenter层基类，定义通用方法
 */

public class BasePresenter<V extends IBaseView> {

    private V mvpView;

    /**
     * 绑定view
     * @param mvpView
     */
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 解除view
     */
    public void detachView() {
        this.mvpView = null;
    }

    /**
     * 判断view是否存在
     * @return
     */
    public boolean isViewAttached() {
        return this.mvpView != null;
    }

    /**
     * 获取view
     * @return
     */
    public V getView() {
        return mvpView;
    }

}
