package com.fastaoe.gankio.component.gank_meizi;

import com.fastaoe.baselibrary.basemvp.IBaseView;
import com.fastaoe.gankio.model.beans.RandomData;

import java.util.List;

/**
 * Created by jinjin on 17/11/14.
 * description:
 */

public class MeiziContract {
    public interface View extends IBaseView {
        void refreshContent();

        void saveTextChanged(String msg);
    }

    public interface Presenter {

        List<RandomData.ResultsBean> getList();

        void refreshContent();

        void saveMeizi(List<RandomData.ResultsBean> list);
    }
}
