package com.xmen.xteam.communication.bean;


import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class BaseListSubpackageSocketBean<T extends BaseSocketBean> extends BaseSubpackageSocketBean {


    protected final String mKey;
    private final Class mClazz;
    private int tid;
    private List tList;//消息服务器IP信息数组列表

    public BaseListSubpackageSocketBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq, String key, Class clazz) {
        mKey = key;
        mClazz = clazz;

        if (receiveProtocol != null) {
            mSeqno = preseq.getSeqno();
            servicerTime = preseq.getTimestamp();
            cmd = preseq.getCmd();
            Object o = receiveProtocol.get("res");
            if (o != null) {
                res = (int) o;
            }
            if (res != 1) {
                mErr = (String) receiveProtocol.get("err");
            }
            Object op = receiveProtocol.get("op");
            if (op != null) {
                this.op = (int) op;
            }
            mReceiveProtocol = receiveProtocol;

            parse(receiveProtocol, preseq);

            mReceiveProtocol = null;
        }
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        tList = new ArrayList<>();

        Object tidObject = receiveProtocol.get("tid");
        if (tidObject != null) {
            tid = (int) tidObject;
        }

        List<HashMap<String, Object>> o = getList(mKey);
        for (HashMap<String, Object> hashMap : o) {
            try {
                Constructor con = mClazz.getConstructor(HashMap.class, ReceiveProtocol.class);
                Object t = con.newInstance(hashMap, preseq);
                tList.add(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> getList() {
        return tList;
    }

    public void setList(List tList) {
        this.tList = tList;
    }

    public int getTid() {
        return tid;
    }

    @Override
    public void fusePage(BaseSubpackageSocketBean minor) {
        getList().addAll(((BaseListSubpackageSocketBean) minor).getList());
    }
}
