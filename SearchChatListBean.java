package com.xmen.xteam.communication.bean;

import com.xmen.xteam.wcdb.bean.MsgListDB;

import java.util.List;


public class SearchChatListBean {
    private  List<MsgListDB> data;

    public List<MsgListDB> getData() {
        return data;
    }

    public void setData(List<MsgListDB> data) {
        this.data = data;
    }
}
