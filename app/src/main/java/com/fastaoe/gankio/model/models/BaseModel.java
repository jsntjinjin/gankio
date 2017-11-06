package com.fastaoe.gankio.model.models;

import com.fastaoe.gankio.model.callback.Callback;

/**
 * Created by jinjin on 17/11/6.
 * description:
 */

public abstract class BaseModel<T> {

    protected String[] params;

    public BaseModel params(String... args) {
        this.params = args;
        return this;
    }

    public abstract void execute(Callback<T> callback);

}
