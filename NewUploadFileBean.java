package com.xmen.xteam.communication.bean;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class NewUploadFileBean extends BaseSocketBean {

    private int fid ; //服务器生成的上传文件id，客户端在正式上传时需要把该id带上
    private int dsz ; //每次发送数据块大小 （暂定 2^15=32768，最后一块不够这个数，则填实际大小） 
    private String url ; //如果MD5串在服务器存在，表示服务器已经有该文件，就返回服务器中文件的url，客户端不必上传。如无MD5，则为空 
    private String param ; //如果是图片，该字段是上面的url对应的缩略图的url

    public NewUploadFileBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
         fid = getInt("fid");
         dsz = getInt("dsz"); 
         url = getString("url");
        param = getString("param");
    }

    public int getFid() {return fid;}
    public int getDsz() {return dsz;}
    public String getUrl() {return url;}
    public String getParam() {return param;}

}
