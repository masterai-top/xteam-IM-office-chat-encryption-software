package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class JoinTeamBean extends BaseSocketBean {

    private int tid ; //团队id 
    private String tnm ; //团队名称 

    public JoinTeamBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         tid = getInt("tid"); 
         tnm = getString("tnm"); 

    }

    public int getTid() {return tid;}
    public String getTnm() {return tnm;}

}
