package com.example.nzlive.fragment.homePage.resultInquiry;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.nzlive.R;

public class ResultInquiryActivity extends AppCompatActivity {

    private WebView wv_webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_inquiry);

        init();

        data();
    }

    private void data() {
        wv_webView.loadUrl("http://220.161.217.98:7788/");
        wv_webView.getSettings().setJavaScriptEnabled(true);   //加上这一行网页为响应式的
        wv_webView.getSettings().setSupportZoom(true);
        wv_webView.getSettings().setBuiltInZoomControls(true);

        // 让JavaScript可以自动打开windows
        wv_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        wv_webView.getSettings().setAppCacheEnabled(true);
        // 将图片调整到合适的大小
        wv_webView.getSettings().setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        wv_webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        wv_webView.getSettings().setDisplayZoomControls(true);
        // 设置默认字体大小
//        wv_webView.getSettings().setDefaultFontSize(12);

        wv_webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        wv_webView.getSettings().setDefaultTextEncodingName("UTF-8");

        wv_webView.setDrawingCacheEnabled(false);
        wv_webView.getSettings().setLoadWithOverviewMode(true);

        //解决页面渲染闪烁问题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        wv_webView.setLayerType(View.LAYER_TYPE_SOFTWARE,  null);

        wv_webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void init() {
        wv_webView=findViewById(R.id.wv_webView);
    }
}
