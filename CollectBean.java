package com.xmen.xteam.communication.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述： 配置信息
 */
public class CollectBean extends BaseSocketBean  implements MultiItemEntity {

    private int cid ; //收藏ID
    private int sid ; //用户ID
    private int toid ; //会话对象id，如果是群聊，则群id 如果是私聊，则为私聊对象的id
    private int mid ; //消息id
    private int stp ; //会话类型 0私聊 1群聊
    private String msg ; //消息内容 如果是图片文件等，则为空
    private String param ; //消息参数属性json串（普通消息的该参数为空），根据消息类型不同json串键值对不同，具体看附录
    private int mtp ; //消息类型 0为系统通知 1为文本 2为图片 3为文件 4为语音 5为视频 6被拉入团队消息 7被拉入群消息 8团队/群系统说明消息(公告板的第一条消息) 9 @用户类型 10消息撤回 11编辑消息 12表情
    private int ctp ; //收藏类型 0收藏 1删除 （收藏类型为删除时，param的json串的格式为：{“cid”:123}）
    private long st ; //发送时间 时间戳8位 精确到毫秒






    @Override
    public int getItemType() {
        return mtp;
    }


    public CollectBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }



    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        cid = getInt("cid");
        sid = getInt("sid");
        toid = getInt("toid");
        mid = getInt("mid");
        stp = getInt("stp");
        msg = getString("msg");
        param = getString("param");
        mtp = getInt("mtp");
        ctp = getInt("ctp");
        st = getInt("ct");
    }


    public int getCid() {return cid;}
    public int getSid() {return sid;}
    public int getToid() {return toid;}
    public int getMid() {return mid;}
    public int getStp() {return stp;}
    public String getMsg() {return msg;}
    public String getParam() {return param;}
    public int getMtp() {return mtp;}
    public int getCtp() {return ctp;}
    public long getSt() {return st;}
}
