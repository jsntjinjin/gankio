package com.fastaoe.gankio.component.gank_detail;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.DataModel;
import com.fastaoe.gankio.model.Token;
import com.fastaoe.gankio.model.beans.GankContent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jinjin on 17/11/28.
 * description:
 */

public class GankDetailPresenter extends BasePresenter<GankDetailContract.View> implements GankDetailContract.Presenter {

    private List<GankContent.ResultsBean.ContentBean> list;
    private String imageUrl;

    @Override
    public void getContent(ArrayList<String> date) {

        if (!isViewAttached()) {
            return;
        }

        getView().showLoading();

        //noinspection unchecked
        DataModel.request(Token.GANK_CONTENT)
                .params(date.get(0), date.get(1), date.get(2))
                .execute()
                .subscribe(new Observer<GankContent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankContent value) {
                        list.clear();
                        GankContent.ResultsBean results = value.getResults();
                        addAllItem(list, results.getAndroid());
                        addAllItem(list, results.getIOS());
                        addAllItem(list, results.getHtmlWeb());
                        addAllItem(list, results.getDevelopmentResource());
                        addAllItem(list, results.getBlindRecommendation());
                        if (results.getWelfare() != null && results.getWelfare().size() > 0) {
                            imageUrl = results.getWelfare().get(0).getUrl();
                        }
                        getView().refreshContent();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    private void addAllItem(List<GankContent.ResultsBean.ContentBean> list, List<GankContent.ResultsBean.ContentBean> contentList) {
        if (setFirstItem(contentList) != null) {
            list.addAll(setFirstItem(contentList));
        }
    }

    private List<GankContent.ResultsBean.ContentBean> setFirstItem(List<GankContent.ResultsBean.ContentBean> contentList) {
        if (contentList != null && contentList.size() > 0) {
            GankContent.ResultsBean.ContentBean contentBean = contentList.get(0);
            contentBean.setFirstItem(true);
            contentList.set(0, contentBean);
        }
        return contentList;
    }

    @Override
    public List<GankContent.ResultsBean.ContentBean> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public String getImage() {
        return imageUrl;
    }
}
