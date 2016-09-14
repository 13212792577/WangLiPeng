package com.wanglipeng.a32014.smallshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanglipeng.a32014.smallshopping.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglipeng on 2016/9/13.
 */
public class MenuAdapter extends BaseAdapter{
    int[] img = new int[]{
            android.R.drawable.ic_menu_my_calendar,
            android.R.drawable.ic_menu_save,
            android.R.drawable.ic_menu_report_image,
            android.R.drawable.ic_menu_delete
    };
    String[] name = new String[]{"首页","收藏","购物车","退出登录"};
    Context context;

    public MenuAdapter( Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public String getItem(int position) {
        return name[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderMenu viewHolderMenu;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_menu,null);
            viewHolderMenu = new ViewHolderMenu(convertView);
            convertView.setTag(viewHolderMenu);
        }else{
            viewHolderMenu = (ViewHolderMenu) convertView.getTag();
        }
        viewHolderMenu.imageView.setImageResource(img[position]);
        viewHolderMenu.textView.setText(name[position]);
        return convertView;
    }

    class ViewHolderMenu{
        ImageView imageView;
        TextView textView;
        View view;

        public ViewHolderMenu(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.menu_image);
            textView = (TextView) view.findViewById(R.id.menu_list);
        }
    }
}
