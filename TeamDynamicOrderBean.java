package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class TeamDynamicOrderBean extends BaseSocketBean {

    private int tid ; //团队ID 
    private int type ; //请求类型：1：设置 2：获取动态口令 3：刷新动态口令 
    private int chgtm ; //设置动态口令改变时间（n天后改变） 
    private int viwtyp ; //可见类型：1：创建者、管理员 2：全团队 
    private int dynpw ; //动态口令 
    private int crttm ; //动态口令生成时间戳 

    public TeamDynamicOrderBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         tid = getInt("tid"); 
         type = getInt("type"); 
         chgtm = getInt("chgtm"); 
         viwtyp = getInt("viwtyp"); 
         dynpw = getInt("dynpw"); 
         crttm = getInt("crttm"); 
    }

    public int getTid() {return tid;}
    public int getType() {return type;}
    public int getChgtm() {return chgtm;}
    public int getViwtyp() {return viwtyp;}
    public int getDynpw() {return dynpw;}
    public int getCrttm() {return crttm;}

}
