package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.wcdb.bean.EmojiDB;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class EmojiBean extends BaseSocketBean {

    private int eid ; //表情文件id ， type为删除记录时为：删除emojId
    private String param ; //表情参数type 为表情时：{“oh”:1372,”ow”:1028,”th”:400,”tw”:300,”url”:”upload/1/2/20180403/15/10000003_1_1522741818399194.jpg”} type 为删除记录时 为空
    private int status ; //记录参数，0:正常 1：已删除 2：删除表情


    public EmojiBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol preseq) {
        eid = getInt("eid");
        status = getInt("status");
         param = getString("param");

    }


    public String getParam() {return param;}

    public int getEid() {
        return eid;
    }

    public EmojiDB toEmojiDB(){
        return new EmojiDB(eid,param);
    }

    @Override
    public String toString() {
        return "EmojiBean{" +
                "eid=" + eid +
                ", param='" + param + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
