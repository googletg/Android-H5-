package com.h5.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView mTvNoParam;
    TextView mTvHasParam;
    WebView webView;
    String url = "http://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListener();
    }

    @JavascriptInterface
    private void init(){
        mTvNoParam = findViewById(R.id.tv_no_param);
        mTvHasParam = findViewById(R.id.tv_has_param);
        webView = findViewById(R.id.webview);
        //webview配置
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //支持js
        //加载本地asset目录下的静态网页 自己写的一个h5页面放在asset目录下，
        webView.loadUrl("file:///android_asset/clickme.html");
//        webView.loadUrl(url);
        //第一个参数把自身给js 第二个参数是this的一个名字
        //这个方法用于让H5调用android方法
        webView.addJavascriptInterface(this,"android");

    }


    private void setListener(){
        mTvNoParam.setOnClickListener(this);
        mTvHasParam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_no_param:
                //参数“javascript:” + js方法名
                webView.loadUrl("javascript:message()");
                //android调用h5
                break;
            case R.id.tv_has_param:
                //在android中调用js有参函数的时候参数要加单引号
                webView.loadUrl("javascript:message2('" + "js有参函数" + "')");
                break;
        }
    }

    /**
     * h5调用android
     * 传入中andorid
     * android.setMessage()
     * 关键字.方法名
     */
    @JavascriptInterface
    public void setMessage(){
        Toast.makeText(this,"我是h5和android交互，无参函数，我弹出来了", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void setMessage(String name){
        Toast.makeText(this,"我是h5和android交互，有参函数，我要弹弹弹",Toast.LENGTH_SHORT).show();
    }
}
