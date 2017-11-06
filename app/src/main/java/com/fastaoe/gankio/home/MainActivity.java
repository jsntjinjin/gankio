package com.fastaoe.gankio.home;

import android.widget.TextView;

import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends MyBaseActivity implements ContentContract.View {

    @BindView(R.id.textview)
    TextView textview;
    private ContentPresenter contentPresenter;

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
        contentPresenter = new ContentPresenter();
        contentPresenter.attachView(this);
    }

    @Override
    protected void destroyData() {
        contentPresenter.detachView();
    }

    @Override
    public void showData(String data) {
        textview.setText(data);
    }

    @OnClick(R.id.textview)
    public void onTextClick() {
        contentPresenter.getData();
    }
}
