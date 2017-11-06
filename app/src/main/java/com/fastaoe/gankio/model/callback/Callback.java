package com.fastaoe.gankio.model.callback;

/**
 * Created by jinjin on 17/11/1.
 * description:
 */

public interface Callback<T> {

    /**
     * 数据请求成功
     * @param data
     */
    void onSuccess(T data);

    /**
     * 请求成功，但是返回的数据由于{@code msg}的原因不能返回
     * @param msg
     */
    void onFailure(String msg);

    /**
     * 请求失败，404或者500
     */
    void onError();

    /**
     * 请求结束，不管是onSuccess、onFailure、onError都会回调这个，可以用来处理隐藏加载等操作
     */
    void onComplete();

}
