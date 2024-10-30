package com.xmen.xteam.communication;

import android.content.DialogInterface;
import android.os.SystemClock;

import com.common.lib.log.LogX;
import com.common.lib.util.DirUtil;
import com.common.lib.util.thread.ThreadUtil;
import com.xmen.xteam.Interface.CommonCallBack;
import com.xmen.xteam.R;
import com.xmen.xteam.app.App;
import com.xmen.xteam.app.Common;
import com.xmen.xteam.communication.bean.BaseListSocketBean;
import com.xmen.xteam.communication.bean.BaseSocketBean;
import com.xmen.xteam.communication.bean.CallRequestBeanEvent;
import com.xmen.xteam.communication.bean.ContactInfoBeanDB;
import com.xmen.xteam.communication.bean.DelEmojiBean;
import com.xmen.xteam.communication.bean.EmojiBean;
import com.xmen.xteam.communication.bean.GroupInfoBean;
import com.xmen.xteam.communication.bean.MsgBean;
import com.xmen.xteam.communication.bean.MsgListBean;
import com.xmen.xteam.communication.bean.MsgReadBean;
import com.xmen.xteam.communication.bean.MultiCallBean;
import com.xmen.xteam.communication.bean.MultiCallUserBean;
import com.xmen.xteam.communication.bean.MultiCallVolumeBean;
import com.xmen.xteam.communication.bean.PCLoginStateBean;
import com.xmen.xteam.communication.bean.SPushMamberUpdate;
import com.xmen.xteam.communication.bean.SettingInfoArrBean;
import com.xmen.xteam.communication.bean.SettingInfoBean;
import com.xmen.xteam.communication.bean.SignallingBeanEvent;
import com.xmen.xteam.communication.bean.TeamInfoBeanDB;
import com.xmen.xteam.communication.bean.TeamOrGroupStateChangeBean;
import com.xmen.xteam.communication.bean.WholeUserInfoBean;
import com.xmen.xteam.control.CallControl;
import com.xmen.xteam.control.ChatDistribute;
import com.xmen.xteam.control.EmotionManage;
import com.xmen.xteam.control.PCLoginControl;
import com.xmen.xteam.control.XmenWebRtcSignalClinet;
import com.xmen.xteam.model.event.ChangeAppFrontStateEvent;
import com.xmen.xteam.model.event.EmotionUpdateEvent;
import com.xmen.xteam.model.event.EnterChatEvent;
import com.xmen.xteam.model.event.OnTeamMemberUpdataEvent;
import com.xmen.xteam.mvp.ui.activity.CallActivity;
import com.xmen.xteam.mvp.ui.activity.Login_1_2_MobileOrEmailActivity;
import com.xmen.xteam.mvp.ui.activity.Login_1_2_ResetPswActivity;
import com.xmen.xteam.mvp.ui.activity.MultiCallActivity;
import com.xmen.xteam.mvp.ui.activity.SplashActivity;
import com.xmen.xteam.mvp.ui.activity.TeamActivity;
import com.xmen.xteam.mvp.ui.widget.dialog.CommonDialog;
import com.xmen.xteam.utils.JsonUtils;
import com.xmen.xteam.utils.ResUtils;
import com.xmen.xteam.utils.RingtoneUtil;
import com.xmen.xteam.utils.ToastUtils;
import com.xmen.xteam.utils.notification.NotificationHelper;
import com.xmen.xteam.wcdb.bean.ChatDB;
import com.xmen.xteam.wcdb.bean.EmojiDB;
import com.xmen.xteam.wcdb.bean.GroupInfoDB;
import com.xmen.xteam.wcdb.bean.GroupMenberDB;
import com.xmen.xteam.wcdb.bean.SettingDB;
import com.xmen.xteam.wcdb.bean.TeamMemberDB;
import com.xmen.xteam.wcdb.bean.UserInfoDB;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import comxmen.emotion.model.CustomEmotionBean;
import comxmen.emotion.model.EmotionBean;

;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/24
 * 描    述： 服务器主推接口分发
 */
public class ReceivePush {
    private boolean mIsReceiveMsg = false;
    private CallRequestBeanEvent mCallRequestBean;
    private MultiCallBean mMultiCallRequestBean;

    /**
     * 当收到单条消息
     *
     * @param msgBean
     */
    public void onPushMsg(MsgBean msgBean) {
//        if (!mIsReceiveMsg) {
//                 ArrayList<MsgBean> beans = new ArrayList<>();
//        beans.add(msgBean);
//        ChatDistribute.getInstance().isNotification(beans);
//            return;
//        }
        if (msgBean.getMtp() == MsgBean.CONTENT_CALL_MSG) {
            Common.getI().getDBRxHelper().addExecQueue(ChatDB.getSqlInsertTempData(msgBean.getStp(), msgBean.getTid()), new ChatDB(msgBean)
                    , new CommonCallBack<Void>() {
                        @Override
                        public void callBack(Void aVoid) {
                            ChatDistribute.getI().refreshCallMsglist();
                        }
                    });
        }

        ArrayList<MsgBean> beans = new ArrayList<>();
        beans.add(msgBean);

        ChatDistribute.getI().isNotification(beans);
        ChatDistribute.getI().distribute(msgBean);
    }


    /**
     * 当收到消息数组
     *
     * @param msgListBean
     */
    public void onPushMsgList(MsgListBean msgListBean) {

        //过滤掉不是当前团队的消息
        if (!mIsReceiveMsg) {
            return;
        }
        ChatDistribute.getI().distribute(msgListBean);
    }


    private static ReceivePush instance;

    private ReceivePush() {
    }

    public static ReceivePush getI() {
        if (instance == null) {
            instance = new ReceivePush();
            EventBus.getDefault().register(instance);
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }

    public void setIsReceiveMsg(boolean b) {
        mIsReceiveMsg = b;
    }

    /**
     * 更新用户信息
     *
     * @param wholeUserInfoBean
     */
    public void pushUpdateUserInfo(BaseListSocketBean<WholeUserInfoBean> wholeUserInfoBean) {
        for (WholeUserInfoBean userInfoBean : wholeUserInfoBean.getList()) {
            int tid = userInfoBean.getTid();
            if (tid <= 0) {
                continue;
            }
            switch (userInfoBean.getOp()) {
                case 0: { //新增
                    UserInfoDB userInfoDB = userInfoBean.toUserInfoDB();
                    ContactInfoBeanDB newMember = new ContactInfoBeanDB(userInfoDB.getUid(), userInfoDB.getUname(), userInfoDB.getAvatar());
                    newMember.setStat(1);
                    //先存放在离开成员里面 然后通过112接口设置为正式成员
                    Common.getI().getLeaveMemberInfo().put(newMember.getUid(), newMember);
                    Common.getI().getDBRxHelper().execSQL(UserInfoDB.getSqlInsert(tid), userInfoDB);
                    break;
                }
                case 1: { //更新
                    UserInfoDB userInfoDB = userInfoBean.toUserInfoDB();
                    ContactInfoBeanDB contactInfoBeanDB = Common.getI().getContactInfo().get(userInfoBean.getUid());
                    if (contactInfoBeanDB == null) {
                        ContactInfoBeanDB newMember = new ContactInfoBeanDB(userInfoDB.getUid(), userInfoDB.getUname(), userInfoDB.getAvatar());
                        //如果没有拿到数据则表示该用户是新进的成员 先存放在离开成员里面 然后通过112接口设置为正式成员
                        Common.getI().addCacheContactInfoBean(newMember);
                    } else {
                        contactInfoBeanDB.setUname(userInfoBean.getNm());
                        contactInfoBeanDB.setAvatar(userInfoDB.getAvatar());
                    }
                    Common.getI().getDBRxHelper().execSQL(UserInfoDB.getSqlInsert(tid), userInfoDB);
                    if (userInfoBean.getUid() == Common.getI().getCurrentUserID()) {
                        Common.getI().setNewMyUserInfo(userInfoBean);
                    }
                    EventBus.getDefault().post(new OnTeamMemberUpdataEvent());
                    break;
                }
                case 2: {
                    break;
                }
            }
        }
    }

    /**
     * 更新团队成员信息
     *
     * @param sPushManberUpdate
     * @param tid
     */
    public void pushUpdateTeammemberInfo(List<SPushMamberUpdate> sPushManberUpdate, int tid) {
        final int[] sum = {0};
        for (SPushMamberUpdate pushManberUpdate : sPushManberUpdate) {
            //0表示新增成员
            if (pushManberUpdate.getStat() == 0) {
                Common.getI().getDBRxHelper().execSQL(TeamMemberDB.getSqlCrate(tid));
                Common.getI().getDBRxHelper().execSQL(TeamMemberDB.getSqlInsert(tid), new TeamMemberDB(pushManberUpdate.getUid(), pushManberUpdate.getRole(), pushManberUpdate.getStat(), pushManberUpdate.getCtm()));
                ContactInfoBeanDB contactInfoBeanDB = Common.getI().getLeaveMemberInfo().get(pushManberUpdate.getUid());
                if (contactInfoBeanDB != null) {
                    //从109进来的数据 删除掉
                    Common.getI().getLeaveMemberInfo().remove(pushManberUpdate.getUid());
                    //修改数据用户添加到正常成员的缓存
                    contactInfoBeanDB.setStat(0);
                    contactInfoBeanDB.setRole(pushManberUpdate.getRole());
                    contactInfoBeanDB.setStat(pushManberUpdate.getCtm());
                    Common.getI().addCacheContactInfoBean(contactInfoBeanDB);
                    EventBus.getDefault().post(new OnTeamMemberUpdataEvent());
                } else {
                    //从服务器拿用户信息的数据 通知更新
                    ProtocalPackage.getI().getUserInfo_174(Common.getI().getCurrentTeamID(), pushManberUpdate.getUid())
                            .setOnSucceedListener(userinfo -> {
                                if (userinfo.getList().size() > 0) {
                                    WholeUserInfoBean wholeUserInfoBean = userinfo.getList().get(0);
                                    ContactInfoBeanDB newMember = new ContactInfoBeanDB(wholeUserInfoBean.getUid(), wholeUserInfoBean.getNm(), wholeUserInfoBean.getAvt());
                                    newMember.setStat(pushManberUpdate.getStat());
                                    newMember.setRole(pushManberUpdate.getRole());
                                    newMember.setStat(pushManberUpdate.getCtm());
                                    Common.getI().addCacheContactInfoBean(newMember);
                                    if (pushManberUpdate.getUid() == Common.getI().getCurrentUserID()) {
                                        Common.getI().setNewMyUserInfo(wholeUserInfoBean);
                                    }
                                    EventBus.getDefault().post(new OnTeamMemberUpdataEvent());
                                }
                            })
                            .setOnFailureListener(failureCode -> {

                            })
                            .send();
                }

            } else if (pushManberUpdate.getStat() == 1) {
                //1 表示被提出成员
                if (pushManberUpdate.getUid() == Common.getI().getCurrentUserID()) {
                    int currentTeamID = Common.getI().getCurrentTeamID();
                    if (tid == currentTeamID) {
                        ThreadUtil.executeOnMain(() -> {
                            App.getApp().getTopActivity().startActivity(TeamActivity.class)
                                    .setAndAllFinish(CallActivity.class, MultiCallActivity.class)
                                    .start();
                        });
                    }
                    //如果是增加就退回到选择团队界面
                    Common.getI().quitTeam(tid);
                } else {
                    //删除数据库和内存的成员
                    Common.getI().getDBRxHelper().execSQL(TeamMemberDB.getSqlDelete(tid, pushManberUpdate.getUid()));
                    if (tid == Common.getI().getCurrentTeamID()) {
                        Common.getI().getContactInfo().remove(pushManberUpdate.getUid());
                        EventBus.getDefault().post(new OnTeamMemberUpdataEvent());
                    }

                }
            }
        }
    }


    /**
     * 当被踢出群 或者提出团队
     *
     * @param teamOrGroupStateChangeBean
     */
    public void onTeamOrGroupStateChange(TeamOrGroupStateChangeBean teamOrGroupStateChangeBean) {
        if (teamOrGroupStateChangeBean.getStatus() != 1) {
            return;
        }
        if (teamOrGroupStateChangeBean.getUid() != Common.getI().getCurrentUserID()) {
            return;
        }
        int tid = teamOrGroupStateChangeBean.getTid();
        int gid = teamOrGroupStateChangeBean.getGid();
        if (tid != 0 && gid != 0) {
            //如果是群被退出了 则修改数据库 和内存
            Common.getI().getDBRxHelper().execSQL(GroupInfoDB.getSqlUpdateState(tid, gid, 1));
            GroupInfoBean groupInfo = Common.getI().getGroupInfo(gid);
            if (groupInfo != null) {
                groupInfo.setStat(1);
            }
        } else if (tid != 0) {
            Common.getI().quitTeam(tid);
            //如果当前推动就是这个团队 则返回到团队选择界面
            if (tid == Common.getI().getCurrentTeamID()) {
                ToastUtils.ToastShort(ResUtils.getString(R.string.on_kicked_team));
                App.getApp().getTopActivity().startActivity(TeamActivity.class)
                        .setAndAllFinish(CallActivity.class, MultiCallActivity.class)
                        .start();
            }
        }
    }

    public void pushMsgRead(List<MsgReadBean> readBean) {
        ChatDistribute.getI().setMsgRead(readBean);
    }

    /**
     * 更新团队信息
     *
     * @param teamInfoBeanDB
     */
    public void pushUpdateTeamInfo(TeamInfoBeanDB teamInfoBeanDB) {
        if (teamInfoBeanDB.getOp() == 0) { //新增 需要红点提示
            Common.getI().getDBRxHelper().execSQL(TeamInfoBeanDB.getSqlInsert(), teamInfoBeanDB);
            Common.getI().putTeamInfo(teamInfoBeanDB);
            EventBus.getDefault().post(teamInfoBeanDB);
        } else if (teamInfoBeanDB.getOp() == 1) { //更新
            Common.getI().putTeamInfo(teamInfoBeanDB);
            Common.getI().getDBRxHelper().execSQL(TeamInfoBeanDB.getSqlInsert(), teamInfoBeanDB);
        } else if (teamInfoBeanDB.getOp() == 2) { //删除 如果当前聊天是当前团队的 则要退出该群
            Common.getI().removeTeamInfo(teamInfoBeanDB.getTid());
        }

    }


    /**
     * 当进入聊天界面的时候
     *
     * @param enterChatEvent
     */
    @Subscribe()
    public void onEventEnterChat(EnterChatEvent enterChatEvent) {
        ChatDistribute.getI().recordActivityEnterState(enterChatEvent);
    }

    /**
     * 前后台切换状态
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventChangeAppFrontState(ChangeAppFrontStateEvent changeAppFrontStateEvent) {
        ChatDistribute.getI().recordChangeAppFrontState(changeAppFrontStateEvent);
    }

    /**
     * 更新设置表信息；
     *
     * @param settingInfoBean
     */
    public void updataSettingTable(SettingInfoBean settingInfoBean) {
//        DBCacheUtils.setTidNotifi(settingInfoBean.getTid(), settingInfoBean.getTntc());
        List<SettingInfoArrBean> list = settingInfoBean.getList();
        List<SettingDB> setlist = new ArrayList<>();
        for (SettingInfoArrBean arrBean : list) {
            setlist.add(arrBean.toSettingDB());
            if (settingInfoBean.getTid() == Common.getI().getCurrentTeamID()) {
                Common.getI().putChatItemInfo(arrBean.getToid(), arrBean);
            }
        }

        Common.getI().getDBRxHelper().execSQLInTxB(SettingDB.getSqlInsert(settingInfoBean.getTid()), setlist);
    }


    //标记一下其他地方登录
    public boolean isOtherLogin = false;

    /**
     * 在其他地方登录
     *
     * @param baseSocketBean
     */
    public void onOtherLogin(BaseSocketBean baseSocketBean) {
        ThreadUtil.executeOnMain(() -> {
            if (baseSocketBean.getRes() == 23) {
                isOtherLogin = true;
                CommonDialog.newDialog(App.getApp().getTopActivity())
                        .setTitle(ResUtils.getString(R.string.title_hint))
                        .setOnClickBackNoOut()
                        .setContent(ResUtils.getString(R.string.gzhyjzqtdfdl))

                        .setPositive(ResUtils.getString(R.string.qd))
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                Common.getI().logout(App.getApp().getTopActivity(), false);
                                isOtherLogin = false;
                            }
                        }).show();
            }
        });
    }

    /**
     * 当群成员更新
     *
     * @param sPushManberUpdate
     * @param tid
     * @param gid
     */
    public void pushUpdateGroupMemberInfo(List<SPushMamberUpdate> sPushManberUpdate, int tid, int gid) {
        for (SPushMamberUpdate bean : sPushManberUpdate) {
            //0表示新增成员
            if (bean.getStat() == 0) {
                GroupMenberDB groupMenberDB = new GroupMenberDB(gid, bean.getUid(), bean.getStat(), bean.getCtm(), bean.getRole());
                Common.getI().getDBRxHelper().execSQL(GroupMenberDB.getSqlInsert(tid), groupMenberDB);
            } else if (bean.getStat() == 1) {

                if (bean.getUid() == Common.getI().getCurrentUserID()) {
//                    ChatDistribute.getInstance().addGroupChatLeaveHint(tid, gid);
                    ChatDistribute.getI().removeMsgList(tid, gid, 1);
                }

            }
        }
    }


    /**
     * 删除表情
     *
     * @param delEmojiBean
     */
    public void delEmoji(DelEmojiBean delEmojiBean) {
        ArrayList<Integer> delEmojis = delEmojiBean.getDelEmojis();

        for (Integer delEmoji : delEmojis) {
            Common.getI().getDBRxHelper().addExecQueue(EmojiDB.getSqlDelete(delEmoji));
            EventBus.getDefault().post(new EmotionUpdateEvent());
        }
        CustomEmotionBean collectionEmotion = EmotionManage.getI().getCollectionEmotion(App.getContext());
        List<EmotionBean> emotionPath = collectionEmotion.getEmotionPath();
        ListIterator<EmotionBean> emotionBeanListIterator = emotionPath.listIterator();

        while (emotionBeanListIterator.hasNext()) {
            EmotionBean next = emotionBeanListIterator.next();

            for (Integer delEmoji : delEmojis) {
                if (next.getId() == delEmoji) {
                    emotionBeanListIterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * 添加表情
     *
     * @param emojiDB
     */
    public void addEmoji(EmojiBean emojiDB) {


        Common.getI().getDBRxHelper().addExecQueue(EmojiDB.getSqlInsert(), emojiDB.toEmojiDB());

        CustomEmotionBean collectionEmotion = EmotionManage.getI().getCollectionEmotion(App.getContext());
        int emojiId = emojiDB.getEid();
        File collectionEmotionDir = new File(DirUtil.getCollectionMyEmotionDir(App.getContext()), emojiId + "");
        File file = new File(collectionEmotionDir, emojiId + "");

        EmotionBean emotionBean = new EmotionBean();
        emotionBean.setId(emojiId);
        emotionBean.setEmotionPath(file.getAbsolutePath());
        emotionBean.setUrl(JsonUtils.newJson(emojiDB.getParam()).getString("url"));
        LogX.e("seturl", JsonUtils.newJson(emojiDB.getParam()).getString("url"));
        emotionBean.setName("");
        emotionBean.setType(EmotionBean.TYPE_ITEM);
        collectionEmotion.getEmotionPath().add(emotionBean);
        EventBus.getDefault().post(new EmotionUpdateEvent());
    }

    public void dissolveGroup(Integer tid, Integer gid) {
        ChatDistribute.getI().addGroupChatLeaveHint(tid, gid);
        ChatDistribute.getI().removeMsgList(tid, gid, 1);
    }

    public void dissolveTeam(Integer tid) {
        int currentTeamID = Common.getI().getCurrentTeamID();
        if (tid == currentTeamID) {
            ThreadUtil.executeOnMain(() -> {
                App.getApp().getTopActivity().startActivity(TeamActivity.class)
                        .setAndAllFinish(CallActivity.class, MultiCallActivity.class)
                        .start();
            });
        }
        //如果是增加就退回到选择团队界面
        Common.getI().quitTeam(tid);
    }

    /**
     * PC的登陆状态改变
     *
     * @param pcLoginStateBean
     */
    public void pcStateChange(PCLoginStateBean pcLoginStateBean) {
        PCLoginControl.pcStateChange(pcLoginStateBean);
    }

    public void onCallRequest(CallRequestBeanEvent callRequestBean) {
//        mCallRequestBean = callRequestBean;

        setCallRequestBean(callRequestBean);
        ThreadUtil.executeOnMain(() -> {
            if (callRequestBean.getType() == 1 && callRequestBean.getOpt() == 1) {


                if (!CallControl.isRunCall()) {
                    if (App.getApp().getTopActivity() != null && App.getApp().getTopActivity().getClass() != SplashActivity.class) {
                        CallActivity.startCallActvity(App.getApp().getTopActivity(), false, callRequestBean.getUid()
                                , callRequestBean.getAvtype(), callRequestBean.getPf(), callRequestBean.getTid());
                    } else {

//                        ThreadUtil.executeOnMain(() -> {
//                            CallActivity.startCallActvity(App.getApp().getTopActivity(),false,callRequestBean.getUid()
//                                    ,callRequestBean.getAvtype(),callRequestBean.getPf(),callRequestBean.getTid());
//                        },2100);
                    }
                }
                if (ChatDistribute.getI().isChatLeave() && !CallControl.isRunCall()) {
                    NotificationHelper.getInstence().sendCallNotifaction(callRequestBean);
                }
            } else if (callRequestBean.getOpt() == 2) {
                setCallRequestBean(null);
                RingtoneUtil.getI().stopRingtonePlayer();
                if (CallControl.isRunCall()) {
                    EventBus.getDefault().post(callRequestBean);
                }
            } else {
                if (CallControl.isRunCall()) {
                    EventBus.getDefault().post(callRequestBean);
                } else {
                    if (callRequestBean.getOpt() == 7) {
                        ProtocalPackage.getI().call_request_263(callRequestBean.getAvtype(), callRequestBean.getTid(), callRequestBean.getUid(), 1, 5)
                                .setNotNetworkHnit()
                                .setDelayedRequestHnit()
                                .setOnSucceedListener(new OnSucceedListener<CallRequestBeanEvent>() {
                                    @Override
                                    public void onSucceed(CallRequestBeanEvent socketBean) {

                                    }
                                })
                                .setOnResultErrorListener(new OnResultErrorListener() {
                                    @Override
                                    public void onResultError(BaseSocketBean socketBean, int code, String errorStr) {

                                    }
                                })
                                .send();
                    }
                }
            }
        });
    }

    public void onSignallingEvent(SignallingBeanEvent signallingEventBean) {
        XmenWebRtcSignalClinet signalClinet = XmenWebRtcSignalClinet.getAttemptInstance();
        if (signalClinet != null) {
            ThreadUtil.executeOnMain(new Runnable() {
                @Override
                public void run() {
                    signalClinet.getOnSignallingEventListener(signallingEventBean);
                }
            });
        }
    }

    public CallRequestBeanEvent getCallRequestBean() {
        return mCallRequestBean;
    }

    public void setCallRequestBean(CallRequestBeanEvent callRequestBean) {
        mMultiCallRequestBean = null;
        mCallRequestBean = callRequestBean;
    }

    public MultiCallBean getMultiCallRequestBean() {
        return mMultiCallRequestBean;
    }

    public void setMultiCallRequestBean(MultiCallBean multiCallBean) {
        int compereUid = multiCallBean.getUid();

        //把主持人加入列表
        boolean compereIsExist = false;
        for (MultiCallUserBean multiCallUserBean : multiCallBean.getUinfo()) {
            if (multiCallUserBean.getUid() == compereUid) {
                compereIsExist = true;
            }
        }
        if (!compereIsExist) {
            MultiCallUserBean multiCallUserBean = new MultiCallUserBean(multiCallBean.getUid());
            multiCallUserBean.setStat(0);
            multiCallBean.getUinfo().add(0, multiCallUserBean);
        }
        mCallRequestBean = null;
        mMultiCallRequestBean = multiCallBean;
    }

    /**
     * 服务器推送客户端的团队用户语音视频会议请求
     *
     * @param multiCallBean
     */
    public void onMultiCallBean(MultiCallBean multiCallBean) {


        // 当被人邀请
        if ((multiCallBean.getOpt() == 1 || multiCallBean.getOpt() == 4) && multiCallBean.getUid() != Common.getI().getCurrentUserID()) {

            //如果已经正在运行了就忽略
            if (CallControl.isRunCall()) {
                if (MultiCallActivity.isRun() && multiCallBean.getCid() == MultiCallActivity.getCid()) {
                    EventBus.getDefault().post(multiCallBean);
                }
                return;
            }
            //遍历邀请列表里面有没有包含自己
            for (MultiCallUserBean multiCallUserBean : multiCallBean.getUinfo()) {
                if (multiCallUserBean.getUid() == Common.getI().getCurrentUserID()) {
                    //如果列表里面有自己代表是邀请自己
                    setMultiCallRequestBean(multiCallBean);
                    MultiCallActivity.answerCall(App.getApp().getTopActivity(), multiCallBean);
                    NotificationHelper.getInstence().sendMultiCallNotifaction(multiCallBean);
                    break;
                }
            }
        } else if (multiCallBean.getOpt() == 12) {
            //断开以后 上线重连逻辑
            //有在运行的 就不处理了
            if (CallControl.isRunCall()) {
                if (MultiCallActivity.isRun() && multiCallBean.getCid() == MultiCallActivity.getCid()) {
                    EventBus.getDefault().post(multiCallBean);
                }
                return;
            }
            if (App.getApp().getTopActivity() instanceof SplashActivity
                    || App.getApp().getTopActivity() instanceof TeamActivity
                    || App.getApp().getTopActivity() instanceof Login_1_2_MobileOrEmailActivity
                    || App.getApp().getTopActivity() instanceof Login_1_2_ResetPswActivity
                    ) {
                LogX.e("■275aaaa", App.getApp().getTopActivity().getClass());
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (App.getApp().getTopActivity() instanceof SplashActivity
                                || App.getApp().getTopActivity() instanceof TeamActivity
                                || App.getApp().getTopActivity() instanceof Login_1_2_MobileOrEmailActivity
                                || App.getApp().getTopActivity() instanceof Login_1_2_ResetPswActivity) {
                            SystemClock.sleep(2000);
                        }
                        LogX.e("■275ccccc", App.getApp().getTopActivity().getClass());
                        ThreadUtil.executeOnMain(new Runnable() {
                            @Override
                            public void run() {
                                MultiCallActivity.reconnectCall(App.getApp().getTopActivity(), multiCallBean);
                            }
                        });
                    }
                }, 1000);
            } else {
                LogX.e("■275bbbb", App.getApp().getTopActivity().getClass());
                ThreadUtil.executeOnMain(new Runnable() {
                    @Override
                    public void run() {
                        MultiCallActivity.reconnectCall(App.getApp().getTopActivity(), multiCallBean);
                    }
                });
            }
        } else {
            LogX.e("post multiCallBean");


            //有在运行的 就不处理了
            if (MultiCallActivity.isRun() && multiCallBean.getCid() == MultiCallActivity.getCid()) {
                EventBus.getDefault().post(multiCallBean);
            }

        }


    }

    /**
     * @param multiCallVolumeBean 服务器推送即时语音信号（278）
     */
    public void onMultiVolumeBean(MultiCallVolumeBean multiCallVolumeBean) {
        EventBus.getDefault().post(multiCallVolumeBean);
    }


}
