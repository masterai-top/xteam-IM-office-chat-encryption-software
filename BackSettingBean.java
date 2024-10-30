package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class BackSettingBean extends BaseSocketBean {

    private int tid ; //团队id 
    private int tntc ; //团队通知设置 0关闭 1推送所有通知 2仅推送@我的通知 

    public BackSettingBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         tid = getInt("tid"); 
         tntc = getInt("tntc"); 

    }

    public int getTid() {return tid;}
    public int getTntc() {return tntc;}

}
