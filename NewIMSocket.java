package com.xmen.xteam.communication;

import android.content.Context;
import android.content.SharedPreferences;

import com.common.lib.base.dialog.BaseDialog;
import com.common.lib.base.dialog.DialogButtonClickListener;
import com.common.lib.log.LogX;
import com.common.lib.util.NetworkUtils;
import com.common.lib.util.SettingUtil;
import com.common.lib.util.thread.ThreadUtil;
import com.xmen.xteam.R;
import com.xmen.xteam.app.App;
import com.xmen.xteam.app.Common;
import com.xmen.xteam.broadcast.NetworkChangeReceiver;
import com.xmen.xteam.communication.bean.BaseSocketBean;
import com.xmen.xteam.model.event.SocketStateEvent;
import com.xmen.xteam.model.event.ToSocketEvent;
import com.xmen.xteam.msgsockey.socket.IMsgSocket;
import com.xmen.xteam.msgsockey.socket.Interface.DisconnectCallback;
import com.xmen.xteam.msgsockey.socket.Interface.IHeartData;
import com.xmen.xteam.msgsockey.socket.Interface.IReadCallBack;
import com.xmen.xteam.msgsockey.socket.Interface.SendMsgFailureListener;
import com.xmen.xteam.msgsockey.socket.Interface.SendMsgSucceedListener;
import com.xmen.xteam.msgsockey.socket.SocketConfig;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.msgsockey.socket.adapter.SendProtocol;
import com.xmen.xteam.msgsockey.socket.base.BytesModel;
import com.xmen.xteam.mvp.ui.activity.SplashActivity;
import com.xmen.xteam.mvp.ui.widget.dialog.CommonDialog;
import com.xmen.xteam.utils.ResUtils;
import com.xmen.xteam.utils.TimeUtils;
import com.xmen.xteam.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/4/16
 * 描    述：
 */
public class NewIMSocket {
    protected Producer<NewSendBuilder> mTimeoutQueue;
    protected ConcurrentHashMap<Integer, NewSendBuilder> mSeqnoMap = new ConcurrentHashMap<>();//线程安全map
    protected List<NewSendBuilder> mRequestQueue = new ArrayList<>();
    public static final int STATE_NORMAL = 10000; //
    public static final int STATE_CONNECTED = 10001; //连接
    public static final int STATE_DISCONNECT = 10002; //断开
    public static final int STATE_NO_NETWORK = 10003; //没有网络

    public static final int DELAYED_TIME = 20000; //超时时间
    public static final int DELAYED_TIME_RECONNECT = 15000; //收不到请求重连 包括心跳等

    private OnVerifyTokenCompleteListener mOnVerifyTokenComplete;// 当验证完TOKEN如果有其他需求可以进行拦截

    private IMsgSocket mMsgSocket;

    private int reconnectTime = 100; //重连时间


    private DisconnectCallback mDisconnectCallback;

    public static final int FAILURE_CODE_DISCONNECT = 400;// 因为断开 发送失败错误
    public static final int FAILURE_CODE_DELAYED = 401; //因为延时 导致错误
    private Thread mTimeoutQueueThread;  //延时操作轮训的线程 用于检查是否正常运行


    long onResponseDataTime = TimeUtils.getServiceTime();//收到数据的时候 记录一下时间 用于很久没有收到服务器数据的时候 断开重连

    private boolean mIsAccountOverdue;  //帐号过期
    public static final int SOCKET_TYPE_MSG_SERVER = 1;
    public static final int SOCKET_TYPE_FILE_SERVER = 2;
    private SocketConfig mSocketConfig;

    public NewIMSocket() {
        initQueue();
    }

    /**
     * 检查是否正常运行  使程序更健壮
     */
    private void checkSafety() {
        if (mTimeoutQueue == null) {
            initQueue();
        }
        if (mTimeoutQueueThread == null || !mTimeoutQueueThread.isAlive()) {
            //如果线程为NULL则重新NEW一个 或者线程已经死了 也重新创建一个
            mTimeoutQueueThread = new Thread(mTimeoutQueue);
            mTimeoutQueueThread.setName("IM_SOCKET_TimeoutQueue");
            mTimeoutQueueThread.start();
        }
    }

    private void initQueue() {
        //对等待结果的队列进行轮训
        if (mTimeoutQueue == null) {
            mTimeoutQueue = new Producer<NewSendBuilder>(2000) {
                @Override
                public void haveData(NewSendBuilder sendBuilder) {
                    if (sendBuilder == null || sendBuilder.mSendProtocol == null) {
                        return;
                    }
                    //如果完成就不需要操作了
                    if (sendBuilder.isCompleted) {
                        remove(sendBuilder);
                        return;
                    }
                    long curTime = TimeUtils.getServiceTime();
                    long createTime = sendBuilder.mCreateTime;

                    //如果超过N秒则超时
                    if (curTime - createTime > sendBuilder.delayedTime) {
                        //是否该请求必须发送成功
                        if (sendBuilder.isMust) {
                            sendBuilder.refreshCreateTime(); //刷新创建时间
//                            mRequestQueue.add(sendBuilder);
                            remove(sendBuilder);
                            send(sendBuilder);
                        } else {
                            sendBuilder.onFailure(FAILURE_CODE_DELAYED);
                            remove(sendBuilder);
                            mSeqnoMap.remove(sendBuilder.getSeqno());  //删除服务器回调监听
                        }
                    }
                }
            };

            mTimeoutQueueThread = new Thread(mTimeoutQueue);
            mTimeoutQueueThread.setName("IM_SOCKET_TimeoutQueue");
            mTimeoutQueueThread.start();

            ProducerManage.getI().addProducer(mTimeoutQueue);
        }

    }


    public SharedPreferences getSharedPreferencesScoket() {
        return App.getContext().getSharedPreferences("socket", Context.MODE_PRIVATE);
    }

    boolean isNeedVerifyToken;
    boolean isVerifyTokenSucceed;


    public IMsgSocket connectSocket(int socketType, String ip, int port, boolean isOpenHeart, boolean isVerifyToken, boolean isMsgServer) {
        mIsAccountOverdue = false;
        //检查超时线程是否正常运行
        checkSafety();

        //保存连接的参数
        saveConnectParam(socketType, ip, port, isOpenHeart, isVerifyToken, isMsgServer);

        isNeedVerifyToken = isVerifyToken;
        isVerifyTokenSucceed = false;

        mSocketConfig = getSocketConfig(ip, port, isOpenHeart, isVerifyToken, isMsgServer);
        mMsgSocket = mSocketConfig.connect();
        return mMsgSocket;
    }

    private SocketConfig getSocketConfig(String ip, int port, boolean isOpenHeart, boolean isVerifyToken, boolean isMsgServer) {
        SocketConfig socketConfig = SocketConfig.NewSocket(ip, port)
                .setConnectedCallback(() -> {
                    LogX.e("收到连上回调");
                    refreshOnResponseTime();
                    if (isMsgServer) {
                        postStickySocketState(NewIMSocket.STATE_CONNECTED, ip, port);
                        Common.getI().setCurrentServerIp(ip, port);
                    }
                    reconnectTime = 200;
                    NetworkChangeReceiver.remove(ip + port);
                    //每次连接成功以后需要校验token
                    LogX.e(isVerifyToken && !isVerifyTokenSucceed, isVerifyToken, isVerifyTokenSucceed);
                    if (isVerifyToken && !isVerifyTokenSucceed) {
                        verifyToken();
                    } else if (!isVerifyToken) {
                        anewRequestFailureQueue();
                    }
                }).setDisconnectCallback(() -> {
                    LogX.e("收到断开回调");
                    //断开以后重置 不接收消息 等待发送收到成功以后才收到
                    if (mDisconnectCallback != null) {
                        mDisconnectCallback.disconnect();
                    }

                    isVerifyTokenSucceed = false;
                    if (NetworkUtils.isNetworkAvailable()) {
                        //只有重连超过一次以后再次失败 才发失败广播
                        if (reconnectTime >= 500) {
                            if (isMsgServer) {
                                postStickySocketState(NewIMSocket.STATE_DISCONNECT, ip, port);
                            }
                        }
                        //断开后会马上重连一次 如果失败则每次重连时间加0.5秒 最高30秒
                        ThreadUtil.execute(() -> {
                            mMsgSocket.reconnect(ip, port);
                            if (reconnectTime < 10000) {
                                reconnectTime = reconnectTime + 500;
                            }
                        }, reconnectTime);
                    } else {
                        if (isMsgServer) {
                            postStickySocketState(NewIMSocket.STATE_NO_NETWORK, ip, port);
                        }
                        //没有网络

                        //等待网络改变
                        NetworkChangeReceiver.put(ip + port, networkType -> {
                            if (!networkType.isNoNetwork()) {
                                // 网络重连后 1秒后重连
                                ThreadUtil.execute(() -> mMsgSocket.reconnect(ip, port), 1000);
                                reconnectTime = 100;
                            }
                        });
                    }
                }).setReadCallback(new IReadCallBack() {
                    int index = 0;

                    @Override
                    public void receive(ReceiveProtocol receiveProtocol) {
                        refreshOnResponseTime();
                        try {
                            ProtocolParse.classify(receiveProtocol);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setLog(true);

        if (isOpenHeart) {
            socketConfig.setHeart(7000, new IHeartData() {
                private byte[] heartByte = null;

                @Override
                public byte[] demandHeart() {
                    checkSendOvertime();
                    //心跳包数据
                    if (heartByte == null) {
                        SendProtocol sendProtocol = new SendProtocol(null);
                        sendProtocol.setCmd((short) 1000);
                        sendProtocol.setExt1(1);
                        heartByte = sendProtocol.getContentBytes();
                    }
                    return heartByte;
                }
            });
        }
        return socketConfig;
    }

    private void refreshOnResponseTime() {
        onResponseDataTime = TimeUtils.getServiceTime();
        LogX.e("refreshOnResponseTime", onResponseDataTime);
    }

    private int curSocketState = NewIMSocket.STATE_NORMAL;

    private void postStickySocketState(int state, String ip, int port) {
        if (curSocketState != state) {
            curSocketState = state;
            EventBus.getDefault().postSticky(new SocketStateEvent(state, ip, port));
        }
    }

    /**
     * 保存参数 避免程序在异常重启的时候 可以拿到参数来重连
     *
     * @param socketType
     * @param ip
     * @param port
     * @param isOpenHeart
     * @param isVerifyToken
     * @param isMsgServer
     */
    private void saveConnectParam(int socketType, String ip, int port, boolean isOpenHeart, boolean isVerifyToken, boolean isMsgServer) {
        SharedPreferences.Editor edit = getSharedPreferencesScoket().edit();
        edit.putString("ip" + socketType, ip);
        edit.putInt("port" + socketType, port);
        edit.putBoolean("isOpenHeart" + socketType, isOpenHeart);
        edit.putBoolean("isVerifyToken" + socketType, isVerifyToken);
        edit.putBoolean("isMsgServer" + socketType, isMsgServer);
        edit.apply();
    }

    public class VerifyTokenIntercept {
        public void goOn() {
            anewRequestFailureQueue();
        }
    }

    private void anewRequestFailureQueue() {
        //等待token校验成功以后重新发送
        while (mRequestQueue.size() >= 1) {
            NewSendBuilder sendBuilder = mRequestQueue.remove(0);
            //如果已经超时了就不发了
            if (TimeUtils.getServiceTime() - sendBuilder.mCreateTime < DELAYED_TIME) {
                sendBuilder.refreshCreateTime();

                ToSocketEvent toSocketEvent = ToSocketEvent
                        .newMsg(sendBuilder.mSendProtocol)
                        .setSucceedListener(new SendMsgSucceedListener() {
                            @Override
                            public void sendMsgSucceed() {
                                sendBuilder.onSend();
                            }
                        })
                        .setFailureListener(new SendMsgFailureListener() {
                            @Override
                            public void sendMsgFailure() {
                                sendBuilder.onFailure(FAILURE_CODE_DISCONNECT);
                            }
                        });

                mMsgSocket.write(new BytesModel(toSocketEvent.getBytes(), toSocketEvent.getSucceedListener(), toSocketEvent.getFailureListener()));
            }
        }
    }


    public NewIMSocket setOnVerifyTokenComplete(OnVerifyTokenCompleteListener onVerifyTokenComplete) {
        mOnVerifyTokenComplete = onVerifyTokenComplete;
        return this;
    }


    private void verifyToken() {
        LogX.e("verifyToken");
        String tk = Common.getI().getCurrentLoginToken();
        if (tk == null) {
            tk = SettingUtil.getI().getCurrentLoginToken();
        }

        LogX.e("verifyToken succeed11");
        NewSendBuilder<BaseSocketBean> builder = ProtocalPackage.getI().verifyToken_74(tk)
                .setOnSucceedListener(socketBean -> {
                    LogX.e("verifyToken succeed222");
                    mIsAccountOverdue = false;
                    isVerifyTokenSucceed = true;
                    LogX.e("mOnVerifyTokenComplete", mOnVerifyTokenComplete);
                    if (mOnVerifyTokenComplete != null) {
                        mOnVerifyTokenComplete.intercept(new NewIMSocket.VerifyTokenIntercept());
                    } else {
                        anewRequestFailureQueue();
                    }

                })
                .setOnResultErrorListener((socketBean, code, errorStr) -> {
                    mIsAccountOverdue = true;
                    Common.getI().accountOverdue(errorStr);

                })
                .setOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(int failureCode) {
                        verifyToken();
                    }
                });
        send(builder);
    }

    /**
     * @param disconnectCallback 断开回调
     */
    public NewIMSocket setDisconnectCallback(DisconnectCallback disconnectCallback) {
        mDisconnectCallback = disconnectCallback;
        return this;
    }


    public boolean isConnect() {
        checkSafety();
        if (mMsgSocket == null) {
            return false;
        }
        return mMsgSocket.isConnect();
    }


    public synchronized <K extends BaseSocketBean> void send(NewSendBuilder<K> sendBuilder) {
        //检查超时线程是否正常运行
        checkSafety();

        SendProtocol sendProtocol = sendBuilder.mSendProtocol;
        mSeqnoMap.put(sendProtocol.getSeqno(), sendBuilder);
        mTimeoutQueue.add(sendBuilder);

        if (mMsgSocket == null || !mMsgSocket.isConnect()) {
            mRequestQueue.add(sendBuilder);
            return;
        }

        if (checkSendOvertime()) {
            mRequestQueue.add(sendBuilder);
            return;
        }

        //如果没有校验token 则先放在队列中 等校验完成在处理
        if (!isNeedVerifyToken || isVerifyTokenSucceed || sendProtocol.getCmd() == CMD.VERIFY_TOKEN_74) {
            ToSocketEvent toSocketEvent = ToSocketEvent
                    .newMsg(sendProtocol)
                    .setSucceedListener(new SendMsgSucceedListener() {
                        @Override
                        public void sendMsgSucceed() {
                            sendBuilder.onSend();
                        }
                    })
                    .setFailureListener(new SendMsgFailureListener() {
                        @Override
                        public void sendMsgFailure() {
                            sendBuilder.onFailure(FAILURE_CODE_DISCONNECT);
                        }
                    });
            mMsgSocket.write(new BytesModel(toSocketEvent.getBytes(), toSocketEvent.getSucceedListener(), toSocketEvent.getFailureListener()));
        } else if (mIsAccountOverdue && !ReceivePush.getI().isOtherLogin) {
            LogX.e("mIsAccountOverdue 111111", sendBuilder.mSendProtocol);
            ThreadUtil.executeOnMain(new Runnable() {
                @Override
                public void run() {
                    mIsAccountOverdue = false;
                    LogX.e("mIsAccountOverdue 22222");
                    if (App.getApp().getTopActivity() instanceof SplashActivity) {
                        ThreadUtil.executeOnMain(() -> {
                            CommonDialog.newDialog(App.getApp().getTopActivity())
                                    .setTitle(ResUtils.getString(R.string.title_hint))
                                    .setContent("账号登陆失效，请重新登陆")
                                    .setOnClickBackNoOut()
                                    .setPositive(ResUtils.getString(R.string.que_ding), new DialogButtonClickListener() {
                                        @Override
                                        public void onClick(BaseDialog dialog) {
                                            mIsAccountOverdue = false;
                                            Common.getI().logout(App.getApp().getTopActivity(), true);
                                        }
                                    }).show();
                        }, 4000);
                    } else {
                        CommonDialog.newDialog(App.getApp().getTopActivity())
                                .setTitle(ResUtils.getString(R.string.title_hint))
                                .setContent("账号登陆失效，请重新登陆")
                                .setOnClickBackNoOut()
                                .setPositive(ResUtils.getString(R.string.que_ding), new DialogButtonClickListener() {
                                    @Override
                                    public void onClick(BaseDialog dialog) {
                                        mIsAccountOverdue = false;
                                        Common.getI().logout(App.getApp().getTopActivity(), true);
                                    }
                                }).show();
                    }
                }
            });

        } else {

            LogX.e("添加到失败队列");
            mRequestQueue.add(sendBuilder);
        }
    }

    private <K extends BaseSocketBean> boolean checkSendOvertime() {
        long currentTimeMillis = TimeUtils.getServiceTime();
        LogX.e("checkSendOvertime", currentTimeMillis, onResponseDataTime, "   _________   ", currentTimeMillis - onResponseDataTime);
        if ((currentTimeMillis - onResponseDataTime) > DELAYED_TIME_RECONNECT) {
            LogX.e("checkSendOvertime 超时");
            onResponseDataTime = currentTimeMillis;
            mMsgSocket.disConnect();
            return true;
        }
        return false;
    }


    public static NewSendBuilder Builder(SendProtocol sendProtocol) {
        //添加通用参数
        sendProtocol.setExt1(Common.getI().getCurrentUserID());
        return new NewSendBuilder(sendProtocol)
                .setOnResultErrorListener(new OnResultErrorListener() {
                    @Override
                    public void onResultError(BaseSocketBean socketBean, int code, String errorStr) {
                        ToastUtils.ToastShort(errorStr, sendProtocol);

                    }
                });
    }

    public static NewSubpackageSendBuilder BuilderPage(SendProtocol sendProtocol) {
        //添加通用参数
        sendProtocol.setExt1(Common.getI().getCurrentUserID());
        return (NewSubpackageSendBuilder) new NewSubpackageSendBuilder(sendProtocol)
                .setOnResultErrorListener(new OnResultErrorListener() {
                    @Override
                    public void onResultError(BaseSocketBean socketBean, int code, String errorStr) {
                        ToastUtils.ToastShort(errorStr);
                    }
                });
    }


    public void clearQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = new ArrayList<>();
        }
        mRequestQueue.clear();
    }


    public void disconnect() {
        if (mMsgSocket != null) {
            mMsgSocket.close();
        }
    }


    /**
     * 收到数据
     *
     * @param socketBean
     */
    public void responseData(BaseSocketBean socketBean) {
        refreshOnResponseTime();

        int preseq = socketBean.getSeqno();
        NewSendBuilder sendBuilder = mSeqnoMap.get(preseq);
        if (sendBuilder != null) {
            boolean isRemoveSeqno = true;
            if (socketBean.getRes() == 1 || socketBean.getRes() == -1) {
                isRemoveSeqno = sendBuilder.onSucceed(socketBean);
            } else {
                isRemoveSeqno = sendBuilder.onResultError(socketBean, socketBean.getRes(), socketBean.getErr());
            }
            if (isRemoveSeqno) {
                mSeqnoMap.remove(preseq);
            }

        }
    }

}
