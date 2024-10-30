package com.xmen.xteam.communication.bean;

import android.support.annotation.NonNull;

import com.tencent.wcdb.Cursor;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.wcdb.bean.IDBBean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class TeamInfoBeanDB extends BaseSocketBean implements IDBBean, Serializable , Comparable<TeamInfoBeanDB> {
    private static final long serialVersionUID = 1L;
    protected int tid; //团队id
    protected String tname; //团队名称
    protected String des; //团队描述
    protected String avatar; //团队头像url
    protected int ucnt; //团队人数
    protected int op; //操作： 0新增  1更新(如果是更新，则下列用户信息项只有需要更新的才填充值) 2删除
    protected int mcnt; //团队未读消息条数
    protected int role; //角色登记
    protected int ctm; // 创建时间

    public TeamInfoBeanDB(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }
    public TeamInfoBeanDB() {
        super(null, null);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        tid = getInt("tid");
        tname = getString("tnm");
        des = getString("des");
        avatar = getString("avt");
        ucnt = getInt("ucnt");
        mcnt = getInt("mcnt");
        role = getInt("role");
        ctm = getInt("tm");
        op = getInt("op");
    }

    public TeamInfoBeanDB(int tid, String tname, String des, String avatar, int ucnt, int mcnt, int role,int ctm) {
        super(null, null);
        this.tid = tid;
        this.tname = tname;
        this.des = des;
        this.avatar = avatar;
        this.ucnt = ucnt;
        this.mcnt = mcnt;
        this.role = role;
        this.ctm = ctm;
    }




    public int getTid() {
        return tid;
    }

    public String getTname() {
        return tname;
    }

    public String getDes() {
        return des;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getUcnt() {
        return ucnt;
    }

    public int getMcnt() {
        return mcnt;
    }

    public int getCtm() {
        return ctm;
    }

    public int getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "TeamInfoBeanDB{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", des='" + des + '\'' +
                ", avatar='" + avatar + '\'' +
                ", ucnt=" + ucnt +
                ", mcnt=" + mcnt +
                '}';
    }

    @Override
    public void parse(Cursor cursor) {
        tid = cursor.getInt(cursor.getColumnIndex("tid"));
        tname = cursor.getString(cursor.getColumnIndex("tname"));
        des = cursor.getString(cursor.getColumnIndex("des"));
        avatar = cursor.getString(cursor.getColumnIndex("avatar"));
        ucnt = cursor.getInt(cursor.getColumnIndex("ucnt"));
        mcnt = cursor.getInt(cursor.getColumnIndex("mcnt"));
        ctm = cursor.getInt(cursor.getColumnIndex("ctm"));
    }

    public static String getSqlCrate() {
        String SQL_CRATE =
                "CREATE TABLE if not exists team_infos(" +
                        "   tid  INT4  PRIMARY KEY  NOT NULL," +
                        "   role  INT4 NOT NULL," +
                        "   tname VARCHAR(50) NOT NULL," +
                        "   avatar   VARCHAR(256) NOT NULL," +
                        "   des   varchar(1000)," +
                        "   ucnt  INT4 NOT NULL," +
                        "   ctm  INT4 NOT NULL," +
                        "   mcnt  INT4 NOT NULL" +
                        ");";
        return SQL_CRATE;
    }


    public static String getSqlDeleteTable() {
        String SQL_SELECT =
                "DELETE FROM team_infos ;";
        return SQL_SELECT;
    }

    public static String getSqlInsert() {
        String SQL_INSERT =
                "REPLACE INTO team_infos (tid,tname,avatar,des,ucnt,role,mcnt,ctm) VALUES (?,?,?,?,?,?,?,?)";
        return SQL_INSERT;
    }

    public static String getSqlUpdateTName(int tid, String tname) {
        String SQL_INSERT =
                "UPDATE team_infos SET tname = '%s' WHERE tid = %s;";
        return String.format(SQL_INSERT, tname, tid);
    }

    public static String getSqlQuery() {
        String SQL_SELECT =
                "SELECT * FROM  team_infos;";
        return SQL_SELECT;
    }

    public static String getSqlDelete(int tid) {
        String SQL_SELECT =
                "DELETE FROM team_infos WHERE tid = %s;";
        return String.format(SQL_SELECT, tid);
    }

    public static String getSqlQuery(int teamID) {
        String SQL_SELECT =
                "SELECT * FROM  team_infos  WHERE tid = %s";
        return String.format(SQL_SELECT, teamID);
    }

    public void setUcnt(int ucnt) {
        this.ucnt = ucnt;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public Object[] getSqlValues() {
        return new Object[]{tid, tname, avatar, des, ucnt, role,mcnt,ctm};
    }

    public void setTname(String tname) {
        this.tname = tname;
    }



    @Override
    public int compareTo(@NonNull TeamInfoBeanDB o) {

        int sort =o.mcnt- this.mcnt  ;
        if (sort == 0) {
            sort= o.ctm-this.ctm;
        }
        return sort;
    }

    @Override
    public int getOp() {
        return op;
    }


    public void setTid(int tid) {
        this.tid = tid;
    }


    public void setOp(int op) {
        this.op = op;
    }
}
