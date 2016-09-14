package com.wanglipeng.a32014.smallshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wanglipeng.a32014.smallshopping.R;
import com.wanglipeng.a32014.smallshopping.bean.DressSingle;

import java.util.List;

/**
 * Created by wanglipeng on 2016/9/4.
 */
public class DressSingleAdapter extends BaseAdapter{
    Context context;
    List<DressSingle> list;

    public DressSingleAdapter(Context context, List<DressSingle> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DressSingle getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_single,null);
        TextView textView = (TextView) convertView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        textView.setText(list.get(position).getWord());
        //具体服装的图片地址
        String path = list.get(position).getPicUrl();
        Picasso.with(context).load(path).into(imageView);
        return convertView;
    }
}
