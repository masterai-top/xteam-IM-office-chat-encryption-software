package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.wcdb.bean.SettingDB;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class SettingInfoArrBean extends BaseSocketBean {

    private int toid; //聊天对象id，私聊为私聊对象id，群聊为群id
    private int stp; //会话类型 0私聊 1群聊
    private int stick; //置顶设置，0：不置顶， 置顶时间戳：置顶
    private int sntc; //会话通知设置 0关闭 1推送所有通知
    private int stat; // 消息列表是否隐藏  0是显示 2是隐藏

    public SettingInfoArrBean(int toid, int stp, int stick, int sntc, int stat) {
        this.toid = toid;
        this.stp = stp;
        this.stick = stick;
        this.sntc = sntc;
        this.stat = stat;
    }

    public SettingInfoArrBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        toid = getInt("toid");
        stp = getInt("stp");
        stick = getInt("stick");
        sntc = getInt("sntc");
        stat = getInt("stat");
    }

    public int getToid() {
        return toid;
    }

    public int getStp() {
        return stp;
    }

    public int getStick() {
        return stick;
    }

    public int getSntc() {
        return sntc;
    }

    public SettingDB toSettingDB() {
        return new SettingDB(toid, stp, stick, sntc,stat);
    }


    public void setStick(int stick) {
        this.stick = stick;
    }

    public void setSntc(int sntc) {
        this.sntc = sntc;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "SettingInfoArrBean{" +
                "toid=" + toid +
                ", stp=" + stp +
                ", stick=" + stick +
                ", sntc=" + sntc +
                ", stat=" + stat +
                '}';
    }
}
