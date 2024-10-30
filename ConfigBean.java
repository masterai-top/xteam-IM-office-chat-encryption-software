package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述： 配置信息
 */
public class ConfigBean extends BaseListSocketBean<UserConfigBean> {

    private int tid; //团队id
    private int tntc; //团队通知设置 0关闭 1推送所有通知 2仅推送@我的通知


    public ConfigBean(HashMap<String, Object> bodyMap, ReceiveProtocol receiveProtocol){
        super(bodyMap, receiveProtocol, "sscinfo", UserConfigBean.class);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super.parse(receiveProtocol, preseq);
        tid = getInt("tid");
        tntc = getInt("tntc");

        List<UserConfigBean> list = getList();
        for (UserConfigBean userConfigBean : getList()) {
            userConfigBean.tid = this.tid;
        }
    }

    public int getTid() {
        return tid;
    }

    public int getTntc() {
        return tntc;
    }
}
