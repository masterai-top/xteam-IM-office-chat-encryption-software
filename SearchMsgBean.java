package com.xmen.xteam.communication.bean;

import android.text.SpannableString;

/**
 * Created by zhangqi on 2018/6/21.
 * 搜索聊天记录的消息bean；
 */

public class SearchMsgBean {
    private int toid;
    private int sid;
    private int msgid;
    private int type;
    private int isread;
    private int ord;
    private long time;
    private int state;
    private SpannableString content;
    private String toidName;//toid的昵称；
    private String param;
    private int msgtype;//标识聊天记录中是私聊还是群聊消息，0是私聊，1是群聊；
    private String searchKey;

    public SearchMsgBean(int toid, int sid, int msgid, int type, int isread, int ord, long time,
                         int state, SpannableString content, String param,int msgtype,String searchKey) {
        this.toid = toid;
        this.sid = sid;
        this.msgid = msgid;
        this.type = type;
        this.isread = isread;
        this.ord = ord;
        this.time = time;
        this.state = state;
        this.content = content;
        this.param = param;
        this.msgtype=msgtype;
        this.searchKey=searchKey;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getToidName() {
        return toidName;
    }

    public void setToidName(String toidName) {
        this.toidName = toidName;
    }

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public SpannableString getContent() {
        return content;
    }

    public void setContent(SpannableString content) {
        this.content = content;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "SearchMsgBean{" +
                "toid=" + toid +
                ", sid=" + sid +
                ", msgid=" + msgid +
                ", type=" + type +
                ", isread=" + isread +
                ", ord=" + ord +
                ", time=" + time +
                ", state=" + state +
                ", content='" + content + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
