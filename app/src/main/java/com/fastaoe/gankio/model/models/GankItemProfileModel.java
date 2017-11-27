package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.database.DataBaseManager;
import com.fastaoe.gankio.model.database.GankItemProfile;
import com.fastaoe.gankio.model.database.GankItemProfileDao;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/27.
 * description:
 */

public class GankItemProfileModel extends BaseModel<List<GankItemProfile>> {

    public static final String QUERY_COLLECTION = "collection";
    public static final String QUERY_LATER_READER = "later_reader";
    public static final String QUERY_READED = "readed";

    @Override
    public Observable<List<GankItemProfile>> execute() {
        WhereCondition eq = null;
        Property order_desc = null;
        switch (params[0]) {
            case QUERY_COLLECTION:
                eq = GankItemProfileDao.Properties.Collectioned.eq(true);
                order_desc = GankItemProfileDao.Properties.CollectionedAt;
                break;
            case QUERY_LATER_READER:
                eq = GankItemProfileDao.Properties.LaterReadered.eq(true);
                order_desc = GankItemProfileDao.Properties.LaterReaderedAt;
                break;
            case QUERY_READED:
                eq = GankItemProfileDao.Properties.Readed.eq(true);
                order_desc = GankItemProfileDao.Properties.ReadedAt;
                break;
        }
        Property finalOrder_desc = order_desc;
        WhereCondition finalEq = eq;
        return Observable
                .create((ObservableOnSubscribe<List<GankItemProfile>>) e ->
                        e.onNext(DataBaseManager.getInstance().getGankItemProfileDao()
                                .queryBuilder()
                                .where(finalEq)
                                .orderDesc(finalOrder_desc).list()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
