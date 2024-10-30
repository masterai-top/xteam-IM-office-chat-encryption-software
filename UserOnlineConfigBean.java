package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class UserOnlineConfigBean extends BaseSocketBean {

    private int uid; //用户ID
    private int lorb ; //离开/忙碌配置 0 离开 1 忙碌 type为1时为必填
    private int time = 3; //客户端无动作时间配置（分钟） 默认为3分钟

    public UserOnlineConfigBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        lorb = getInt("lorb");
        time = getInt("time");

    }

    public UserOnlineConfigBean(int uid, int lorb, int time) {
        this.uid = uid;
        this.lorb = lorb;
        this.time = time;
    }

    public int getUid() {
        return uid;
    }


    public int getLorb() {
        return lorb;
    }

    public int getTime() {
        return time;
    }

}
