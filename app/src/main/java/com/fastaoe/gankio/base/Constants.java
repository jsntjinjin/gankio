package com.fastaoe.gankio.base;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class Constants {

    public static final boolean DEBUG = true;

    public static ENVIRONMENT ENV = ENVIRONMENT.DEV;

    public enum ENVIRONMENT {
        PRD,
        STG,
        DEV
    }

    public static String getEndPoint() {
        switch (ENV) {
            case DEV:
                return "http://gank.io/api/";
            case PRD:
                return "http://gank.io/api/";
            case STG:
                return "http://gank.io/api/";
        }
        return "http://gank.io/api/";
    }

    public static final int PERMISSION_READ_WRITE_STORAGE_MEIZI_LIST = 0x0001;
    public static final int PERMISSION_READ_WRITE_STORAGE_MEIZI = 0x0002;
}
