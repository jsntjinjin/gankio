package com.fastaoe.gankio.component.gank;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastaoe.baselibrary.basemvp.BaseFragment;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.component.DefaultFragment;
import com.fastaoe.gankio.component.gank_all.GankAllFragment;
import com.fastaoe.gankio.widget.indicator.IndicatorAdapter;
import com.fastaoe.gankio.widget.indicator.TrackIndicatorView;

import butterknife.BindView;

/**
 * Created by jinjin on 17/11/9.
 * description:
 */

public class GankFragment extends BaseFragment {

    @BindView(R.id.trackView)
    TrackIndicatorView trackView;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private static final String[] items = {"all", "福利", "Android", "iOS", "休息", "拓展", "前端"};

    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_gank_root;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        initViewPager();
        initIndicator();
    }

    @Override
    protected void destroyData() {

    }

    private void initIndicator() {
        trackView.setAdapter(new IndicatorAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public View getView(int position, @Nullable ViewGroup parent) {
                TextView tv = new TextView(mContext);
                tv.setTextSize(14);
                tv.setText(items[position]);
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                int padding = 20;
                tv.setPadding(padding, padding, padding, padding);
                return tv;
            }

            @Override
            public View getBottomLine() {
                View view = new View(mContext);
                view.setBackgroundColor(Color.RED);
                view.setLayoutParams(new ViewGroup.LayoutParams(50, 8));
                return view;
            }

            @Override
            public void highLineIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.RED);
            }

            @Override
            public void restoreLineIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.BLACK);
            }
        }, viewpager);
    }

    private void initViewPager() {
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return GankAllFragment.newInstance();
                    default:
                        return DefaultFragment.newInstance(items[position]);
                }
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });
    }

}
