package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.beans.RandomData;
import com.fastaoe.gankio.model.database.DataBaseManager;
import com.fastaoe.gankio.model.database.MeiziImageProfile;
import com.fastaoe.gankio.model.services.HttpEngine;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/16.
 * description:
 */

public class RandomMeiziModel extends BaseModel<RandomData> {
    @Override
    public Observable execute() {
        return HttpEngine.getBlogService()
                .getRandomData("福利", "9")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable getMeiziSavingList() {
        return Observable.create(
                (ObservableOnSubscribe<List<MeiziImageProfile>>) e ->
                        e.onNext(DataBaseManager.getInstance().getMeiziImageProfileDao().loadAll()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
