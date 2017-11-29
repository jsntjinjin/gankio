package com.fastaoe.gankio.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fastaoe.gankio.R;
import com.fastaoe.gankio.widget.navigationbar.DefaultNavigationBar;

import butterknife.BindView;

/**
 * Created by jinjin on 17/11/29.
 * description:
 */

public class MyBaseWebActivity extends MyBaseActivity {

    @BindView(R.id.wv_webview)
    WebView webView;
    private String url, title;

    public static void startMyBaseWebActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, MyBaseWebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_base_web_view;
    }

    @Override
    protected void initTitle() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        new DefaultNavigationBar.Builder(this)
                .setTitle(title)
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        initWebViewClient();
        initWebSetting();
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl(url);
    }

    @Override
    protected void destroyData() {

    }


    /**
     * 初始化webSetting
     */
    private void initWebSetting() {
        WebSettings settings = webView.getSettings();
        //支持获取手势焦点
        webView.requestFocusFromTouch();
        //支持JavaScript
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webView调用requestFocus时为WebView设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
        // 默认false，设置为true，
        // 网页上面报net::ERR_CACHE_MISS错误 log Cannot call determinedVisibility() - never saw a aconnetion for the pid:xxx（这个项目中我的错误是这样的）
        // settings.setBlockNetworkLoads(false);
        // settings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 设置user-agent
        // settings.setUserAgentString(MyBaseApplication.getUserAgent());
    }


    /**
     * 初始化webView显示相关
     */
    private void initWebViewClient() {
        webView.setWebViewClient(new WebViewClient() {
            //是否在WebView内加载页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else if (url.startsWith("taobao://")) {
                    return false;
                } else {
                    view.loadUrl(url);
                    return true;
                }
            }

            //页面开始加载...
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            // ssl error 错误处理
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }

            //页面完成加载...
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }

            //网络错误时的回调方法
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                showError();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }


    @Override
    protected void onPause() {
        if (webView != null) webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) webView.destroy();
    }


}
