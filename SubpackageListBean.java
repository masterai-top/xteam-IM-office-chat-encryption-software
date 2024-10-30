package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/8/31
 * 描    述：
 */
public class SubpackageListBean extends BaseListSocketBean<SubpackageBean> {
    public SubpackageListBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq, "minfo", SubpackageBean.class);
    }
}
