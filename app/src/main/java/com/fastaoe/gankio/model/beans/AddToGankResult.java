package com.fastaoe.gankio.model.beans;

/**
 * Created by jinjin on 17/11/7.
 * description:
 */

public class AddToGankResult {


    /**
     * error : false
     * msg : 老大, 所有提交数据一切正常!
     */

    private boolean error;
    private String msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
