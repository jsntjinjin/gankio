package com.fastaoe.gankio.home;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.beans.Search;
import com.fastaoe.gankio.model.callback.Callback;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.HistoryForOneDay;

import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


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
        DataModel.request(Token.SEARCH)
                .params("android", "10", "1")
                .execute()
                .subscribe(new Observer<Search>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Search value) {
                        getView().showData(value.getResults().get(new Random().nextInt(10)).getDesc());
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showToast("onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
