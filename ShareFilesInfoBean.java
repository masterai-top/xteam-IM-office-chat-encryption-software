package com.xmen.xteam.communication.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangqi on 2018/4/24.
 */

public class ShareFilesInfoBean implements MultiItemEntity {
    private String fnm;
    private int odsz;
    private String url;
    private int itemType;
    private String time;
    private int counts;
    private String namePerson;
    public static int TITLE=0;
    public static int CONTENT=1;
    private ShareFilesInfoBean titleBean;

    public ShareFilesInfoBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(ShareFilesInfoBean titleBean) {
        this.titleBean = titleBean;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getFnm() {
        return fnm;
    }

    public void setFnm(String fnm) {
        this.fnm = fnm;
    }

    public int getOdsz() {
        return odsz;
    }

    public void setOdsz(int odsz) {
        this.odsz = odsz;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    @Override
    public String toString() {
        return "ShareFilesInfoBean{" +
                "fnm='" + fnm + '\'' +
                ", odsz=" + odsz +
                ", url='" + url + '\'' +
                ", itemType=" + itemType +
                ", time='" + time + '\'' +
                ", counts=" + counts +
                ", namePerson='" + namePerson + '\'' +
                '}';
    }
}
