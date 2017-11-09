package com.fastaoe.gankio.component.gank_all;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.AllContent;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public class GankAllPresenter extends BasePresenter<GankAllContract.View> implements GankAllContract.Presenter {

    @Override
    public void refreshContent(String category, String count, String page) {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        //noinspection unchecked
        DataModel.request(Token.ALL_CONTENT)
                .params(category, count, page)
                .execute()
                .subscribe(new Observer<AllContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllContent value) {
                        getView().showContent();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }
}
