package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.utils.TimeUtils;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class MultiCallUserBean extends BaseSocketBean {

    private int uid; //被邀请用户ID
    private int stat; //通话状态：1 邀请中 2 连接中 3 通话中 4 静音 5 未接听 6 已拒绝 7 已退出
    private int inver; //最后邀请人id
    private int invtm; //最后邀请时间戳
    private int vstat; //屏幕共享状态：0 关闭 1 打开

    public MultiCallUserBean(int uid) {
        this.uid = uid;
        stat = 2;
        this.invtm = (int) (TimeUtils.getServiceTime() / 1000);
    }

    public MultiCallUserBean(int uid, int inver) {
        this.uid = uid;
        this.inver = inver;
        this.invtm = (int) (TimeUtils.getServiceTime() / 1000);
    }

    public MultiCallUserBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol seqno) {
        super(receiveProtocol, seqno);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        stat = getInt("stat");
        inver = getInt("inver");
        invtm = getInt("invtm");
        vstat = getInt("vstat");
    }

    public int getUid() {
        return uid;
    }

    public int getStat() {
        return stat;
    }

    public int getInver() {
        return inver;
    }

    public int getInvtm() {
        return invtm;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setInver(int inver) {
        this.inver = inver;
    }

    public void setInvtm(int invtm) {
        this.invtm = invtm;
    }

    public int getVstat() {
        return vstat;
    }


    @Override
    public boolean equals(Object obj) {

        return this.uid == ((MultiCallUserBean) obj).uid;
    }
}
