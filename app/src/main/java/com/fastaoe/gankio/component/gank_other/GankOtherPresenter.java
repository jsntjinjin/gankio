package com.fastaoe.gankio.component.gank_other;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.AllContent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class GankOtherPresenter extends BasePresenter<GankOtherContract.View> implements GankOtherContract.Presenter {

    private int page = 1;
    private List<AllContent.ResultsBean> gankOhterList;

    @Override
    public void refreshContent(boolean isLoadMore, String item) {
        if (!isViewAttached()) {
            return;
        }

        if (!isLoadMore) {
            page = 1;
        } else {
            page++;
        }

        //noinspection unchecked
        DataModel.request(Token.ALL_CONTENT)
                .params(item, "10", String.valueOf(page))
                .execute()
                .subscribe(new Observer<AllContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllContent value) {
                        if (!isLoadMore) {
                            gankOhterList.clear();
                        }
                        gankOhterList.addAll(value.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (!isLoadMore) {
                            getView().stopRefresh();
                        } else {
                            getView().stopLoadMore();
                        }
                    }
                });
    }

    @Override
    public List<AllContent.ResultsBean> getList() {
        if (gankOhterList == null) {
            gankOhterList = new ArrayList<>();
        }
        return gankOhterList;
    }
}
