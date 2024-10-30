package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class UserProtectBean extends BaseSocketBean {

    private int uid ; //用户ID 
    private int aprt ; //设置账号保护 0关闭 1打开 
    private int res ; //1：成功 其他：失败 
    private String err ; //错误描述，对应res的错误码，如果res=1则为空 
    private List<HashMap<String, Object>> dinfo;//各个用户的设备信息列表，只给mode，和sysver

//    mode	string	手机型号(android,ios)
//    sysver	string	手机/电脑系统版本(android,ios,pc)

    public UserProtectBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }


    public int getUid() {return uid;}
    public int getAprt() {return aprt;}

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        aprt = getInt("aprt");
        res = getInt("res");
        err = getString("err");
        dinfo = getList("dinfo");

    }

    public int getRes() {return res;}
    public String getErr() {return err;}
    public List<HashMap<String, Object>>  getDinfo() {return dinfo;}

    public void setAprt(int aprt) {
        this.aprt = aprt;
    }
}
