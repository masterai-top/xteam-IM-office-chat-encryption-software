package com.xmen.xteam.communication.bean;


import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class SPushMamberUpdate extends BaseSocketBean {

    private int uid ; //用户id 
    private int role ; //用户角色0-普通用户1-管理员2-创建者3-机器人 
    private int stat ; //是否退出，0-正常，1-已退出 
    private int ctm ; //加入时间 

    public SPushMamberUpdate(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        role = getInt("role");
        stat = getInt("stat");
        ctm = getInt("ctm");
    }


    public int getUid() {return uid;}
    public int getRole() {return role;}
    public int getStat() {return stat;}
    public int getCtm() {return ctm;}

}
