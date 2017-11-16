package com.fastaoe.gankio.component.gank_meizi;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.RandomData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter {

    private List<RandomData.ResultsBean> meiziResults;

    @Override
    public void refreshContent() {
        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        //noinspection unchecked
        DataModel.request(Token.RANDOM_MEIZI)
                .execute()
                .subscribe(new Observer<RandomData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RandomData value) {
                        meiziResults.clear();
                        meiziResults.addAll(value.getResults());
                        getView().refreshContent();
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

    @Override
    public List<RandomData.ResultsBean> getList() {
        if (meiziResults == null) {
            meiziResults = new ArrayList<>();
        }
        return meiziResults;
    }
}
