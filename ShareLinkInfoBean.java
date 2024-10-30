package com.xmen.xteam.communication.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangqi on 2018/5/4.
 */

public class ShareLinkInfoBean implements MultiItemEntity {
    private String title;
    private String link;
    private String head;
    private int itemType;
    private String time;
    private int counts;
    public static int TITLE=0;
    public static int CONTENT=1;
    private ShareLinkInfoBean titleBean;

    public ShareLinkInfoBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(ShareLinkInfoBean titleBean) {
        this.titleBean = titleBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "ShareLinkInfoBean{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", head='" + head + '\'' +
                ", itemType=" + itemType +
                ", time='" + time + '\'' +
                ", counts=" + counts +
                '}';
    }
}
