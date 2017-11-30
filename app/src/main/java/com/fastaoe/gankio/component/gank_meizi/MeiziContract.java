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
        void refreshContent(int savedSize);

        void saveTextChanged(String msg);

        void saveMeiziStart();

        void saveMeiziEnd();

        int setSavePosition();

        void showMeizi(RandomData.ResultsBean resultsBean);

        void dialogRefresh(boolean saved);
    }

    public interface Presenter {

        List<RandomData.ResultsBean> getList();

        void refreshContent();

        void showMeiziDialog(int position);

        void saveMeizi(List<RandomData.ResultsBean> list);

        void saveMeizi(RandomData.ResultsBean resultsBean);

        RandomData.ResultsBean getShowMeizi();
    }
}
