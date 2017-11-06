package com.fastaoe.gankio.base;

import android.app.Application;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class MyBaseApplication extends Application{

    public static Application sAppContext;




    @Override
    public void onCreate() {
        super.onCreate();

        this.sAppContext = this;

        initConfig();

        initHttpConfig();
    }

    private void initHttpConfig() {

    }

    private void initConfig() {

    }
}
