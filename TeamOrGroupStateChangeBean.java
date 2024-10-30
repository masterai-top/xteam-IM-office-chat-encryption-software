package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class TeamOrGroupStateChangeBean extends BaseSocketBean {

    private int tid ; //团队ID 
    private int gid ; //群ID，从团队中删除时为0，否则为群ID 
    private int uid ; //用户ID 
    private int status ; //在群/团队状态 0:正常 1：已退群/已退团 

    public TeamOrGroupStateChangeBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         tid = getInt("tid"); 
         gid = getInt("gid"); 
         uid = getInt("uid"); 
         status = getInt("status"); 

    }

    public int getTid() {return tid;}
    public int getGid() {return gid;}
    public int getUid() {return uid;}
    public int getStatus() {return status;}

}
