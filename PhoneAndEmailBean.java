package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class PhoneAndEmailBean extends BaseSocketBean {

    private int uid ; //用户id 
    private int dm ; //区号 86 
    private String pho ; //手机号 
    private String eml ; //email

    public PhoneAndEmailBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol seqno) {
        super(receiveProtocol, seqno);
    }

    public int getUid() {return uid;}
    public int getDm() {return dm;}
    public String getPho() {return pho;}
    public String getEml() {return eml;}

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        dm = getInt("dm");
        pho = getString("pho");
        eml = getString("eml");
    }
}
