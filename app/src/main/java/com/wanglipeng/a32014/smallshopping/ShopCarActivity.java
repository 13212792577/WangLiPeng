package com.wanglipeng.a32014.smallshopping;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.wanglipeng.a32014.smallshopping.adapter.CarAdapter;
import com.wanglipeng.a32014.smallshopping.bean.DressCar;

import java.util.ArrayList;
import java.util.List;

public class ShopCarActivity extends AppCompatActivity {

    ListView listView;  //购物车的列表
    CheckBox checkBox;  //全选的checkbox
    TextView textView;   //合计的总额
    Button button;       //结算
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        listView = (ListView) findViewById(R.id.listview_car);
        checkBox = (CheckBox) findViewById(R.id.cb_all);
        textView = (TextView) findViewById(R.id.textview_car);
        initOptionMenu();
        List<DressCar> list = new ArrayList<>();

        database = openOrCreateDatabase("wanglipeng",MODE_PRIVATE,null);
        Cursor cursor = database.query("wang",null,null,null,null,null,null);
        CarAdapter carAdapter = new CarAdapter(this,cursor);
        listView.setAdapter(carAdapter);
    }

    private void initOptionMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("王立鹏王八蛋");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
