package com.wanglipeng.a32014.smallshopping;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.wanglipeng.a32014.smallshopping.adapter.DressAllAdapter;
import com.wanglipeng.a32014.smallshopping.adapter.DressSingleAdapter;
import com.wanglipeng.a32014.smallshopping.adapter.MenuAdapter;
import com.wanglipeng.a32014.smallshopping.bean.DressAll;
import com.wanglipeng.a32014.smallshopping.bean.DressSingle;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView,menuList;
    GridView gridView;
    String path_list = "http://api-v2.mall.hichao.com/category/list?ga=%2Fcategory%2Flist";

    List<DressAll> dressAll_list;
    List<DressSingle> dressSingle_list;
    DressAllAdapter adapter;
    DressSingleAdapter singleAdapter;
    String name = "dressAll";  //目录名
    File file;

    ActionBar actionBar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        menuList = (ListView) findViewById(R.id.menu_listview);
        gridView = (GridView) findViewById(R.id.gridView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        actionBar = getSupportActionBar();
        actionBar.setTitle("首页");
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        MenuAdapter menuAdapter = new MenuAdapter(this);
        menuList.setAdapter(menuAdapter);
        file = InitFile.initFile(name);

        getResource();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setRealPosition(position);
                adapter.notifyDataSetChanged();
                dressSingle_list = dressAll_list.get(position).getList();
                singleAdapter = new DressSingleAdapter(MainActivity.this, dressSingle_list);
                gridView.setAdapter(singleAdapter);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //word是点击想的具体服饰类型
                String word = dressSingle_list.get(position).getWord();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("word", word);
                startActivity(intent);
            }
        });
    }

    //解析json字符串
    public List<DressAll> getDressAll(String data) {

        List<DressAll> list_all = new ArrayList<>();
        try {
            JSONObject object1 = new JSONObject(data);
            JSONObject object2 = object1.getJSONObject("data");
            JSONArray array1 = object2.getJSONArray("items");
            int length1 = array1.length();

            for (int i = 0; i < length1; i++) {
                JSONObject object3 = array1.getJSONObject(i);
                JSONObject object6 = object3.getJSONObject("component");
                DressAll dressAll = new DressAll();
                String title = object6.getString("title");
                dressAll.setTitle(title);

                List<DressSingle> list_single = new ArrayList<>();
                JSONArray array2 = object6.getJSONArray("items");
                int length2 = array2.length();
                for (int j = 0; j < length2; j++) {
                    JSONObject object4 = array2.getJSONObject(j);
                    JSONObject object5 = object4.getJSONObject("component");
                    DressSingle dressSingle = new DressSingle();
                    String word = object5.getString("word");
                    String picUrl = object5.getString("picUrl");
                    dressSingle.setWord(word);
                    dressSingle.setPicUrl(picUrl);
                    list_single.add(dressSingle);
                }
                dressAll.setList(list_single);
                list_all.add(dressAll);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list_all;
    }

    public void getResource(){
        if(!file.exists()){
            //从网上下载数据到本地
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path_list).build();
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
                                dressAll_list = getDressAll(data);
                                adapter = new DressAllAdapter(MainActivity.this, dressAll_list);
                                adapter.setRealPosition(0);   //默认第一项为选择项
                                listView.setAdapter(adapter);
                                //listview 点击之前 就加载第一项的gridview
                                dressSingle_list = dressAll_list.get(0).getList();
                                singleAdapter = new DressSingleAdapter(MainActivity.this, dressSingle_list);
                                gridView.setAdapter(singleAdapter);
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
                dressAll_list = getDressAll(sb.toString());
                adapter = new DressAllAdapter(this, dressAll_list);
                adapter.setRealPosition(0);   //默认第一项为选择项
                listView.setAdapter(adapter);
                //listview 点击之前 就加载第一项的gridview
                dressSingle_list = dressAll_list.get(0).getList();
                singleAdapter = new DressSingleAdapter(this, dressSingle_list);
                gridView.setAdapter(singleAdapter);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /**home键的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(Gravity.LEFT);
            }else{
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //
    public void clickCircle(){
    }
}
