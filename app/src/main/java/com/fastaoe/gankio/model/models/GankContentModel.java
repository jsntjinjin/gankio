package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.GankContent;
import com.fastaoe.gankio.model.services.HttpEngine;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/28.
 * description:
 */

public class GankContentModel extends BaseModel<GankContent> {

    @Override
    public Observable<GankContent> execute() {
        return HttpEngine.getBlogService()
                .getContent(params[0], params[1], params[2])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
