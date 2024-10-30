package com.xmen.xteam.communication.bean;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmen.xteam.R;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;

import java.util.HashMap;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class OnLineStateBean extends BaseSocketBean {

    private int uid; //用户id
    private String curstat; //当前客户端活动状态：第一位为显示状态，第二位为WEB，第三位为PC，第四位为手机端，如：”0033” 表示需要显示的状态0为在线，WEB状态0为在线，PC的状态为离线，手机端状态为离线
    private int state;//0为正常，1为离开 2/忙碌，3为请勿打扰 4为离线状态
    public static final int STATE_ONLINE = 0;//0为正常
    public static final int STATE_LEAVE = 1;//1为离开
    public static final int STATE_BUSY = 2;//忙碌
    public static final int STATE_BOTHER = 3; //3为请勿打扰
    public static final int STATE_OFFLINE = 4; //4为离线状态

    public OnLineStateBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }

    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        uid = getInt("uid");
        curstat = getString("curstat");
        if (!TextUtils.isEmpty(curstat)) {
            try {
                state = Integer.parseInt(curstat.substring(0, 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public int getUid() {
        return uid;
    }

    public String getCurstat() {
        return curstat;
    }

    public int getState() {
        return state;
    }
    public int getAnroidState() {
        try {
            return Integer.parseInt(curstat.substring(3, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void bindImage(ImageView iv_head, ImageView iv_state) {
        if (getState() == OnLineStateBean.STATE_OFFLINE) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            iv_head.setColorFilter(filter);
        } else {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(1);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            iv_head.setColorFilter(filter);
        }

        switch (getState()) {
            case OnLineStateBean.STATE_ONLINE: {
                iv_state.setVisibility(View.VISIBLE);
                iv_state.setImageResource(R.mipmap.group28_3);
                break;
            }
            case OnLineStateBean.STATE_LEAVE: {
                iv_state.setVisibility(View.VISIBLE);
                iv_state.setImageResource(R.mipmap.group28_0);
                break;
            }
            case OnLineStateBean.STATE_BUSY: {
                iv_state.setVisibility(View.VISIBLE);
                iv_state.setImageResource(R.mipmap.group28_1);
                break;
            }
            case OnLineStateBean.STATE_BOTHER: {
                iv_state.setVisibility(View.VISIBLE);
                iv_state.setImageResource(R.mipmap.group28_2);
                break;
            }
            case OnLineStateBean.STATE_OFFLINE: {
                iv_state.setVisibility(View.GONE);
                break;
            }
        }
    }
    public void bindTextView(ImageView iv_head, TextView tv_state) {
        if (getState() == OnLineStateBean.STATE_OFFLINE) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            iv_head.setColorFilter(filter);
        } else {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(1);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            iv_head.setColorFilter(filter);
        }

        switch (getState()) {
            case OnLineStateBean.STATE_ONLINE: {
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("[在线]");
                break;
            }
            case OnLineStateBean.STATE_LEAVE: {
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("[离开]");
                break;
            }
            case OnLineStateBean.STATE_BUSY: {
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("[忙碌]");
                break;
            }
            case OnLineStateBean.STATE_BOTHER: {
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("[请勿打扰]");
                break;
            }
            case OnLineStateBean.STATE_OFFLINE: {
                tv_state.setVisibility(View.VISIBLE);
                tv_state.setText("[离线]");
                break;
            }
        }
    }
}
