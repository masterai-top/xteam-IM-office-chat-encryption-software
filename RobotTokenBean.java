package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class RobotTokenBean extends BaseSocketBean {

    private String token ; //团队id
    private int chatId ; //团队id

    public RobotTokenBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        token = getString("token");
        chatId = getInt("gid");

    }

    public String getToken() {
        return token;
    }

    public int getChatId() {
        return chatId;
    }

    @Override
    public String toString() {
        return "RobotTokenBean{" +
                "token='" + token + '\'' +
                ", chatId=" + chatId +
                '}';
    }
}
