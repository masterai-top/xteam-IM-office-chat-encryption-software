package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class XListTaskResultBean extends BaseListSocketBean<XListTaskBean> {

    private int tid ; //团队id 
    private int uid ; //用户id 
    private int type ; //请求类型： 1 添加 2 删除 3 修改 
    private int tkid ; //任务Id (请求类型为1添加会返回一个任务id) 

    public XListTaskResultBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq,"tkinfo",XListTaskBean.class);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
        super.parse(receiveProtocol,preseq);
         tid = getInt("tid");
         uid = getInt("uid");
         type = getInt("type");
         tkid = getInt("tkid");

    }

    public int getTid() {return tid;}
    public int getUid() {return uid;}
    public int getType() {return type;}
    public int getTkid() {return tkid;}
}
