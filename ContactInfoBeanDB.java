package com.xmen.xteam.communication.bean;

import android.text.SpannableString;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tencent.wcdb.Cursor;
import com.xmen.xteam.app.Common;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.utils.HanziToPinyin;
import com.xmen.xteam.wcdb.bean.IDBBean;
import com.xmen.xteam.wcdb.bean.TeamMemberDB;
import com.xmen.xteam.wcdb.bean.UserInfoDB;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class ContactInfoBeanDB extends BaseSocketBean implements Comparable<ContactInfoBeanDB>, MultiItemEntity, IDBBean, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    public static final int GROUPING = 101;
    public static final int ITEM = 102;
    int itemType = ITEM;

    private int stat; //是否退出，0-正常，1-已退出
    private int ctm; //	加入团队时间
    private int uid; //用户id
    private int role;//用户角色 0-普通用户 1-管理员 2-创建者 3-机器人

    private String uname; //用户名称
    private Object avatar; //用户头像url
    private String signature;//个性签名


    private boolean ismember;//是否是群成员;

    private String whole;//全拼
    private String simple;//简拼
    private String initial;//用户首字母
    private SpannableString ssName;//多彩用户名；

    public void setUname(String uname) {
        this.uname = uname;

        if (TextUtils.isEmpty(uname)) {
            return;
        }
        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(uname);
        whole = pinyin.whole;
        simple = pinyin.simple;
        initial = pinyin.initial;
    }

    public ContactInfoBeanDB copy(){
        ContactInfoBeanDB contactInfoBeanDB = new ContactInfoBeanDB();
        contactInfoBeanDB.itemType = itemType;
        contactInfoBeanDB.stat = stat;
        contactInfoBeanDB.ctm = ctm;
        contactInfoBeanDB.uid = uid;
        contactInfoBeanDB.role = role;
        contactInfoBeanDB.uname = uname;
        contactInfoBeanDB.avatar = avatar;
        contactInfoBeanDB.signature = signature;
        contactInfoBeanDB.ismember = ismember;
        contactInfoBeanDB.whole = whole;
        contactInfoBeanDB.simple = simple;
        contactInfoBeanDB.initial = initial;
        contactInfoBeanDB.ssName = ssName;
        return contactInfoBeanDB;
    }

    public ContactInfoBeanDB(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    public ContactInfoBeanDB(int grouping) {
        super(null, null);
        itemType = grouping;
    }

    public ContactInfoBeanDB(int grouping, String initial) {
        super(null, null);
        itemType = grouping;
        this.initial = initial;
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        uname = getString("unm");
        if (TextUtils.isEmpty(uname)) {
            uname = getString("nm");
        }
        avatar = getString("avt");
        role = getInt("role");
        stat = getInt("stat");
        ctm = getInt("ctm");

        if (TextUtils.isEmpty(uname)) {
            return;
        }
        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(uname);

        whole = pinyin.whole;
        simple = pinyin.simple;
        initial = pinyin.initial;
    }

    public ContactInfoBeanDB(int uid, String uname, String avatar) {
        super(null, null);
        this.uid = uid;
        this.uname = uname;
        this.avatar = avatar;
        if (TextUtils.isEmpty(uname)) {
            return;
        }
        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(uname);
        whole = pinyin.whole;
        simple = pinyin.simple;
        initial = pinyin.initial;
    }

    public UserInfoDB toUserInfo() {
        return new UserInfoDB(uid, uname, avatar.toString(), whole, simple);
    }

    public TeamMemberDB toTeamMemberDB() {
        return new TeamMemberDB(uid, role, stat, ctm);
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public Object getAvatar() {
        return avatar;
    }

    public String getWhole() {
        return whole;
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }

    public String getSimple() {
        return simple;
    }

    @Override
    public String toString() {
        return "ContactInfoBeanDB{" +
                "itemType=" + itemType +
                ", uid=" + uid +
                ", uname='" + uname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", whole='" + whole + '\'' +
                ", simple='" + simple + '\'' +
                ", initial='" + initial + '\'' +
                '}';
    }

    public int getItemType() {
        return itemType;
    }

    public String getInitial() {
        if (initial == null) {
            initial = "#";
        }
        return initial;
    }

//    @Override
//    public int compareTo(@NonNull ContactInfoBeanDB o2) {
//        int sort;
//        sort = this.initial.compareTo(o2.initial);
//        if (sort == 0) {
//            sort = this.itemType - o2.itemType;
//        }
//        return sort;
//    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    @Override
    public int compareTo(ContactInfoBeanDB r) {
        boolean flag;
        if (this.initial == null) {
            this.initial = "#";
        }
        if (r.initial == null) {
            r.initial = "#";
        }

        if ((flag = this.initial.startsWith("#")) ^ r.initial.startsWith("#")) {
            return flag ? 1 : -1;
        }
        if ((flag = this.initial.startsWith("管")) ^ r.initial.startsWith("管")) {
            return flag ? -1 : 1;
        }
        if ((flag = this.initial.startsWith("参")) ^ r.initial.startsWith("参")) {
            return flag ? -1 : 1;
        }
        if ((flag = this.initial.startsWith("执")) ^ r.initial.startsWith("执")) {
            return flag ? -1 : 1;
        }
        if ((flag = this.initial.startsWith("责")) ^ r.initial.startsWith("责")) {
            return flag ? -1 : 1;
        }
        if ((flag = this.initial.startsWith("已")) ^ r.initial.startsWith("已")) {
            return flag ? -1 : 1;
        }

        int sort;
        sort = this.initial.compareTo(r.initial);
        if (sort == 0) {
            sort = this.itemType - r.itemType;
        }

        if (sort == 0) {

            sort = r.role - this.role;
        }

        if (sort == 0) {
            if (whole != null && r.whole != null) {
                sort = this.whole.compareTo(r.whole);
            }
        }

        return sort;
    }

    public int getRole() {
        return role;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }


    public void setRole(int role) {
        this.role = role;
    }

    public int getStat() {
        return stat;
    }

    public int getCtm() {
        return ctm;
    }


    public static String getSqlQuery() {
        int currentTeamID = Common.getI().getCurrentTeamID();
        String SQL_CRATE = "SELECT * FROM team_member_%s LEFT OUTER JOIN user_info_%s  WHERE  team_member_%s.stat = 0  AND team_member_%s.uid = user_info_%s.uid;";
        return String.format(SQL_CRATE, currentTeamID,currentTeamID, currentTeamID, currentTeamID,currentTeamID);
    }

    public static String getSqlQuery(int tid, int uid) {
        String SQL_CRATE = "SELECT * FROM team_member_%s ,user_info_%s  where team_member_%s.uid = %s AND team_member_%s.uid = user_info_%s.uid;";

        return String.format(SQL_CRATE,tid, tid, tid, uid, tid,tid);
    }

    public static String deteleSqlQuery(int tid, int uid) {
        String SQL_DETELE = "DELETE FROM team_member_%s WHERE uid = %s;";
        return String.format(SQL_DETELE, tid, uid);
    }

    @Override
    public void parse(Cursor cursor) {
        uid = cursor.getInt(cursor.getColumnIndex("uid"));
        role = cursor.getInt(cursor.getColumnIndex("role"));
        stat = cursor.getInt(cursor.getColumnIndex("uname"));
        ctm = cursor.getInt(cursor.getColumnIndex("ctm"));
        uname = cursor.getString(cursor.getColumnIndex("uname"));
        avatar = cursor.getString(cursor.getColumnIndex("avatar"));
        whole = cursor.getString(cursor.getColumnIndex("whole"));
        simple = cursor.getString(cursor.getColumnIndex("simple"));

        String substring = "#";
        if (simple == null) {
            simple = "";
        }
        if (simple.length()>0) {
             substring = simple.substring(0, 1);
        }
        if (TextUtils.isEmpty(simple.trim())) {
            this.initial = "#";
        } else if ('A' <= substring.charAt(0) && 'Z' >= substring.charAt(0)) {
            this.initial = substring;
        } else {
            this.initial = "#";
        }

    }

    public ContactInfoBeanDB() {
    }

    @Override
    public Object[] getSqlValues() {
        return new Object[0];
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public boolean getIsmember() {
        return ismember;
    }

    public void setIsmember(boolean ismember) {
        this.ismember = ismember;
    }

    public SpannableString getSsName() {
        return ssName;
    }

    public void setSsName(SpannableString ssName) {
        this.ssName = ssName;
    }

    public ContactInfoBeanDB clone() {
        try {
            return (ContactInfoBeanDB) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
