package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangqi on 2018/7/19.
 */

public class CountryCodeBean extends BaseSocketBean {

    private List<CountryCodeInfoBean> conCodelist;
    private String simplePy;

    public CountryCodeBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        conCodelist = new ArrayList<>();

        List<HashMap<String, Object>> o = getList("dms");
        for (HashMap<String, Object> hashMap : o) {
            String countryName = (String) hashMap.get("name");
            String countryCode = (String) hashMap.get("dm");
            CountryCodeInfoBean countryCodeInfoBean = new CountryCodeInfoBean(countryName, countryCode);
            conCodelist.add(countryCodeInfoBean);
        }
    }

    public List<CountryCodeInfoBean> getConCodelist() {
        return conCodelist;
    }

    @Override
    public String toString() {
        return "CountryCodeBean{" +
                "conCodelist=" + conCodelist +
                '}';
    }
}
