package com.fastaoe.gankio.component.gank_meizi;

import android.os.Bundle;
import android.widget.TextView;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;

import butterknife.BindView;

/**
 * Created by jinjin on 2017/11/11.
 * description:
 */

public class MeiziFragment extends BaseFragment {
    @BindView(R.id.text_view)
    TextView textView;

    public static MeiziFragment newInstance(String item) {
        MeiziFragment fragment = new MeiziFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_default;
    }

    @Override
    protected void initView() {
        textView.setText(getArguments().getString("flag"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void destroyData() {

    }
}
