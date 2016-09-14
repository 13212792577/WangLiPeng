package com.wanglipeng.a32014.smallshopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wanglipeng.a32014.smallshopping.R;
import com.wanglipeng.a32014.smallshopping.bean.DressAll;

import java.util.List;

/**
 * Created by wanglipeng on 2016/9/3.
 */
public class DressAllAdapter extends BaseAdapter{
    Context context;
    List<DressAll> list;
    View view_now;
    int realPosition;


    public void setRealPosition(int realPosition) {
        this.realPosition = realPosition;
    }

    public View getView_now() {
        return view_now;
    }

    public DressAllAdapter(Context context, List<DressAll> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DressAll getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_all,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getTitle());
        if (realPosition==position){
            viewHolder.textView.setBackgroundResource(android.R.color.holo_blue_dark);
        }else {
            viewHolder.textView.setBackgroundResource(android.R.color.transparent);
        }
        return convertView;
    }
    public class ViewHolder{
        public TextView textView;
    }
}
