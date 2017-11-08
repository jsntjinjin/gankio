package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.HistoryForOneDay;
import com.fastaoe.gankio.model.beans.HistoryList;
import com.fastaoe.gankio.model.services.HttpEngine;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class HistoryDayModel extends BaseModel<HistoryForOneDay> {

    @Override
    public Observable<HistoryForOneDay> execute() {
        return HttpEngine.getBlogService().getHistoryDay()
                .flatMap(new Function<HistoryList, ObservableSource<HistoryForOneDay>>() {
                    @Override
                    public ObservableSource<HistoryForOneDay> apply(HistoryList historyList) throws Exception {
                        return HttpEngine.getBlogService().getHistoryForOneDay("2016", "4", "20");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
