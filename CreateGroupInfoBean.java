package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class CreateGroupInfoBean extends BaseSocketBean {

    private int res ; //1：成功 其他：失败 
    private int tid ; //团队id 
    private int gid ; //群id 
    private String nm ; //群名称 
    private String avt ; //群头像Url

    public CreateGroupInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        res = getInt("res");
        tid = getInt("tid");
        gid = getInt("gid");
        nm = getString("nm");
        avt = getString("avt");
    }

    public int getRes() {return res;}
    public int getTid() {return tid;}
    public int getGid() {return gid;}
    public String getNm() {return nm;}
    public String getAvt() {return avt;}
}
