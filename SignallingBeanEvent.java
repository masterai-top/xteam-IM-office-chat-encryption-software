package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class SignallingBeanEvent extends BaseSocketBean {

    private String event ; //信令事件 
    private String data ; //信令信息 

    public SignallingBeanEvent(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         event = getString("event"); 
         data = getString("data"); 

    }

    public String getEvent() {return event;}
    public String getData() {return data;}

}
