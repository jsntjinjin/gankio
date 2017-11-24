package com.fastaoe.gankio.base;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.fastaoe.gankio.model.database.DataBaseManager;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class MyBaseApplication extends Application {

    public static Application sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        this.sAppContext = this;

        initConfig();

        initHttpConfig();

        // 初始化数据库
        DataBaseManager.getInstance().init(this);

        // 调试数据库和网络 http://www.jianshu.com/p/03da9f91f41f
        if (Constants.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }

    private void initHttpConfig() {

    }

    private void initConfig() {
        // 初始化icons
        Iconify.with(new FontAwesomeModule());
    }
}
