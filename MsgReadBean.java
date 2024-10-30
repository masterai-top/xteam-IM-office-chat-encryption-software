package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class MsgReadBean extends BaseSocketBean {

    private int tid ; //团队id 
    private int toid ; //聊天会话对象id，私聊为用户id，群聊为群id (群暂时不管) 
    private int tp ; //会话类型0私聊1群聊 
    private int mid ; //已读的最新的消息id
    private int uid; //点击已读的人 用来判断是不是自己点的

    public MsgReadBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        toid = getInt("toid");
        tp = getInt("tp");
        mid = getInt("mid");
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTid() {return tid;}
    public int getToid() {return toid;}
    public int getTp() {return tp;}
    public int getMid() {return mid;}

    @Override
    public String toString() {
        return "MsgReadBean{" +
                "tid=" + tid +
                ", toid=" + toid +
                ", tp=" + tp +
                ", mid=" + mid +
                '}';
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }
}
