package com.wanglipeng.a32014.smallshopping.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wanglipeng.a32014.smallshopping.R;
import com.wanglipeng.a32014.smallshopping.bean.DressOwn;

import java.util.List;

/**
 * Created by wanglipeng on 2016/9/5.
 */
public class DressAdapter extends BaseAdapter{
    List<DressOwn> list;
    Context context;

    public DressAdapter(List<DressOwn> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DressOwn getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder2 viewHolder2;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dress,null);
            viewHolder2 = new ViewHolder2(convertView);
            convertView.setTag(viewHolder2);
        }else{
            viewHolder2 = (ViewHolder2) convertView.getTag();
        }

        viewHolder2.textView1.setText(list.get(position).getDescription());
        viewHolder2.textView2.setText("销售："+list.get(position).getSales());
        viewHolder2.textView3.setText("￥"+list.get(position).getPrice());
        viewHolder2.textView4.setText("原￥"+list.get(position).getOrigin_price());
        viewHolder2.textView4.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        String path = list.get(position).getPicUrl();
        Picasso.with(context).load(path).into(viewHolder2.imageView);
        return convertView;
    }

    class ViewHolder2{
        ImageView imageView;
        TextView textView1,textView2,textView3,textView4;
        View view;

        public ViewHolder2(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView2);
            textView1 = (TextView) view.findViewById(R.id.textView);
            textView2 = (TextView) view.findViewById(R.id.textView3);
            textView3 = (TextView) view.findViewById(R.id.textView4);
            textView4 = (TextView) view.findViewById(R.id.textView5);
        }
    }
}
