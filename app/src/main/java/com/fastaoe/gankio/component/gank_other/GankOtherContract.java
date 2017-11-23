package com.fastaoe.gankio.component.gank_other;

import com.fastaoe.baselibrary.basemvp.IBaseView;
import com.fastaoe.gankio.model.beans.AllContent;

import java.util.List;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public interface GankOtherContract {
    public interface View extends IBaseView {

        void stopRefresh();

        void stopLoadMore();

    }

    public interface Presenter {

        void refreshContent(boolean isLoadMore, String item);

        List<AllContent.ResultsBean> getList();

        boolean setLaterReaderOrNot(int position);

        boolean setCollectionOrNot(int position);

        void setReaded(int position);
    }
}
