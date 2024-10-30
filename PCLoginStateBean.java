package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class PCLoginStateBean extends BaseSocketBean {

    private int uid ; //用户ID 
    private int stat ; //登陆状态 1:登陆 2:断线
    private int type ; //登陆状态 1:登陆 2:断线

    public PCLoginStateBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         uid = getInt("uid"); 
         stat = getInt("stat");
        type = getInt("type");

    }

    public int getUid() {return uid;}
    public int getStat() {return stat;}

    public int getType() {
        return type;
    }
}
