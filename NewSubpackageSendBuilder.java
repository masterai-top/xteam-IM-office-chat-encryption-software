package com.xmen.xteam.communication;

import android.util.SparseArray;

import com.common.lib.log.LogX;
import com.common.lib.util.thread.ThreadUtil;
import com.xmen.xteam.communication.bean.BaseSocketBean;
import com.xmen.xteam.communication.bean.BaseSubpackageSocketBean;
import com.xmen.xteam.communication.bean.SubpackageBean;
import com.xmen.xteam.communication.bean.SubpackageListBean;
import com.xmen.xteam.msgsockey.socket.adapter.SendProtocol;

import java.util.List;


public class NewSubpackageSendBuilder<T extends BaseSocketBean> extends NewSendBuilder<T> {

    private List<SubpackageBean> mList;
    private SparseArray<BaseSubpackageSocketBean> mPage = new SparseArray<>();
    private OnSubpackageSucceedListener<T> mListener;

    public NewSubpackageSendBuilder(SendProtocol sendProtocol) {
        super(sendProtocol);
    }


    @Override
    protected boolean onSucceed(BaseSocketBean socketBean) {
        if (socketBean == null) {
            return isCompleted;
        }
        onReceiveServerResult(socketBean);


        if (socketBean instanceof SubpackageListBean) {
            mList = ((SubpackageListBean) socketBean).getList();

            if (mList.size() ==0) {
                isCompleted = true;


                succeedThread(aVoid -> {
                    if (mListener != null) {
                        mListener.onSucceed(null);
                    }
                });

            }else{
                for (SubpackageBean subpackageBean : mList) {
                    int npack = subpackageBean.getNpack();
                    if (npack != 0) {
                        return isCompleted;
                    }
                }
                isCompleted = true;

                succeedThread(aVoid -> {
                    if (mListener != null) {
                        mListener.onSucceed(null);
                    }
                });
            }
        } else if (socketBean instanceof BaseSubpackageSocketBean) {

            //判断包是否接收完毕了
            int cmd = socketBean.getCmd();

            //保存多次接收的包
            BaseSubpackageSocketBean baseSubpackageSocketBean = mPage.get(cmd);
            if (baseSubpackageSocketBean == null) {
                mPage.put(cmd, (BaseSubpackageSocketBean) socketBean);
            } else {
                baseSubpackageSocketBean.fusePage((BaseSubpackageSocketBean) socketBean);
            }

            //判断是否接收完成
            isReceiveFinish(cmd);
            //支持旧方法  但是会多次调用
            callOldSucceedListener(socketBean);
            if (isCompleted && mListener != null) {
                BaseSubpackageSocketBean baseSubpackageSocketBean1 = mPage.get(cmd);
                succeedThread(aVoid -> {
                    if (mListener != null) {
                        mListener.onSucceed((T) baseSubpackageSocketBean1);
                    }

                    mPage = null;
                    mList = null;
                    mListener = null;
                });
            }
        } else {
            //判断包是否接收完毕了
            int cmd = socketBean.getCmd();

            isReceiveFinish(cmd);

            //支持旧方法  但是会多次调用
            callOldSucceedListener(socketBean);
        }
        return isCompleted;
    }

    private void isReceiveFinish(int cmd) {
        //判断是否接收完成
        boolean dissatisfy = false;
        for (SubpackageBean subpackageBean : mList) {
            if (subpackageBean.getCmd() == cmd) {
                subpackageBean.setCurPackSum(subpackageBean.getCurPackSum() + 1);
            }

            if (subpackageBean.getNpack() != subpackageBean.getCurPackSum()) {
                dissatisfy = true;
            }
        }
        if (!dissatisfy) {
            isCompleted = true;
        }
    }

    /**
     * 支持旧方法  但是会多次调用
     *
     * @param socketBean
     */
    private void callOldSucceedListener(BaseSocketBean socketBean) {
        succeedThread(aVoid -> {
            try {
                if (mOnSucceedListener != null) {
                    mOnSucceedListener.onSucceed((T) socketBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogX.e("请求结果转换失败", " 崩溃异常对象" + socketBean);
            }
            if (isCompleted) {
                clear();
            }
        });
    }


    @Override
    protected boolean onResultError(BaseSocketBean socketBean, int code, String errorStr) {
        onReceiveServerResult(socketBean);
        if (mOnResultErrorListener != null) {
            ThreadUtil.executeOnMain(() -> {
                if (mOnResultErrorListener != null) {
                    mOnResultErrorListener.onResultError(socketBean, code, errorStr);
                }
                clear();
            });
        }
        return false;
    }

    public NewSubpackageSendBuilder<T> setOnSubpackageSucceedListener(OnSubpackageSucceedListener<T> listener) {
        mListener = listener;
        return this;
    }


    @Override
    public NewSubpackageSendBuilder<T> setMust() {
        return (NewSubpackageSendBuilder<T>) super.setMust();
    }
}