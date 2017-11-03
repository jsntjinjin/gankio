package com.fastaoe.gankio.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity implements MvpView {

    @BindView(R.id.textview)
    TextView textview;
    private MvpPresenter mvpPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        mvpPresenter = new MvpPresenter();
        mvpPresenter.attachView(this);
    }

    @Override
    protected void destroyData() {
        mvpPresenter.detachView();
    }

    @Override
    public void showData(String data) {
        textview.setText(data);
    }

    @OnClick(R.id.textview)
    public void onTextClick() {
        mvpPresenter.getData();
    }
}
