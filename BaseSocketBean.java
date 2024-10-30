package com.xmen.xteam.communication.bean;


import android.widget.ImageView;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public abstract class BaseSocketBean implements Serializable {

    protected HashMap<String, Object> mReceiveProtocol;
    protected int mSeqno;
    protected int cmd;
    protected long servicerTime;
    protected int res = -1;
    protected String mErr = "";
    protected int op = -1;

    protected BaseSocketBean() {
    }

    public BaseSocketBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol seqno) {
        if (receiveProtocol != null) {
            mSeqno = seqno.getSeqno();
            servicerTime = seqno.getTimestamp();
            cmd = seqno.getCmd();
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

                parse(receiveProtocol, seqno);
            mReceiveProtocol = null;
        }
    }

    public String getString(String key) {
        if (mReceiveProtocol == null) {
            return "";
        }
        Object o = mReceiveProtocol.get(key);
        if (o != null && o instanceof String) {
            return (String) mReceiveProtocol.get(key);
        }
        return "";
    }

    public int getInt(String key) {
        if (mReceiveProtocol == null) {
            return 0;
        }
        Object o = mReceiveProtocol.get(key);
        if (o != null && o instanceof Integer) {
            return (int) mReceiveProtocol.get(key);
        }
        return 0;
    }

    public long getLong(String key) {
        if (mReceiveProtocol == null) {
            return 0;
        }
        Object o = mReceiveProtocol.get(key);
        if (o != null && o instanceof  Long) {
            return (long) mReceiveProtocol.get(key);
        }
        return 0;
    }

    public List<HashMap<String, Object>> getList(String key) {
        if (mReceiveProtocol == null) {
            return new ArrayList<>();
        }
        Object o = mReceiveProtocol.get(key);
        try {
            if (o != null && o instanceof List) {
                return (List<HashMap<String, Object>>) mReceiveProtocol.get(key);
            }
        }catch (Exception e){

        }

        return new ArrayList<>();
    }


    protected abstract void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq);

    public int getSeqno() {
        return mSeqno;
    }

    public int getRes() {
        return res;
    }

    public String getErr() {
        return mErr;
    }

    public int getOp() {
        return op;
    }


    public long getServicerTime() {
        return servicerTime;
    }

    @Override
    public String toString() {
        return "BaseSocketBean{" +
                "mReceiveProtocol=" + mReceiveProtocol +
                ", mSeqno=" + mSeqno +
                ", res=" + res +
                ", mErr='" + mErr + '\'' +
                ", op=" + op +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

}
