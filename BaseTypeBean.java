package com.xmen.xteam.communication.bean;


import java.io.Serializable;

public class BaseTypeBean implements Serializable {
    public String url;

    public int position;

    public int type;

    public int msgId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "BaseTypeBean{" +
                "url='" + url + '\'' +
                ", position=" + position +
                ", type=" + type +
                '}';
    }
}
