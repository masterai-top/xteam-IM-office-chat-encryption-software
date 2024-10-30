package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class CallRequestBeanEvent extends BaseSocketBean {

    private int tid ; //团队ID 
    private int uid ; //用户id 
    private int toid ; //对方用户id 
    private int type ; //请求类型: 1 发起 2 回应 
    private int avtype ; //音视频类型： 1 音视频 2 音频
    private int opt ; //操作类型：1 请求通话 2 取消通话 3 同意通话 4 拒绝通话 5 关闭通话(请求类型为1(发起)时，opt为1(请求通话)、2(取消通话)、5(关闭通话)，请求类型为2(回应)时，opt可以为3(同意通话)、4(拒绝通话))
    private int pf ; //

    public CallRequestBeanEvent(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         tid = getInt("tid"); 
         uid = getInt("uid"); 
         toid = getInt("toid"); 
         type = getInt("type"); 
         opt = getInt("opt");
        avtype = getInt("avtype");
        pf = getInt("pf");

    }

    public int getTid() {return tid;}
    public int getUid() {return uid;}
    public int getToid() {return toid;}
    public int getType() {return type;}
    public int getOpt() {return opt;}

    public int getAvtype() {
        return avtype;
    }

    public int getPf() {
        return pf;
    }
}
