package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class CreateTeamBean extends BaseSocketBean {

    private int tid; //团队id
    private String tname; //团队名称
    private String avatar; //团队头像Url
    private int role; //角色等级
    private int stat; //状态
    private int ctm; //创建时间


    public CreateTeamBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        tid=getInt("tid");
        tname=getString("tnm");
        avatar=getString("avt");
        role=getInt("role");
        stat=getInt("stat");
        ctm=getInt("ctm");
    }

    public int getTid() {
        return tid;
    }

    public String getTname() {
        return tname;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getRole() {
        return role;
    }

    public int getStat() {
        return stat;
    }

    public int getCtm() {
        return ctm;
    }

    @Override
    public String toString() {
        return "CreateTeamBean{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
