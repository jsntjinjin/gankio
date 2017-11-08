package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.Search;
import com.fastaoe.gankio.model.services.HttpEngine;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/8.
 * description:
 */

public class SearchModel extends BaseModel<Search> {
    @Override
    public Observable<Search> execute() {
        return HttpEngine.getBlogService().getSearch(params[0], params[1], params[2])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
