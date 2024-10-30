package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/11
 * 描    述：
 */
public class GetAllTeamInfosBean extends BaseSocketBean  {

    private List<TeamInfoBeanDB> tinfo ;//消息服务器IP信息数组列表

    public GetAllTeamInfosBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        tinfo= new ArrayList<>();

        List<HashMap<String,Object>> o = getList("tinfo");
        for (HashMap<String, Object> hashMap : o) {
            TeamInfoBeanDB userInfoBean = new TeamInfoBeanDB(hashMap, preseq);
            tinfo.add(userInfoBean);
        }
    }

    public List<TeamInfoBeanDB> getTinfo() {
        return tinfo;
    }
}
