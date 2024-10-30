package com.xmen.xteam.communication.bean;

import com.common.lib.bean.IPBean;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class UserInfoBean extends BaseSocketBean {

    int uid;//用户id
    int sex;//1男2女0未知
    String name;//用户名
    String des;//个性签名
    String avatar;//用户头像的url
    String token;//校验令牌
    List<IPBean> ipinfo;//消息服务器IP信息数组列表
    private WholeUserInfoBean mWholeUserInfoBean;

    public UserInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        token = getString("tk");
        mWholeUserInfoBean = new WholeUserInfoBean(receiveProtocol, preseq);
        ipinfo = new ArrayList<>();

        List<HashMap<String, Object>> o = getList("ipinfo");
        for (HashMap<String, Object> hashMap : o) {
            String ip = (String) hashMap.get("ip");
            int port = (int) hashMap.get("port");
            IPBean ipBean = new IPBean(ip, port);
            ipinfo.add(ipBean);
        }
    }

    public UserInfoBean( int uid, int sex, String name, String des, String avatar, String token, List<IPBean> ipinfo) {
        super(null, null);
        this.uid = uid;
        this.sex = sex;
        this.name = name;
        this.des = des;
        this.avatar = avatar;
        this.token = token;
        this.ipinfo = ipinfo;
    }

    public String getToken() {
        return token;
    }


    public List<IPBean> getIpinfo() {
        return ipinfo;
    }


    @Override
    public String toString() {
        return "UserInfoBean{" +
                ", uid=" + uid +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", des='" + des + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                ", ipinfo=" + ipinfo +
                '}';
    }

    public WholeUserInfoBean getWholeUserInfoBean() {
        return mWholeUserInfoBean;
    }

}
