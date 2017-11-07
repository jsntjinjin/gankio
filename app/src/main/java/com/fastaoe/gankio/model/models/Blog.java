package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.HistoryForOneDay;
import com.fastaoe.gankio.model.beans.HistoryList;
import com.fastaoe.gankio.model.services.HttpEngine;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class Blog extends BaseModel<HistoryForOneDay> {

    @Override
    public Observable<HistoryForOneDay> execute() {
        return HttpEngine.getBlogService().getHistoryDay()
                .flatMap(new Func1<HistoryList, Observable<HistoryForOneDay>>() {
                    @Override
                    public Observable<HistoryForOneDay> call(HistoryList historyList) {
                        return HttpEngine.getBlogService().getHistoryForOneDay("2016", "4", "20");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
