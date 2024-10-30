package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class UpdateAppBean extends BaseSocketBean {

    private int typ ; //值同142协议发送值 
    private int pf ; //值同142协议发送至 
    private String ver ; //当前最新版本 
    private String mrk ; //当前更新介绍 
    private String url ; //APK包下载地址 

    public UpdateAppBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         typ = getInt("typ"); 
         pf = getInt("pf"); 
         ver = getString("ver"); 
         mrk = getString("mrk"); 
         url = getString("url");
    }

    public int getTyp() {return typ;}
    public int getPf() {return pf;}
    public String getVer() {return ver;}
    public String getMrk() {return mrk;}
    public String getUrl() {return url;}

    @Override
    public String toString() {
        return "UpdateAppBean{" +
                "typ=" + typ +
                ", pf=" + pf +
                ", ver='" + ver + '\'' +
                ", mrk='" + mrk + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
