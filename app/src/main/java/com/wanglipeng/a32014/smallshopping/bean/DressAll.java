package com.wanglipeng.a32014.smallshopping.bean;

import java.util.List;

/**
 * Created by wanglipeng on 2016/9/3.
 */
public class DressAll {
    private List<DressSingle> list;

    private String title;

    /**
     * @return
     */
    public List<DressSingle> getList() {
        return list;
    }

    public void setList(List<DressSingle> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
