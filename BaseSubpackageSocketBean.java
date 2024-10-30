package com.xmen.xteam.communication.bean;


import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public abstract class BaseSubpackageSocketBean extends BaseSocketBean {

    public BaseSubpackageSocketBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol seqno) {
        super(receiveProtocol, seqno);
    }

    //合包  把第二个包融合到第一个包里面
    public abstract void fusePage(BaseSubpackageSocketBean minor);

    protected BaseSubpackageSocketBean() {
    }

}
