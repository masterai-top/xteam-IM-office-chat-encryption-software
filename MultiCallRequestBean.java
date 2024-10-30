package com.xmen.xteam.communication.bean;

import com.xmen.xteam.app.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class MultiCallRequestBean {

    private int tid; //团队ID
    private int uid; //用户id
    private int type; //请求类型: 1 发起 2 回应 3系统通知
    private int opt; //操作类型：1 会议邀请 2 会议关闭 3 退出会议 4 会议中邀请用户 5 会议中移除用户 6 会议类型更改 7 管理会议中用户 8 同意邀请 9 拒绝邀请 10 会议用户通话状态变更 11 与IM网络断开连接 12 与IM网络恢复连接 13 与FS网络断开连接(预留) 14 与FS网络恢复连接(预留)——————————————————————(请求类型为1(发起)时，opt为1(会议邀请)、2(会议关闭)、3(退出会议)、4(会议中邀请用户)、5(会议中移除用户)、6(会议类型更改)7(管理会议中用户)，请求类型为2(回应)时，opt可以为8(同意邀请)、9(拒绝邀请)，请求类型为3(系统通知)时，opt可以为10(会议用户通话状态变更)、11(与IM网络断开连接)、12(与IM网络恢复连接)、13(与FS网络断开连接、14(FS网络恢复连接)
    private int avtype = 3; //音视频类型： 3 音频 4 音频+屏幕共享
    private int cid; //会议id
    private int ctype; //控制类型：1 单个用户静音 2 解除单个用户静音 3 全员静音 4 解除全员静音
    private List<Integer> invuid; //被邀请用户id，可多个用户


    public MultiCallRequestBean newCreatingMeeting(ArrayList<MultiCallUserBean> attendee, int myUid, int tid) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean();
        //会议邀请
        callRequestBean.tid = tid;
        callRequestBean.uid = myUid;
        callRequestBean.type = 1;
        callRequestBean.opt = 1;
        callRequestBean.invuid = transition(attendee);
        callRequestBean.cid = 0;
        callRequestBean.ctype = 0;
        return callRequestBean;
    }

    public MultiCallRequestBean newAnewCall(int tid, int cid, int uid, int avType) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 4,avType);
        callRequestBean.invuid = new ArrayList<>();
        callRequestBean.invuid.add(uid);
        return callRequestBean;
    }

    public MultiCallRequestBean newCloseMeeting(int tid, int cid, int avtype) {
        //关闭会议
        return new MultiCallRequestBean(tid, cid, 2,avtype);
    }

    public MultiCallRequestBean newAllMute(int tid, int cid, boolean isAllMute, int avType) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 7,avType);
        callRequestBean.ctype = isAllMute ? 3 : 4;

        return callRequestBean;
    }

    public MultiCallRequestBean newRemoveMember(int tid, int cid, int uid, int avType) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 5,avType);
        callRequestBean.invuid = new ArrayList<>();
        callRequestBean.invuid.add(uid);
        return callRequestBean;
    }


    public MultiCallRequestBean newMulti(int tid, int cid, int uid, boolean isAllMute, int avType) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 7,avType);
        callRequestBean.ctype = isAllMute ? 1 : 2;
        callRequestBean.invuid = new ArrayList<>();
        callRequestBean.invuid.add(uid);
        return callRequestBean;
    }

    public MultiCallRequestBean newQuitMeeting(int tid, int cid) {
        //退出会议
        return new MultiCallRequestBean(tid, cid, 3);
    }

    public MultiCallRequestBean newRejectMeeting(int tid, int cid, int avType) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 9,avType);
        callRequestBean.type=2;
        //9 拒绝会议
        return callRequestBean;
    }


    public MultiCallRequestBean newInviteMember(int tid, int cid, ArrayList<Integer> requestUid, int avtypeVoice) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 4,avtypeVoice);
        callRequestBean.invuid  = requestUid;
        return callRequestBean;
    }

    public List<Integer> transition(ArrayList<MultiCallUserBean> attendee) {
        List<Integer> uids = new ArrayList<>();
        for (MultiCallUserBean multiCallUserBean : attendee) {
            if (multiCallUserBean.getUid() == Common.getI().getCurrentUserID()) {
                continue;
            }
            uids.add(multiCallUserBean.getUid());
        }
        return uids;
    }

    public MultiCallRequestBean newAnswerMeeting(int tid, int cid, int avtype) {
        MultiCallRequestBean callRequestBean = new MultiCallRequestBean(tid, cid, 8,avtype);
        callRequestBean.type = 2;
        return callRequestBean;
    }


    public MultiCallRequestBean(int tid, int cid, int opt) {
        this.tid = tid;
        this.uid = Common.getI().getCurrentUserID();
        this.type = 1;
        this.opt = opt;
        this.invuid = new ArrayList<>();
        this.cid = cid;
        this.ctype = 0;
    }
    public MultiCallRequestBean(int tid, int cid, int opt,int avtype) {
        this.tid = tid;
        this.uid = Common.getI().getCurrentUserID();
        this.type = 1;
        this.opt = opt;
        this.invuid = new ArrayList<>();
        this.cid = cid;
        this.ctype = 0;
        this.avtype = avtype;
    }

    public MultiCallRequestBean() {
    }

    public int getTid() {
        return tid;
    }

    public int getUid() {
        return uid;
    }

    public int getType() {
        return type;
    }

    public int getOpt() {
        return opt;
    }

    public int getAvtype() {
        return avtype;
    }

    public int getCid() {
        return cid;
    }

    public int getCtype() {
        return ctype;
    }

    public List getInvuid() {
        return invuid;
    }


    @Override
    public String toString() {
        return "MultiCallRequestBean{" +
                "tid=" + tid +
                ", uid=" + uid +
                ", type=" + type +
                ", opt=" + opt +
                ", avtype=" + avtype +
                ", cid=" + cid +
                ", ctype=" + ctype +
                ", invuid=" + invuid +
                '}';
    }



}
