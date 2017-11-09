package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.model.services.HttpEngine;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public class AllContentModel extends BaseModel<AllContent> {
    @Override
    public Observable<AllContent> execute() {
        return HttpEngine.getBlogService()
                .getAllData(params[0],params[1],params[2])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
