package com.fastaoe.gankio.component.gank_all;

import com.fastaoe.baselibrary.basemvp.IBaseView;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public interface GankAllContract {

    public interface View extends IBaseView {
       void showContent();
    }

    public interface Presenter {
        void refreshContent(String category, String count ,String page);
    }

}
