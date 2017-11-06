package com.fastaoe.gankio.model;

import com.fastaoe.gankio.model.models.BaseModel;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public class DataModel {

    public static BaseModel request(String token) {

        BaseModel model = null;

        try {
            model = (BaseModel) Class.forName(token).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return model;
    }

}
