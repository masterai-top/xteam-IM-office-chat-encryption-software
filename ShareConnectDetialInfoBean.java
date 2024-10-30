package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class ShareConnectDetialInfoBean extends BaseSocketBean {

    private String tit ; //显示的标题（暂为发送者姓名） 
    private String pic ; //显示的图片（暂为发送者头像） 
    private long st ; //原消息发送的时间 时间戳8位 精确到毫秒
    private String msg ; //消息内容(链接) 

    public ShareConnectDetialInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         tit = getString("tit"); 
         pic = getString("pic"); 
         st = getInt("st");
         msg = getString("msg"); 

    }

    public String getTit() {return tit;}
    public String getPic() {return pic;}
    public long getSt() {return st;}
    public String getMsg() {return msg;}

    @Override
    public String toString() {
        return "ShareConnectDetialInfoBean{" +
                "tit='" + tit + '\'' +
                ", pic='" + pic + '\'' +
                ", st=" + st +
                ", msg='" + msg + '\'' +
                '}';
    }
}
