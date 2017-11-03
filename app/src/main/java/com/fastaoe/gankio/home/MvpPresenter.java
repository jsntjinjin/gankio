package com.fastaoe.gankio.home;

import com.fastaoe.baselibrary.basemvp.BasePresenter;

/**
 * Created by jinjin on 17/11/2.
 * description:
 */

public class MvpPresenter extends BasePresenter<MvpView> {

    public void getData() {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        MvpModel.getNetData(new MvpCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().showData(data);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {
                getView().showError();
            }

            @Override
            public void onComplete() {
                getView().hideLoading();
            }
        });
    }

}
