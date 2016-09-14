package com.wanglipeng.a32014.smallshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Main3Activity extends AppCompatActivity {

    ProgressBar progressBar;
    ActionBar actionBar;
    WebView webView;
    String path = "http://m.hichao.com/lib/interface.php?m=goodsdetail&sid=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        actionBar = getSupportActionBar();
        actionBar.setTitle("详情");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String scorceId = intent.getStringExtra("scorceId");

        String scorcePath = path+scorceId;

        webView.loadUrl(scorcePath);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress!=100){
                    progressBar.setProgress(newProgress);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(this,Main2Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
