package com.fastaoe.gankio.component.gank_all;

import com.fastaoe.baselibrary.basemvp.IBaseView;
import com.fastaoe.gankio.model.beans.AllContent;

import java.util.List;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public interface GankAllContract {

    public interface View extends IBaseView {
        void refreshContent();

        void stopRefresh();

        void stopLoadMore();

    }

    public interface Presenter {

        void refreshContent(String category, boolean isLoadMore);

        List<AllContent.ResultsBean> getList();
    }

}
