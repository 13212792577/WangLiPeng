package com.wanglipeng.a32014.smallshopping.utils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanglipeng on 2016/9/6.
 */
public class GetResource {

    //从本地获取数据  如果本地没有数据就从网上下载数据保存到本地
    public static String getResource(String path_file, String path){
        Log.e("======","==path=="+path);
        final File file = new File(path_file);
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
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bos.write(data.getBytes());
                    bos.close();
                }
            });
        }

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
            return sb.toString();
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}
