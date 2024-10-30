package com.xmen.xteam.communication.bean;


import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class SharePictureDetialInfoBean extends BaseSocketBean {

    private long st ; //原消息发送的时间 时间戳8位 精确到毫秒 
    private String param ; //消息参数属性json串（图片），具体看附录1 

    public SharePictureDetialInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         st = getInt("st");
         param = getString("param");

    }

    public long getSt() {return st;}
    public String getParam() {return param;}

    @Override
    public String toString() {
        return "SharePictureDetialInfoBean{" +
                "st=" + st +
                ", param='" + param + '\'' +
                '}';
    }
}
