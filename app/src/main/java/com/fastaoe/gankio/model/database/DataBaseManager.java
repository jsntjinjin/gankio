package com.fastaoe.gankio.model.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by jinjin on 17/11/23.
 * description:
 */

public class DataBaseManager {

    private DaoSession daoSession;
    private MeiziImageProfileDao meiziImageProfileDao;

    private final static class Holder {
        private final static DataBaseManager INSTANCE = new DataBaseManager();
    }

    public static DataBaseManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        initDao(context);
    }

    private void initDao(Context context) {
        ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "gankio.db");
        Database database = helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
        meiziImageProfileDao = daoSession.getMeiziImageProfileDao();
    }

    public MeiziImageProfileDao getMeiziImageProfileDao() {
        return meiziImageProfileDao;
    }
}
