package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/18
 * 描    述：
 */
public class NewTeamGroupMenberInfoBean extends BaseSubpackageSocketBean  {

    private ArrayList<GroupInfoBean> mGinfo;
    private ArrayList<ContactInfoBeanDB> mUinfo;

    public NewTeamGroupMenberInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    public void fusePage(BaseSubpackageSocketBean minor) {
        NewTeamGroupMenberInfoBean bean = (NewTeamGroupMenberInfoBean) minor;
        mGinfo.addAll(bean.getGinfo());
        mUinfo.addAll(bean.getUinfo());
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        mGinfo = new ArrayList<>();
        mUinfo = new ArrayList<>();

        List<HashMap<String,Object>> g = getList("ginfo");
        List<HashMap<String,Object>> u = getList("uinfo");
        for (HashMap<String, Object> hashMap : g) {
            GroupInfoBean groupInfoBean = new GroupInfoBean(hashMap, preseq);
            mGinfo.add(groupInfoBean);
        }
        for (HashMap<String, Object> hashMap : u) {
            ContactInfoBeanDB memberInfoBean = new ContactInfoBeanDB(hashMap, preseq);
            mUinfo.add(memberInfoBean);
        }
    }



    public ArrayList<GroupInfoBean> getGinfo() {
        return mGinfo;
    }

    public ArrayList<ContactInfoBeanDB> getUinfo() {
        return mUinfo;
    }

    @Override
    public String toString() {
        return "NewTeamGroupMenberInfoBean{" +
                "mGinfo=" + mGinfo +
                ", mUinfo=" + mUinfo +
                '}';
    }
}
