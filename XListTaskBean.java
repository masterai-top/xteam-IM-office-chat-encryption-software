package com.xmen.xteam.communication.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.mvp.ui.widget.adapter.ExpandableItemAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class XListTaskBean extends BaseSocketBean implements MultiItemEntity {

    private int tkid ; //任务Id (请求类型为1添加时为空，2删除、3修改时需要传) 
    private String tktitle ; //任务标题 
    private int frsppsn ; //第一责任人(选择的责任人的uid) 
    private List<Integer> excut ; //执行者(选择的执行者的uid，数组)
    private String tkdes ; //任务描述 
    private int tkstat ; //任务状态： 1 未开始 2 进行中 3 已完成 
    private int level ; //优先级： 1 一级 2 二级 3 三级 
    private String bgntm ; //开始时间(时间戳)
    private String endtm ; //截止时间(时间戳)
    private String plntm ; //计划工时 (小时) 
    private String acttm ; //实际工时 (小时) 
    private String remark ; //备注
    private int wkpro ; //进度 (最大为100) 
    private int crttm ; //创建时间戳 
    private int updtm ; //最近更新时间戳 

    public XListTaskBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    public XListTaskBean() {
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol,ReceiveProtocol  preseq) {
         tkid = getInt("tkid"); 
         tktitle = getString("tktitle"); 
         frsppsn = getInt("frsppsn"); 
        Object o = mReceiveProtocol.get("excut");
        try {
            if (o != null && o instanceof List) {
                excut = (List<Integer>) o;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
         tkdes = getString("tkdes");
         tkstat = getInt("tkstat"); 
         level = getInt("level"); 
         bgntm = getString("bgntm");
         endtm = getString("endtm");
         plntm = getString("plntm"); 
         acttm = getString("acttm"); 
         remark = getString("remark");
         wkpro = getInt("wkpro"); 
         crttm = getInt("crttm"); 
         updtm = getInt("updtm"); 

    }

    public int getTkid() {return tkid;}
    public String getTktitle() {return tktitle;}
    public int getFrsppsn() {return frsppsn;}
    public List<Integer> getExcut() {return excut;}
    public String getTkdes() {return tkdes;}
    public int getTkstat() {return tkstat;}
    public int getLevel() {return level;}
    public String getBgntm() {return bgntm;}
    public String getEndtm() {return endtm;}
    public String getPlntm() {return plntm;}
    public String getActtm() {return acttm;}
    public String getRemark() {return remark;}
    public int getWkpro() {return wkpro;}
    public int getCrttm() {return crttm;}
    public int getUpdtm() {return updtm;}


    public void setTkid(int tkid) {
        this.tkid = tkid;
    }

    public void setTktitle(String tktitle) {
        this.tktitle = tktitle;
    }

    public void setFrsppsn(int frsppsn) {
        this.frsppsn = frsppsn;
    }

    public void setExcut(List<Integer> excut) {
        this.excut = excut;
    }

    public void setTkdes(String tkdes) {
        this.tkdes = tkdes;
    }

    public void setTkstat(int tkstat) {
        this.tkstat = tkstat;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setBgntm(String bgntm) {
        this.bgntm = bgntm;
    }

    public void setEndtm(String endtm) {
        this.endtm = endtm;
    }

    public void setPlntm(String plntm) {
        this.plntm = plntm;
    }

    public void setActtm(String acttm) {
        this.acttm = acttm;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setWkpro(int wkpro) {
        this.wkpro = wkpro;
    }

    public void setCrttm(int crttm) {
        this.crttm = crttm;
    }

    public void setUpdtm(int updtm) {
        this.updtm = updtm;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_PERSON;
    }

    @Override
    public String toString() {
        return "XListTaskBean{" +
                "tkid=" + tkid +
                ", tktitle='" + tktitle + '\'' +
                ", frsppsn=" + frsppsn +
                ", excut=" + excut +
                ", tkdes='" + tkdes + '\'' +
                ", tkstat=" + tkstat +
                ", level=" + level +
                ", bgntm='" + bgntm + '\'' +
                ", endtm='" + endtm + '\'' +
                ", plntm='" + plntm + '\'' +
                ", acttm='" + acttm + '\'' +
                ", remark='" + remark + '\'' +
                ", wkpro=" + wkpro +
                ", crttm=" + crttm +
                ", updtm=" + updtm +
                '}';
    }
}
