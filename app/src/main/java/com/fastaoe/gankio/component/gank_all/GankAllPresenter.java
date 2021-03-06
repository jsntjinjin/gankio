package com.fastaoe.gankio.component.gank_all;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.utils.DateUtil;
import com.fastaoe.gankio.widget.recycler.DefaultLoadCreator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    public void refreshContent(boolean isLoadMore) {
        if (!isViewAttached()) {
            return;
        }

        if (!isLoadMore) {
            page = 1;
        } else {
            page++;
        }

        lastPosition = 0;

        //noinspection unchecked
        Observable.zip(
                DataModel.request(Token.ALL_CONTENT)
                        .params("福利", "10", String.valueOf(page))
                        .execute(),
                DataModel.request(Token.ALL_CONTENT)
                        .params("休息视频", "10", String.valueOf(page))
                        .execute(),
                this::mergeRest2MeiziDesc)
                .subscribeOn(Schedulers.io())
                .map(o -> ((AllContent) o).getResults())
                .flatMap(o -> Observable.fromIterable((List<AllContent.ResultsBean>) o))
                .toSortedList((o, t1) ->
                        ((AllContent.ResultsBean) t1).getPublishedAt().compareTo(((AllContent.ResultsBean) o).getPublishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<AllContent.ResultsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<AllContent.ResultsBean> value) {
                        if (!isLoadMore) {
                            results.clear();
                        }
                        results.addAll(value);
                        // getView().refreshContent();


                        if (!isLoadMore) {
                            getView().stopRefresh();
                        } else {
                            if (value.size() > 0) {
                                getView().stopLoadMore(DefaultLoadCreator.LOAD_RESULT_TEXT_LOAD_MORE);
                            } else {
                                getView().stopLoadMore(DefaultLoadCreator.LOAD_RESULT_TEXT_TO_BOTTOM);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().stopLoadMore(DefaultLoadCreator.LOAD_RESULT_TEXT_LOAD_ERROR);
                    }
                });
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
            if (DateUtil.isTheSameDay(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc();
                lastPosition = i;
                break;
            }
        }
        return videoDesc;
    }

}
