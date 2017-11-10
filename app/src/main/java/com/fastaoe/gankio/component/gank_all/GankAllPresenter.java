package com.fastaoe.gankio.component.gank_all;

import android.text.TextUtils;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.AllContent;

import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public class GankAllPresenter extends BasePresenter<GankAllContract.View> implements GankAllContract.Presenter {

    private List<AllContent.ResultsBean> results = new ArrayList<>();

    private int page = 1;

    @Override
    public List<AllContent.ResultsBean> getList() {
        return results;
    }

    @Override
    public void refreshContent(String category, boolean isLoadMore) {
        if (!isViewAttached()) {
            return;
        }

        if (!isLoadMore) {
            page = 1;
        } else {
            page++;
        }

        //noinspection unchecked
        Observable.zip(
                DataModel.request(Token.ALL_CONTENT)
                        .params(category, "10", String.valueOf(page))
                        .execute(),
                DataModel.request(Token.ALL_CONTENT)
                        .params("福利", "10", String.valueOf(page))
                        .execute(),
                this::mergeRest2MeiziDesc)
                .map(data -> ((AllContent) data).getResults())
                .toSortedList((meizi, meizi2) -> ((AllContent.ResultsBean) meizi).getPublishedAt()
                        .compareTo(((AllContent.ResultsBean) meizi2).getPublishedAt()))
                .subscribe(new Observer<List<AllContent.ResultsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<AllContent.ResultsBean> value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                .subscribe(new Observer<AllContent.ResultsBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(AllContent.ResultsBean value) {
//                        if (!isLoadMore) {
//                            results.clear();
//                        }
//                        results.addAll(value);
//                        getView().refreshContent();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        if (!isLoadMore) {
//                            getView().stopRefresh();
//                        } else {
//                            getView().stopLoadMore();
//                        }
//                    }
//                });
    }

    private AllContent mergeRest2MeiziDesc(AllContent meizi, AllContent video) {
        for (AllContent.ResultsBean meiziBean : meizi.getResults()) {
            meiziBean.setDesc(meiziBean.getDesc() + " "
                    + getFirstVideoDesc(meiziBean.getPublishedAt(), video.getResults()));
        }
        return meizi;
    }

    private int lastPosition = 0;

    private String getFirstVideoDesc(Date publishedAt, List<AllContent.ResultsBean> results) {
        String videoDesc = "";
        for (int i = lastPosition; i < results.size(); i++) {
            AllContent.ResultsBean video = results.get(i);
            if (video.getPublishedAt() == null) {
                video.setPublishedAt(video.getCreatedAt());
            }
            if (isTheSameDay(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc();
                lastPosition = i;
                break;
            }
        }
        return videoDesc;
    }

    public static boolean isTheSameDay(Date one, Date another) {
        Calendar _one = Calendar.getInstance();
        _one.setTime(one);
        Calendar _another = Calendar.getInstance();
        _another.setTime(another);
        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
        int anotherDay = _another.get(Calendar.DAY_OF_YEAR);

        return oneDay == anotherDay;
    }
}
