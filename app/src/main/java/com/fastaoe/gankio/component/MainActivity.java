package com.fastaoe.gankio.component;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;
import com.fastaoe.gankio.component.gank.GankFragment;

import butterknife.BindView;

public class MainActivity extends MyBaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.drawer_navigation)
    NavigationView drawerNavigation;
    @BindView(R.id.drawer_content)
    DrawerLayout drawerContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        toolbar.setTitle("干货");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerContent, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
    }

    @Override
    protected void initView() {
        drawerNavigation.setNavigationItemSelectedListener(item -> {
            drawerContent.closeDrawers();
            return false;
        });
        actionBarDrawerToggle.syncState();
        drawerContent.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void initData() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, GankFragment.newInstance())
                .commit();
    }

    @Override
    protected void destroyData() {
    }

}
