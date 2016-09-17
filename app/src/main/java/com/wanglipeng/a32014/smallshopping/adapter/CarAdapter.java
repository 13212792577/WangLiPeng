package com.wanglipeng.a32014.smallshopping.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wanglipeng.a32014.smallshopping.R;

/**
 * Created by wanglipeng on 2016/9/17.
 */
public class CarAdapter extends CursorAdapter{

    SQLiteDatabase database;
    public CarAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car,null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        database = context.openOrCreateDatabase("wanglipeng",Context.MODE_PRIVATE,null);

        TextView textView1 = (TextView) view.findViewById(R.id.text_item_description);
        TextView textView2 = (TextView) view.findViewById(R.id.text_item_price);
        TextView textView3 = (TextView) view.findViewById(R.id.textview_center);
        textView1.setText(cursor.getString(cursor.getColumnIndex("description")));
        textView2.setText(cursor.getString(cursor.getColumnIndex("price")));
        int count = cursor.getInt(cursor.getColumnIndex("count"));
        textView3.setText(String.valueOf(count));
        String path = cursor.getString(cursor.getColumnIndex("picUrl"));
        ImageView imageView = (ImageView) view.findViewById(R.id.image_item_car);
        Picasso.with(context).load(path).into(imageView);

        //点击事件  全选 加一 减一
    }
}
