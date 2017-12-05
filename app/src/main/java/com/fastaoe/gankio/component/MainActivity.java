package com.fastaoe.gankio.component;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.fastaoe.baselibrary.basemvp.FragmentManagerHelper;
import com.fastaoe.gankio.R;
import com.fastaoe.gankio.base.MyBaseActivity;
import com.fastaoe.gankio.component.gank.GankFragment;
import com.fastaoe.gankio.component.gank_meizi.MeiziFragment;
import com.fastaoe.gankio.component.gank_user.GankCollectionActivity;
import com.fastaoe.gankio.component.other.AboutFragment;
import com.fastaoe.gankio.component.other.MineFragment;
import com.fastaoe.gankio.utils.LogUtil;

import java.lang.reflect.Method;

import butterknife.BindView;

public class MainActivity extends MyBaseActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.drawer_navigation)
    NavigationView drawerNavigation;
    @BindView(R.id.drawer_content)
    DrawerLayout drawerContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FragmentManagerHelper helper;

    private GankFragment gankFragment;
    private MeiziFragment meiziFragment;
    private AboutFragment aboutFragment;
    private MineFragment mineFragment;
    private DefaultFragment githubFragment;

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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerContent, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerNavigation.setNavigationItemSelectedListener(item -> {
            chooseFragment(item.getTitle().toString());
            toolbar.setTitle(item.getTitle().toString());
            drawerContent.closeDrawers();
            return false;
        });
        actionBarDrawerToggle.syncState();
        drawerContent.addDrawerListener(actionBarDrawerToggle);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        if (helper == null) {
            helper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.content_frame);
        }
        chooseFragment("干货");
    }

    @Override
    protected void destroyData() {
        Menu menu = drawerNavigation.getMenu();
        setIconsVisible(menu, true);
    }

    private void chooseFragment(String itemTitle) {
        switch (itemTitle) {
            case "干货":
                if (gankFragment == null) gankFragment = GankFragment.newInstance();
                helper.switchFragment(gankFragment);
                break;
            case "妹纸":
                if (meiziFragment == null) meiziFragment = MeiziFragment.newInstance();
                helper.switchFragment(meiziFragment);
                break;
            case "操作记录":
                if (mineFragment == null) mineFragment = MineFragment.newInstance();
                helper.switchFragment(mineFragment);
                break;
            case "关于我":
                if (aboutFragment == null) aboutFragment = AboutFragment.newInstance(itemTitle);
                helper.switchFragment(aboutFragment);
                break;
            case "登录Github":
                if (aboutFragment == null) githubFragment = DefaultFragment.newInstance(itemTitle);
                helper.switchFragment(githubFragment);
                break;
        }
    }

    private void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if (menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
