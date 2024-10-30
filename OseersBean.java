package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class OseersBean extends BaseSocketBean {

    private int tid ; //团队id 
    private int uid ; //用户id 
    private int tkuid ; //被获取任务参与者的用户id 
    private List<Integer> oseers ; //参与者id

    public OseersBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         tid = getInt("tid"); 
         uid = getInt("uid"); 
         tkuid = getInt("tkuid");
        Object o =  receiveProtocol.get("oseers");
        try {
            if (o != null && o instanceof List) {
                oseers = (List<Integer>) o;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getTid() {return tid;}
    public int getUid() {return uid;}
    public int getTkuid() {return tkuid;}
    public List<Integer> getOseers() {return oseers;}

}
