package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class DownloadFileSTBean extends BaseSocketBean {

    private int uid ; //用户ID 
    private String url ; //团队ID 
    private int tid ; //团队ID 
    private String expires ; //10位时间戳 
    private String code ; //url中code的值 

    public DownloadFileSTBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    public int getUid() {return uid;}
    public String getUrl() {return url;}
    public int getTid() {return tid;}
    public String getExpires() {return expires;}
    public String getCode() {return code;}

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        url = getString("url");
        tid = getInt("tid");
        expires = getString("expires");
        code = getString("code");
    }
}
