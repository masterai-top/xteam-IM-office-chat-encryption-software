package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class MultiCallVolumeBean extends BaseSocketBean {

    private int uid ; //用户id
    private int cid ; //会议id
    private int sgnl ; //信号 1 语音

    public MultiCallVolumeBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
        uid = getInt("uid");
        cid = getInt("cid");
        sgnl = getInt("sgnl");

    }

    public MultiCallVolumeBean(int uid, int cid, int sgnl) {
        this.uid = uid;
        this.cid = cid;
        this.sgnl = sgnl;
    }

    public int getUid() {return uid;}
    public int getCid() {return cid;}
    public int getSgnl() {return sgnl;}

}
