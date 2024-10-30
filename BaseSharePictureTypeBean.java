package com.xmen.xteam.communication.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by zhangqi on 2018/4/23.
 */

public class BaseSharePictureTypeBean extends BaseTypeBean implements MultiItemEntity  {

    private int titleCounts;
    private String fnm;
    private int oh;
    private int ow;
    private int th;
    private int tw;
    private int counts;
    private String time;
    public static int TITLE=0;
    public static int CONTENT=1;
    private BaseSharePictureTypeBean titleBean;

    public BaseSharePictureTypeBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(BaseSharePictureTypeBean titleBean) {
        this.titleBean = titleBean;
    }

    public int getTitleCounts() {
        return titleCounts;
    }

    public void setTitleCounts(int titlePostion) {
        this.titleCounts = titlePostion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getFnm() {
        return fnm;
    }

    public void setFnm(String fnm) {
        this.fnm = fnm;
    }

    public int getOh() {
        return oh;
    }

    public void setOh(int oh) {
        this.oh = oh;
    }

    public int getOw() {
        return ow;
    }

    public void setOw(int ow) {
        this.ow = ow;
    }

    public int getTh() {
        return th;
    }

    public void setTh(int th) {
        this.th = th;
    }

    public int getTw() {
        return tw;
    }

    public void setTw(int tw) {
        this.tw = tw;
    }



    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "BaseSharePictureTypeBean{" +
                "type=" + type +
                ", fnm='" + fnm + '\'' +
                ", oh=" + oh +
                ", ow=" + ow +
                ", th=" + th +
                ", tw=" + tw +
                ", url='" + url + '\'' +
                ", counts=" + counts +
                '}';
    }

    @Override
    public int getItemType() {
        return type;
    }
}
