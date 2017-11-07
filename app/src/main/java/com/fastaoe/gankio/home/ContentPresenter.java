package com.fastaoe.gankio.home;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.callback.Callback;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.HistoryForOneDay;

import rx.Observer;

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
                .execute()
                .subscribe(new Observer<HistoryForOneDay>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HistoryForOneDay o) {
                        getView().showData(o.results.get(0)._id);
                    }
                });
    }

}
