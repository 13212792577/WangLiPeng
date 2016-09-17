package com.wanglipeng.a32014.smallshopping;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    ProgressBar progressBar;
    ActionBar actionBar;
    WebView webView;
    TextView textView;   //小圆圈
    int i=0;             //小圆圈的内容
    String path = "http://m.hichao.com/lib/interface.php?m=goodsdetail&sid=";
    //从Main2Activity接受的数据 用于传给购物车界面
    String picUrl;
    String price;
    String description;
    SQLiteDatabase database;  //数据库存储
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.count);

        actionBar = getSupportActionBar();
        actionBar.setTitle("详情");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String scorceId = intent.getStringExtra("scorceId");
        picUrl = intent.getStringExtra("picUrl");
        price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");

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

        database = openOrCreateDatabase("wanglipeng",MODE_PRIVATE,null);
        String sql = "create table if not exists wang"+" ( _id integer primary key autoincrement,picUrl text,description text, price real,count integer)";
        database.execSQL(sql);
        int textviewNum = database.query("wang",null,null,null,null,null,null).getCount();
        textView.setText(String.valueOf(textviewNum));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(this,Main2Activity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**收藏点击事件
     * @param v
     */
    public void clickCollect(View v){

    }

    /**加入购物车点击事件
     * @param v
     */
    int count = 0;
    public void clickCar(View v){
        Cursor cursor = database.query("wang",null,"picUrl = ?",new String[]{picUrl},null,null,null);
        if(cursor.getCount()==0){
            //插入
            count = 1;
            ContentValues values = new ContentValues();
            values.put("picUrl",picUrl);
            values.put("description",description);
            values.put("price",price);
            values.put("count",count);
            database.insert("wang",null,values);
        }else {
            //更新
            count++;
            ContentValues values = new ContentValues();
            values.put("count",count);
            database.update("wang",values,"picUrl = ? ",new String[]{picUrl});
        }
        int textviewNum = database.query("wang",null,null,null,null,null,null).getCount();
        textView.setText(String.valueOf(textviewNum));
    }

    /**立即购买点击事件
     * @param v
     */
    public void clickBuy(View v){

    }
}
