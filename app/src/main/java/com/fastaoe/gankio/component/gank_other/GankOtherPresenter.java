package com.fastaoe.gankio.component.gank_other;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.AllContent;
import com.fastaoe.gankio.model.database.DataBaseManager;
import com.fastaoe.gankio.model.database.GankItemProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    public boolean setLaterReaderOrNot(int position) {
        AllContent.ResultsBean resultsBean = getList().get(position);
        GankItemProfile gankItemProfile = new GankItemProfile(
                resultsBean.get_id(),
                resultsBean.getCreatedAt(),
                resultsBean.getDesc(),
                resultsBean.getPublishedAt (),
                resultsBean.getType(),
                resultsBean.getUrl(),
                resultsBean.getWho(),
                list2String(resultsBean.getImages()),
                false,
                new Date(),
                true,
                new Date(System.currentTimeMillis()),
                false,
                new Date());
        DataBaseManager.getInstance().getGankItemProfileDao().insert(gankItemProfile);
        return false;
    }

    @Override
    public boolean setCollectionOrNot(int position) {
        return false;
    }

    @Override
    public void setReaded(int position) {

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

        //noinspection unchecked
        DataModel.request(Token.ALL_CONTENT)
                .params(item, "10", String.valueOf(page))
                .execute()
                .subscribe(new Observer<AllContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AllContent value) {
                        if (!isLoadMore) {
                            gankOhterList.clear();
                        }
                        gankOhterList.addAll(value.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (!isLoadMore) {
                            getView().stopRefresh();
                        } else {
                            getView().stopLoadMore();
                        }
                    }
                });
    }

    private String list2String(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        for (String string : list) {
            buffer.append(string);
        }
        return buffer.toString();
    }
}
