package com.wanglipeng.a32014.smallshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class CollectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        //菜单栏的初始化
        initOptionMenu();
    }

    private void initOptionMenu() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("收藏");
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
