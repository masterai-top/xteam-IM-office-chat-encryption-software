package com.xmen.xteam.communication;

import android.os.SystemClock;

import com.common.lib.base.dialog.BaseDialog;
import com.common.lib.log.LogX;
import com.common.lib.util.ResUtils;
import com.common.lib.util.SettingUtil;
import com.common.lib.util.ToastUtils;
import com.common.lib.util.thread.ThreadPoolFactory;
import com.common.lib.util.thread.ThreadPoolProxy;
import com.common.lib.util.thread.ThreadUtil;
import com.xmen.xteam.Interface.CommonCallBack;
import com.xmen.xteam.R;
import com.xmen.xteam.broadcast.NetworkChangeReceiver;
import com.xmen.xteam.communication.bean.BaseSocketBean;
import com.xmen.xteam.msgsockey.socket.adapter.SendProtocol;
import com.xmen.xteam.mvp.contract.BaseContract;
import com.xmen.xteam.service.MsgSocketService;
import com.xmen.xteam.utils.TimeUtils;

;

public class NewSendBuilder<T extends BaseSocketBean> {
    protected SendProtocol mSendProtocol;
    protected OnSucceedListener<T> mOnSucceedListener;
    protected OnFailureListener mOnFailureListener;
    protected OnSendSocketSucceed mOnSendSocketSucceed;
    protected OnResultErrorListener mOnResultErrorListener;

    protected boolean isMust = false; //必须请求到
    protected  long mCreateTime;
    protected boolean isCompleted = false;
    protected BaseContract.BaseView mView;
    protected ThreadPoolProxy mOnSucceedThread;
    protected BaseDialog mBaseDialog;
    protected boolean mIsNotNetworkHnit;
    protected int delayedTime = NewIMSocket.DELAYED_TIME;


    //用于校验服务器时间用的
    private long sendTime = 0;
    private long succeedTime = 0;

    public NewSendBuilder(SendProtocol sendProtocol) {
        mSendProtocol = sendProtocol;
        mSendProtocol.setSeqno(SettingUtil.getI().getIncreaseNumber());
        mCreateTime = TimeUtils.getServiceTime();
    }


    public NewSendBuilder<T> setShowLoadView(BaseContract.BaseView view) {
        mView = view;
        return this;
    }


    public void refreshCreateTime(){
        mCreateTime = TimeUtils.getServiceTime();
    }

    /**
     * 帮助管理dialog隐藏
     *
     * @return
     */
    public NewSendBuilder<T> setShowLoadView(BaseDialog showLoadView) {
        if (showLoadView != null) {
            mBaseDialog = showLoadView;

        }

        return this;
    }

    public NewSendBuilder<T> onCrateSendProtocol(CommonCallBack<SendProtocol> onCrateSendProtocol) {
        if (onCrateSendProtocol != null) {
            onCrateSendProtocol.callBack(mSendProtocol);
        }
        return this;
    }

    public int getSeqno() {
        int seqno = mSendProtocol.getSeqno();
        return seqno;
    }

    public NewSendBuilder<T> setOnSucceedListener(OnSucceedListener<T> onSucceedListener) {
        mOnSucceedListener = onSucceedListener;
        return this;
    }

    public NewSendBuilder<T> setOnFailureListener(OnFailureListener onFailureListener) {
        mOnFailureListener = onFailureListener;
        return this;
    }

    public NewSendBuilder<T> setOnResultErrorListener(OnResultErrorListener onResultErrorListener) {
        mOnResultErrorListener = onResultErrorListener;
        return this;
    }

    protected void onFailure(int failureCodeDisconnect) {
        if (!isCompleted ) {
            ThreadUtil.executeOnMain(() -> {
                if (mOnFailureListener != null) {
                    mOnFailureListener.onFailure(failureCodeDisconnect);
                }

                clear();
            });
        }
        isCompleted = true;
    }

    public NewSendBuilder<T> setDelayedTime(int delayedTime) {
        this.delayedTime = delayedTime;
        return this;
    }

    public void send() {
        sendTime = SystemClock.elapsedRealtime();

        if (mIsNotNetworkHnit) {
            if (NetworkChangeReceiver.isNoNetwork()) {
                ToastUtils.ToastShort(ResUtils.getString(R.string.dqwwl));
                clear();
                return;
            }
        }

        if (mView != null) {
            ThreadUtil.executeOnMain(() -> mView.showLoadView());
        }else if(mBaseDialog != null){
            if (!mBaseDialog.isShow()) {
                mBaseDialog.show();
            }
        }
        ThreadPoolFactory.getIOPool().execute(() -> MsgSocketService.send(NewSendBuilder.this));
    }

    protected boolean onSucceed(BaseSocketBean socketBean) {
        onReceiveServerResult(socketBean);

        if (!isCompleted ) {
            succeedThread(aVoid -> {
                if (mOnSucceedListener != null) {
                    try {
                        mOnSucceedListener.onSucceed((T) socketBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogX.e("请求结果转换失败", " 崩溃异常对象" + socketBean);
                    }
                }
                clear();
            });
        }
        isCompleted = true;
        return true;
    }

    protected void onReceiveServerResult(BaseSocketBean socketBean) {

//        sendTime = SystemClock.elapsedRealtime();
        long intervalTime = SystemClock.elapsedRealtime() - sendTime;
        long servicerTime = socketBean.getServicerTime();
        TimeUtils.initServerTime(servicerTime,intervalTime);

    }

    public NewSendBuilder<T> setOnSendSocketSucceed(OnSendSocketSucceed onSendSocketSucceed) {
        mOnSendSocketSucceed = onSendSocketSucceed;
        return this;
    }

    public void onSend() {
        if (mOnSendSocketSucceed != null) {
            mOnSendSocketSucceed.onSend();
        }
    }

    protected boolean onResultError(BaseSocketBean socketBean, int code, String errorStr) {
        onReceiveServerResult(socketBean);
            ThreadUtil.executeOnMain(() -> {
                if (mOnResultErrorListener != null) {
                    mOnResultErrorListener.onResultError(socketBean,code, errorStr);
                }

                clear();
            });
        return true;
    }

    protected void succeedThread(CommonCallBack<Void> callback){
        if (mOnSucceedThread != null) {
            mOnSucceedThread.execute(() -> {
                if (callback != null) {
                    callback.callBack(null);
                }
            });
        } else {
            ThreadUtil.executeOnMain(() -> {
                if (callback != null) {
                    callback.callBack(null);
                }
            });
        }

    }

    protected void clear() {
        mOnSucceedListener = null;
        mOnResultErrorListener = null;
        mOnFailureListener = null;
        if (mBaseDialog != null) {
            mBaseDialog.dismiss();
            mBaseDialog = null;
        }
        if (mView != null) {
            mView.dismissLoadView();
            mView = null;
        }
    }

    public NewSendBuilder<T> setMust() {
//        isMust = true;
        return this;
    }

    public NewSendBuilder<T> setOnSucceedThread(ThreadPoolProxy onSucceedThread) {
        mOnSucceedThread = onSucceedThread;
        return this;
    }


    public NewSendBuilder<T> setNotNetworkHnit() {
        mIsNotNetworkHnit = true;
        return this;
    }

    public NewSendBuilder<T> setDelayedRequestHnit() {
        setOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(int failureCode) {
                switch (failureCode) {
                    case NewIMSocket.FAILURE_CODE_DELAYED: {
                        ToastUtils.ToastShort(ResUtils.getString(R.string.wlqqcs));
                        break;
                    }
                }
            }
        });
        return this;
    }



}