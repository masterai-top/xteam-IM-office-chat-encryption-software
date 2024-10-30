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
public class DelEmojiBean extends BaseSocketBean{

    private ArrayList<Integer> delEmojis ; //删除成功的表情id

    public DelEmojiBean(HashMap<String, Object> bodyMap, ReceiveProtocol receiveProtocol) {
        super(bodyMap,receiveProtocol);
    }

    public ArrayList<Integer>  getDelemojis() {return delEmojis;}

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        delEmojis = getListNew("einfo");
    }

    private ArrayList<Integer> getListNew(String key) {
        if (mReceiveProtocol == null) {
            return new ArrayList<>();
        }
        Object o = mReceiveProtocol.get(key);
        try {
            if (o != null && o instanceof List) {
                return (ArrayList<Integer>) mReceiveProtocol.get(key);
            }
        }catch (Exception e){

        }

        return new ArrayList<>();
    }

    public ArrayList<Integer> getDelEmojis() {
        return delEmojis;
    }
}
