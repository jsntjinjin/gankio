package com.fastaoe.baselibrary.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fastaoe.baselibrary.dialog.AlertDialog;

import butterknife.ButterKnife;

/**
 * Created by jinjin on 17/10/31.
 * description:
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        loadingDialog = getLoadingDialog();
        initTitle();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        destroyData();
        super.onDestroy();
    }

    protected abstract AlertDialog getLoadingDialog();

    protected abstract int getContentView();

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void destroyData();

    @Override
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
