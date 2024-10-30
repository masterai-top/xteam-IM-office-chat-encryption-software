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
public class ShareSearchFilesBean extends BaseSocketBean {

    private int tid ; //团队id
    private int toid ; //会话对象id，如果是群聊，则群id 如果是私聊，则为私聊对象的id
    private int sid ; //发送者id
    private int stp ; //会话类型 0私聊 1群聊
    private int ftp ; //1为链接 2为图片 3为文件 4全部
    private int page ; //分页,第一次请求发送1，二次发送2，20条数据一页
    private int res ; //结果 1:成功 其他：失败
    private String err ; //错误描述，对应res的错误码，如果res=1则为空
    private List<ShareFilesDetialInfoBean> param;//搜索文件；

    public ShareSearchFilesBean(HashMap<String, Object> bodyMap, ReceiveProtocol receiveProtocol){
        super(bodyMap,receiveProtocol);
    }


    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {

        param = new ArrayList<>();

         tid = getInt("tid");
         toid = getInt("toid");
         sid = getInt("sid");
         stp = getInt("stp");
         ftp = getInt("ftp");
         page = getInt("page");
         res = getInt("res");
         err = getString("err");

        List<HashMap<String, Object>> para3 = getList("param");
        for (HashMap<String, Object> hashMap :para3) {
            ShareFilesDetialInfoBean shareFilesDetialInfoBean = new ShareFilesDetialInfoBean(hashMap, preseq);
            param.add(shareFilesDetialInfoBean);
        }
    }

    public int getTid() {return tid;}
    public int getToid() {return toid;}
    public int getSid() {return sid;}
    public int getStp() {return stp;}
    public int getFtp() {return ftp;}
    public int getPage() {return page;}
    public int getRes() {return res;}
    public String getErr() {return err;}

    public List<ShareFilesDetialInfoBean> getParam() {
        return param;
    }

    public void setParam(List<ShareFilesDetialInfoBean> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ShareMediaBean{" +
                "tid=" + tid +
                ", toid=" + toid +
                ", sid=" + sid +
                ", stp=" + stp +
                ", ftp=" + ftp +
                ", page=" + page +
                ", res=" + res +
                ", err='" + err + '\'' +
                ", param=" + param +
                '}';
    }
}
