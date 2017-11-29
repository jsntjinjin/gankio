package com.fastaoe.gankio.component.gank_other;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.model.database.DataBaseManager;
import com.fastaoe.gankio.model.database.GankItemProfile;
import com.fastaoe.gankio.widget.recycler.DefaultLoadCreator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class GankOtherPresenter extends BasePresenter<GankOtherContract.View> implements GankOtherContract.Presenter {

    private int page = 1;
    private List<AllContent.ResultsBean> gankOhterList;

    @Override
    public List<AllContent.ResultsBean> getList() {
        if (gankOhterList == null) {
            gankOhterList = new ArrayList<>();
        }
        return gankOhterList;
    }

    @Override
    public void setLaterReaderOrNot(int position) {
        AllContent.ResultsBean resultsBean1 = getList().get(position);
        GankItemProfile gankItemProfile = DataBaseManager.getInstance()
                .getGankItemProfileDao().load(resultsBean1.get_id());
        if (gankItemProfile != null) {
            // 保存过
            gankItemProfile.setLaterReadered(!gankItemProfile.getLaterReadered());
            gankItemProfile.setLaterReaderedAt(new Date(System.currentTimeMillis()));
            DataBaseManager.getInstance().getGankItemProfileDao().update(gankItemProfile);
            resultsBean1.setLaterReadered(gankItemProfile.getLaterReadered());
        } else {
            // 没保存过 -> 保存
            GankItemProfile toSaveGankItemProfile = new GankItemProfile(
                    resultsBean1.get_id(),
                    resultsBean1.getCreatedAt(),
                    resultsBean1.getDesc(),
                    resultsBean1.getPublishedAt(),
                    resultsBean1.getType(),
                    resultsBean1.getUrl(),
                    resultsBean1.getWho(),
                    list2String(resultsBean1.getImages()),
                    false,
                    new Date(0),
                    true,
                    new Date(System.currentTimeMillis()),
                    false,
                    new Date(0));
            DataBaseManager.getInstance().getGankItemProfileDao().insert(toSaveGankItemProfile);
            resultsBean1.setLaterReadered(true);
        }
        getList().set(position, resultsBean1);
        getView().refreshRecycle();
    }

    @Override
    public void setCollectionOrNot(int position) {
        AllContent.ResultsBean resultsBean1 = getList().get(position);
        GankItemProfile gankItemProfile = DataBaseManager.getInstance()
                .getGankItemProfileDao().load(resultsBean1.get_id());
        if (gankItemProfile != null) {
            // 保存过
            gankItemProfile.setCollectioned(!gankItemProfile.getCollectioned());
            gankItemProfile.setCollectionedAt(new Date(System.currentTimeMillis()));
            DataBaseManager.getInstance().getGankItemProfileDao().update(gankItemProfile);
            resultsBean1.setCollectioned(gankItemProfile.getCollectioned());
        } else {
            // 没保存过 -> 保存
            GankItemProfile toSaveGankItemProfile = new GankItemProfile(
                    resultsBean1.get_id(),
                    resultsBean1.getCreatedAt(),
                    resultsBean1.getDesc(),
                    resultsBean1.getPublishedAt(),
                    resultsBean1.getType(),
                    resultsBean1.getUrl(),
                    resultsBean1.getWho(),
                    list2String(resultsBean1.getImages()),
                    true,
                    new Date(System.currentTimeMillis()),
                    false,
                    new Date(0),
                    false,
                    new Date(0));
            DataBaseManager.getInstance().getGankItemProfileDao().insert(toSaveGankItemProfile);
            resultsBean1.setCollectioned(true);
        }
        getList().set(position, resultsBean1);
        getView().refreshRecycle();
    }

    @Override
    public void setReaded(int position) {
        AllContent.ResultsBean resultsBean1 = getList().get(position);
        GankItemProfile gankItemProfile = DataBaseManager.getInstance()
                .getGankItemProfileDao().load(resultsBean1.get_id());
        if (gankItemProfile != null) {
            // 保存过
            gankItemProfile.setReaded(true);
            gankItemProfile.setReadedAt(new Date(System.currentTimeMillis()));
            DataBaseManager.getInstance().getGankItemProfileDao().update(gankItemProfile);
        } else {
            // 没保存过 -> 保存
            GankItemProfile toSaveGankItemProfile = new GankItemProfile(
                    resultsBean1.get_id(),
                    resultsBean1.getCreatedAt(),
                    resultsBean1.getDesc(),
                    resultsBean1.getPublishedAt(),
                    resultsBean1.getType(),
                    resultsBean1.getUrl(),
                    resultsBean1.getWho(),
                    list2String(resultsBean1.getImages()),
                    false,
                    new Date(0),
                    false,
                    new Date(0),
                    true,
                    new Date(System.currentTimeMillis()));
            DataBaseManager.getInstance().getGankItemProfileDao().insert(toSaveGankItemProfile);
        }
    }

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

        boolean isToBottom;

        //noinspection unchecked
        Observable.zip(
                DataModel.request(Token.ALL_CONTENT).params(item, "10", String.valueOf(page)).execute(),
                new Observable<List<GankItemProfile>>() {
                    @Override
                    protected void subscribeActual(Observer<? super List<GankItemProfile>> observer) {
                        observer.onNext(DataBaseManager.getInstance().getGankItemProfileDao().loadAll());
                    }
                }.subscribeOn(Schedulers.io()),
                (BiFunction<AllContent, List<GankItemProfile>, List<AllContent.ResultsBean>>) (allContent, gankItemProfiles) -> {
                    for (AllContent.ResultsBean resultsBean : allContent.getResults()) {
                        for (GankItemProfile gankItemProfile : gankItemProfiles) {
                            if (resultsBean.get_id().equals(gankItemProfile.getId())) {
                                resultsBean.setCollectioned(gankItemProfile.getCollectioned());
                                resultsBean.setLaterReadered(gankItemProfile.getLaterReadered());
                                break;
                            }
                        }
                    }
                    return allContent.getResults();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (!isLoadMore) {
                        gankOhterList.clear();
                    }
                    gankOhterList.addAll((List<AllContent.ResultsBean>)value);
                    if (!isLoadMore) {
                        getView().stopRefresh();
                    } else {
                        if (((List<AllContent.ResultsBean>)value).size() > 0) {
                            getView().stopLoadMore(DefaultLoadCreator.LOAD_RESULT_TEXT_LOAD_MORE);
                        } else {
                            getView().stopLoadMore(DefaultLoadCreator.LOAD_RESULT_TEXT_TO_BOTTOM);
                        }
                    }
                }, throwable -> getView().stopLoadMore(DefaultLoadCreator.LOAD_RESULT_TEXT_LOAD_ERROR));
        //                .map(new Function<List<AllContent.ResultsBean>, Boolean>() {
        //                    @Override
        //                    public Boolean apply(List<AllContent.ResultsBean> resultsBeans) throws Exception {
        //                        if (!isLoadMore) {
        //                            gankOhterList.clear();
        //                        }
        //                        gankOhterList.addAll(resultsBeans);
        //                        return resultsBeans.size() > 0;
        //                    }
        //                })
        //                .subscribe(isToBottom1 -> {
        //                    if (!isLoadMore) {
        //                        getView().stopRefresh();
        //                    } else {
        //                        getView().stopLoadMore();
        //                    }
        //                });

        //        //noinspection unchecked
        //        DataModel.request(Token.ALL_CONTENT)
        //                .params(item, "10", String.valueOf(page))
        //                .execute()
        //                .subscribe(new Observer<AllContent>() {
        //                    @Override
        //                    public void onSubscribe(Disposable d) {
        //
        //                    }
        //
        //                    @Override
        //                    public void onNext(AllContent value) {
        //                        if (!isLoadMore) {
        //                            gankOhterList.clear();
        //                        }
        //                        gankOhterList.addAll(value.getResults());
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

    private String list2String(List<String> list) {
        if (list == null || list.size() == 0) {
            return "";
        } else {
            StringBuffer buffer = new StringBuffer();
            for (String string : list) {
                buffer.append(string).append("|");
            }
            return buffer.toString();
        }
    }
}
