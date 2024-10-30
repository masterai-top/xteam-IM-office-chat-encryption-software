package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class QrcodeRequestTidBean extends BaseSocketBean {

    private int tid ; //团队id 
    private String qrcode ; //返回二维码字段 
    private String fromId ; //返回二维码字段

    public QrcodeRequestTidBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         tid = getInt("tid"); 
         qrcode = getString("qrcode");
        fromId = getString("fromId");

    }

    public int getTid() {return tid;}
    public String getQrcode() {return qrcode;}

    public String getFromId() {
        return fromId;
    }
}
