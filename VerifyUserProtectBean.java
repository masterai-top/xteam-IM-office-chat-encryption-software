package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class VerifyUserProtectBean extends BaseSocketBean {


    private int ptype ; //0:常用设备不走账号保护，1:需要验证码，2:需要验证码+谷歌验证 
    private int gtype ; //0:常用设备不走账号保护，1:需要验证码，2:需要验证码+谷歌验证

    public VerifyUserProtectBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         ptype = getInt("ptype");
        gtype = getInt("gtype");
    }

    public int getPtype() {return ptype;}

    public int getGtype() {
        return gtype;
    }
}
