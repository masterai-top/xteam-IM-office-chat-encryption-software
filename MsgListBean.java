package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/22
 * 描    述：
 */
public class MsgListBean extends BaseListSocketBean<MsgBean> {
    int tid;

    public MsgListBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq, "minfo", MsgBean.class);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super.parse(receiveProtocol, preseq);
        tid = getInt("tid");
        List<MsgBean> list = getList();
        for (MsgBean msgBean : list) {
            msgBean.setTid(tid);
        }
    }

    public int getTid() {
        return tid;
    }


}
