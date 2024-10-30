package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class SettingInfoBean extends BaseListSocketBean<SettingInfoArrBean> {

    private int tid ; //团队id
    private int tntc ; //团队通知设置 0关闭 1推送所有通知 2仅推送@我的通知
    private List<SettingInfoArrBean> sscinfo;

    public SettingInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq, String key) {
        super(receiveProtocol,preseq,key,SettingInfoArrBean.class);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super.parse(receiveProtocol,preseq);
        sscinfo=new ArrayList<>();
         tid = getInt("tid"); 
         tntc = getInt("tntc");
         List<HashMap<String, Object>> list = getList(mKey);
        for (HashMap<String, Object> hashMap :list) {
            SettingInfoArrBean settingInfoArrBean = new SettingInfoArrBean(hashMap, preseq);
            sscinfo.add(settingInfoArrBean);
        }
    }

    public int getTid() {return tid;}
    public int getTntc() {return tntc;}

    public List<SettingInfoArrBean> getSscinfo() {
        return sscinfo;
    }

    public void setSscinfo(List<SettingInfoArrBean> sscinfo) {
        this.sscinfo = sscinfo;
    }

    @Override
    public String toString() {
        return "SettingInfoBean{" +
                "tid=" + tid +
                ", tntc=" + tntc +
                ", sscinfo=" + sscinfo +
                '}';
    }
}
