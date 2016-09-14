package com.wanglipeng.a32014.smallshopping;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wanglipeng.a32014.smallshopping.adapter.DressAdapter;
import com.wanglipeng.a32014.smallshopping.bean.DressOwn;
import com.wanglipeng.a32014.smallshopping.utils.InitFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http://api-v2.mall.hichao.com/search/skus?query=%20%20&sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1
 */
public class Main2Activity extends AppCompatActivity {

    GridView gridView1;
    File file;
    ActionBar actionBar;
    List<DressOwn> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        gridView1 = (GridView) findViewById(R.id.gridView2);

        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        //菜单栏的初始化
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(word);

        Log.e("=======", "==word====" + word);
        String encod = URLEncoder.encode(word);
        Log.e("=======", "==encod====" + encod);
        String path = "http://api-v2.mall.hichao.com/search/skus?" +
                "query=" + encod + "%20%20&" +
                "sort=all&ga=%252Fsearch%252Fskus&flag=&cat=&asc=1";

        file = InitFile.initFile(word);
        getResource(path);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                intent.putExtra("scorceId",list.get(position).getSourceId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public List<DressOwn> jsonUtils(String data) {
        try {
            JSONObject object1 = new JSONObject(data);
            JSONObject object2 = object1.getJSONObject("data");
            JSONArray array1 = object2.getJSONArray("items");
            int length1 = array1.length();

            List<DressOwn> list = new ArrayList<>();
            for (int i = 0; i < length1; i++) {
                JSONObject object3 = array1.getJSONObject(i);
                JSONObject object4 = object3.getJSONObject("component");
                DressOwn dressOwn = new DressOwn();
                String description = object4.getString("description");
                String picUrl = object4.getString("picUrl");
                String price = object4.getString("price");
                String origin_price = object4.getString("origin_price");
                String sales = object4.getString("sales");
                JSONObject object5 = object4.getJSONObject("action");
                String sourceId = object5.getString("sourceId");
                dressOwn.setDescription(description);
                dressOwn.setOrigin_price(origin_price);
                dressOwn.setPicUrl(picUrl);
                dressOwn.setPrice(price);
                dressOwn.setSales(sales);
                dressOwn.setSourceId(sourceId);
                list.add(dressOwn);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getResource(String path){
        if(!file.exists()){
            //从网上下载数据到本地
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path).build();
            Log.e("=====","==request=="+request);
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("=====","==call=="+call);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Log.e("=====","==成功=="+call);
                    final String data = response.body().string();
                    Log.e("===","==data=="+data);
                    //将数据存到指定目录

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                                bos.write(data.getBytes());
                                bos.close();
                                list = jsonUtils(data);
                                DressAdapter adapter = new DressAdapter(list, Main2Activity.this);
                                gridView1.setAdapter(adapter);
                            }catch (Exception e){
                                e.getMessage();
                            }
                        }
                    });
                }
            });
        }else{
            //从本地读取出数据
            try {
                InputStream is = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String temp = "";

                while((temp=br.readLine())!=null){
                    sb.append(temp);
                }
                is.close();
                list = jsonUtils(sb.toString());
                DressAdapter adapter = new DressAdapter(list, this);
                gridView1.setAdapter(adapter);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
