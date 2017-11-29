package com.fastaoe.gankio.component.gank_user;

import com.fastaoe.baselibrary.basemvp.IBaseView;
import com.fastaoe.gankio.model.database.GankItemProfile;

import java.util.List;

/**
 * Created by jinjin on 17/11/27.
 * description:
 */

public interface GankUserContract {

    public interface View extends IBaseView{
        void showContent();
    }

    public interface Presenter {
        void showContent(String query);

        List<GankItemProfile> getList();

        void setReaded(int position);
    }

}
