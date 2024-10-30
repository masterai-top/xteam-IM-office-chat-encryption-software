package com.xmen.xteam.communication.bean;

import android.text.TextUtils;

import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.utils.HanziToPinyin;
import com.xmen.xteam.wcdb.bean.UserInfoDB;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class WholeUserInfoBean extends BaseSocketBean {

    private int uid ; //用户id 
    private String nm ; //用户名称 
    private String avt ; //用户头像的url 
    private int sex ; //1男2女0未知 
    private String des ; //个性签名 
    private int dm ; //区号 86 
    private String pho ; //手机号 
    private String eml ; //email
    private int tid ;//6以后新版本增加
    private int mSpf;
    private int mSef;
    private ContactInfoBeanDB mContactInfoBeanDB;

    public WholeUserInfoBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol,preseq);
    }

    public WholeUserInfoBean(int uid, String nm, String avt, int sex, String des, int dm, String pho, String eml) {
        super(null, null);
        this.uid = uid;
        this.nm = nm;
        this.avt = avt;
        this.sex = sex;
        this.des = des;
        this.dm = dm;
        this.pho = pho;
        this.eml = eml;
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
         nm = getString("nm");
        if (TextUtils.isEmpty(nm)) {
            nm = getString("unm");
        }
         avt = getString("avt"); 
         sex = getInt("sex"); 
         des = getString("des"); 
         dm = getInt("dm"); 
         pho = getString("pho"); 
         eml = getString("eml");
        tid = getInt("tid");
        //团队中显示手机标志 0：不显示，1：显示
        mSpf = getInt("spf");
        //团队中显示邮箱标志 0：不显示，1：显示
        mSef = getInt("sef");

        mContactInfoBeanDB = new ContactInfoBeanDB(receiveProtocol,preseq);

        if (mSpf == 0 ) {
            pho= "";
        }
        if (mSef == 0) {
            eml = "";
        }
    }

    public int getUid() {return uid;}
    public String getNm() {return nm;}
    public String getAvt() {return avt;}
    public int getSex() {return sex;}
    public String getDes() {return des;}
    public int getDm() {return dm;}
    public String getPho() {return pho;}
    public String getEml() {return eml;}


    public UserInfoDB toUserInfoDB(String whole, String simple) {
        return new UserInfoDB(uid,nm,sex,des,avt,whole,simple,dm,pho,eml);
    }
    public UserInfoDB toUserInfoDB() {
        HanziToPinyin.Pinyin pinyin = HanziToPinyin.getInstance().getPinyin(nm);
       String whole = pinyin.whole;
        String  simple = pinyin.simple;
        return new UserInfoDB(uid,nm,sex,des,avt,whole,simple,dm,pho,eml);
    }

    @Override
    public String toString() {
        return "WholeUserInfoBean{" +
                "uid=" + uid +
                ", nm='" + nm + '\'' +
                ", avt='" + avt + '\'' +
                ", sex=" + sex +
                ", des='" + des + '\'' +
                ", dm=" + dm +
                ", pho='" + pho + '\'' +
                ", eml='" + eml + '\'' +
                ", tid=" + tid +
                '}';
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setDm(int dm) {
        this.dm = dm;
    }

    public void setPho(String pho) {
        this.pho = pho;
    }

    public void setEml(String eml) {
        this.eml = eml;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getSpf() {
        return mSpf;
    }

    public int getSef() {
        return mSef;
    }

    public ContactInfoBeanDB getContactInfoBeanDB() {
        return mContactInfoBeanDB;
    }
}
