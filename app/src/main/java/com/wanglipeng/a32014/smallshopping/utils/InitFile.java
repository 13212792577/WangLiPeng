package com.wanglipeng.a32014.smallshopping.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by wanglipeng on 2016/9/6.
 */
public class InitFile {
    //创建要存储数据的文件目录
    public static File initFile(String name){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path2 = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"0906";
            File file_directory = new File(path2);
            if(!file_directory.exists()){
                file_directory.mkdirs();
            }
            String path = path2+File.separator+name;

            File file = new File(path);
            Log.e("=====","path"+file.getAbsolutePath());
            return file;
        }
        return null;
    }
}
