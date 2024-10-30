package com.xmen.xteam.communication.bean;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class MultiCallBean extends BaseSocketBean {

    private int tid ; //团队ID 
    private int uid ; //用户id 
    private int pw ; //用户密码 
    private int cid ; //会议id 
    private int rid ; //注册id
    private int type ; //请求类型: 1 发起 2 回应 3系统通知
    private int opt ; //操作类型：1 会议邀请 2 会议关闭 3 退出会议 4 会议中邀请用户 5 会议中移除用户 6 会议类型更改 7 管理会议中用户 8 同意邀请 9 拒绝邀请 10 会议用户通话状态变更 11 与IM网络断开连接 12 与IM网络恢复连接 13 与FS网络断开连接(预留) 14 与FS网络恢复连接(预留) 
    private int avtype ; //音视频类型： 3 音频 4 音频+屏幕共享 
    private int ctype ; //控制类型：1 单个用户静音 2 解除单个用户静音 3 全员静音 4 解除全员静音 
    private int curtm ; //服务器当前时间戳 
    private List<MultiCallUserBean> uinfo ; //用户的信息结构

    public MultiCallBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         tid = getInt("tid"); 
         uid = getInt("uid"); 
         pw = getInt("pw"); 
         cid = getInt("cid");
        rid = getInt("rid");
         type = getInt("type");
         opt = getInt("opt"); 
         avtype = getInt("avtype"); 
         ctype = getInt("ctype"); 
         curtm = getInt("curtm");
        this.uinfo =new ArrayList<>();
        for (HashMap<String, Object> hashMap : getList("uinfo")) {
            uinfo.add(new MultiCallUserBean(hashMap,preseq));
        }
    }

    public int getTid() {return tid;}
    public int getUid() {return uid;}
    public int getPw() {return pw;}
    public int getCid() {return cid;}
    public int getType() {return type;}
    public int getOpt() {return opt;}
    public int getAvtype() {return avtype;}
    public int getCtype() {return ctype;}
    public int getCurtm() {return curtm;}

    public int getRid() {
        return rid;
    }

    public List<MultiCallUserBean> getUinfo() {return uinfo;}

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public void setAvtype(int avtype) {
        this.avtype = avtype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public void setCurtm(int curtm) {
        this.curtm = curtm;
    }
}
