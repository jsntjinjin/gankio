package com.fastaoe.gankio.component.gank_user;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.database.GankItemProfile;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jinjin on 17/11/27.
 * description:
 */

public class GankUserPresenter extends BasePresenter<GankUserContract.View> implements GankUserContract.Presenter {

    public static final String QUERY_COLLECTION = "collection";
    public static final String QUERY_LATER_READER = "later_reader";
    public static final String QUERY_READED = "readed";

    private List<GankItemProfile> list;

    @Override
    public void showContent(String query) {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        //noinspection unchecked
        DataModel.request(Token.GANK_ITEM_PROFILE)
                .params(query)
                .execute()
                .subscribe(new Observer<List<GankItemProfile>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GankItemProfile> value) {
                        list.clear();
                        list.addAll(value);
                        getView().showContent();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });

    }

    @Override
    public List<GankItemProfile> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
