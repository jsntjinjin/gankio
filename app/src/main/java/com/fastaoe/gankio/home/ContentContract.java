package com.fastaoe.gankio.home;

import com.fastaoe.baselibrary.basemvp.IBaseView;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class ContentContract {

    public interface View extends IBaseView {

        void showData(String data);

    }

    public interface Presenter {

        void getData();

    }

}
