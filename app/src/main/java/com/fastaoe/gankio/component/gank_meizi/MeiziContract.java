package com.fastaoe.gankio.component.gank_meizi;

import com.fastaoe.baselibrary.basemvp.IBaseView;
import com.fastaoe.gankio.model.beans.AllContent;

import java.util.List;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class MeiziContract {
    public interface View extends IBaseView {
        void refreshContent();

        void stopRefresh();

        void stopLoadMore();

    }

    public interface Presenter {

        void refreshContent(boolean isLoadMore);

        List<AllContent.ResultsBean> getList();
    }
}