package com.fastaoe.gankio.base;

import com.fastaoe.baselibrary.basemvp.BaseActivity;
import com.fastaoe.baselibrary.dialog.AlertDialog;

/**
 * Created by jinjin on 17/10/31.
 * description:
 */

public abstract class MyBaseActivity extends BaseActivity {

    @Override
    protected AlertDialog getLoadingDialog() {
        return null;
    }

}
