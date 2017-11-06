package com.fastaoe.gankio.home;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.callback.Callback;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.Content;

/**
 * Created by jinjin on 17/11/2.
 * description:
 */

public class ContentPresenter extends BasePresenter<ContentContract.View> implements ContentContract.Presenter {
    @Override
    public void getData() {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        //noinspection unchecked
        DataModel.request(Token.BLOG)
                .execute(new Callback<Content>() {
                    @Override
                    public void onSuccess(Content data) {
                        getView().showData(data.results.get(0)._id);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
