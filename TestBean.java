package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class TestBean extends BaseSocketBean {

    private int tid ; //团队id 
    private int toid ; //聊天对象id，私聊为私聊对象id，群聊为群id 
    private int stp ; //会话类型 0私聊 1群聊 
    private int stick ; //置顶设置，0：不置顶， 置顶时间戳：置顶 
    private int sntc ; //会话通知设置 0关闭 1推送所有通知 

    public TestBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         tid = getInt("tid"); 
         toid = getInt("toid"); 
         stp = getInt("stp"); 
         stick = getInt("stick"); 
         sntc = getInt("sntc"); 

    }

    public int getTid() {return tid;}
    public int getToid() {return toid;}
    public int getStp() {return stp;}
    public int getStick() {return stick;}
    public int getSntc() {return sntc;}


}
