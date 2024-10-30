package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/8/31
 * 描    述：
 */
public class SubpackageBean extends  BaseSocketBean {

    int cmd;
    int npack;
    int curPackSum;


    public SubpackageBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        cmd = getInt("cmd");
        npack = getInt("npack");
    }

    public int getCmd() {
        return cmd;
    }

    public int getNpack() {
        return npack;
    }

    public void setCurPackSum(int curPackSum) {
        this.curPackSum = curPackSum;
    }

    public int getCurPackSum() {
        return curPackSum;
    }
}
