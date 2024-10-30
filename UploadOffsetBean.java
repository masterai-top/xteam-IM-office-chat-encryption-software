package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class UploadOffsetBean extends BaseSocketBean {

    private int fid ; //服务器生成的上传文件id 
    private long offset ; //下次数据块开始位置的偏移量，从文件的第一个byte开始算的位置
    private int dsz ; //下次发送数据块大小 

    public UploadOffsetBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         fid = getInt("fid"); 
         offset = getLong("offset");
         dsz = getInt("dsz"); 

    }

    public int getFid() {return fid;}
    public long getOffset() {return offset;}
    public int getDsz() {return dsz;}

}
