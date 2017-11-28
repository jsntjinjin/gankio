package com.fastaoe.gankio.component.gank_detail;

import com.fastaoe.baselibrary.basemvp.IBaseView;
import com.fastaoe.gankio.model.beans.GankContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinjin on 17/11/28.
 * description:
 */

public interface GankDetailContract {

    public interface View  extends IBaseView{
        void refreshContent();
    }

    public interface Presenter {
        public void getContent(ArrayList<String> date);

        public List<GankContent.ResultsBean.ContentBean> getList();

        String getImage();
    }

}
