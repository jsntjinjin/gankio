package com.fastaoe.gankio.component.gank_meizi;

import com.fastaoe.baselibrary.basemvp.BasePresenter;
import com.fastaoe.gankio.model.beans.AllContent;

import java.util.List;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter {
    @Override
    public void refreshContent(boolean isLoadMore) {

    }

    @Override
    public List<AllContent.ResultsBean> getList() {
        return null;
    }
}
