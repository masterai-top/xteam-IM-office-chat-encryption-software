package com.xmen.xteam.communication.bean;

import android.text.TextUtils;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.utils.HanziToPinyin;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class GroupInfoBean extends BaseSocketBean {

    private int gid; //群组id
    private String gname; //群组名称
    private String avatar; //群组头像url
    private int role; //用户角色0-普通用户 1-管理员 2-创建者 3-机器人
    private int gtp; //0-公告版1-普通群
    private int stat; //是否删除,0-正常，1-删除
    private int ctm; //创建时间
    private String whole;//全拼
    public String simple;//简拼
    public String initial;//简拼


    public GroupInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    public GroupInfoBean(int gid, String gname, String avatar, int role, int gtp, int stat, int ctm, String whole, String simple, String initial) {
        this.gid = gid;
        this.gname = gname;
        this.avatar = avatar;
        this.role = role;
        this.gtp = gtp;
        this.stat = stat;
        this.ctm = ctm;
        this.whole = whole;
        this.simple = simple;
        this.initial = initial;
    }

    public GroupInfoBean(int gid, String gname, String avatar) {
        this.gid = gid;
        this.gname = gname;
        this.avatar = avatar;
        if (TextUtils.isEmpty(gname)) {
            return;
        }
        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(gname);
        whole = pinyin.whole;
        simple = pinyin.simple;
        initial = pinyin.initial;
    }

    public GroupInfoBean(int gid, String gname, String avatar, int role, int gtp, int stat, int ctm) {
        this.gid = gid;
        this.gname = gname;
        this.avatar = avatar;
        this.role = role;
        this.gtp = gtp;
        this.stat = stat;
        this.ctm = ctm;
        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(gname);
        whole = pinyin.whole;
        simple = pinyin.simple;
        initial = pinyin.initial;
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        gid = getInt("gid");
        gname = getString("gnm");
        avatar = getString("avt");
        role = getInt("role");
        gtp = getInt("gtp");
        stat = getInt("stat");
        ctm = getInt("ctm");

        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(gname);
        whole = pinyin.whole;
        simple = pinyin.simple;
        initial = pinyin.initial;
    }


    public String getWhole() {
        return whole;
    }

    public String getSimple() {
        return simple;
    }

    public int getGid() {
        return gid;
    }

    public String getGname() {
        return gname;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getRole() {
        return role;
    }

    public int getGtp() {
        return gtp;
    }

    public int getStat() {
        return stat;
    }

    public int getCtm() {
        return ctm;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setGtp(int gtp) {
        this.gtp = gtp;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setCtm(int ctm) {
        this.ctm = ctm;
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

    public String getInitial() {
        return initial;
    }

    @Override
    public String toString() {
        return "GroupInfoBean{" +
                "gid=" + gid +
                ", gname='" + gname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", gtp=" + gtp +
                ", stat=" + stat +
                ", ctm=" + ctm +
                ", whole='" + whole + '\'' +
                ", simple='" + simple + '\'' +
                ", initial='" + initial + '\'' +
                '}';
    }
}
