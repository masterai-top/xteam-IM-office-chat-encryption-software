package com.xmen.xteam.communication;

import com.common.lib.log.LogX;
import com.xmen.xteam.communication.bean.BackSettingBean;
import com.xmen.xteam.communication.bean.BaseListSocketBean;
import com.xmen.xteam.communication.bean.BaseListSubpackageSocketBean;
import com.xmen.xteam.communication.bean.BaseSocketBean;
import com.xmen.xteam.communication.bean.CallRequestBeanEvent;
import com.xmen.xteam.communication.bean.CollectBean;
import com.xmen.xteam.communication.bean.ContactInfoBeanDB;
import com.xmen.xteam.communication.bean.CountryCodeBean;
import com.xmen.xteam.communication.bean.CreateGroupInfoBean;
import com.xmen.xteam.communication.bean.CreateTeamBean;
import com.xmen.xteam.communication.bean.DelEmojiBean;
import com.xmen.xteam.communication.bean.DownloadFileSTBean;
import com.xmen.xteam.communication.bean.EmojiBean;
import com.xmen.xteam.communication.bean.GetAllTeamInfosBean;
import com.xmen.xteam.communication.bean.GroupInfoBean;
import com.xmen.xteam.communication.bean.JoinTeamBean;
import com.xmen.xteam.communication.bean.MsgBean;
import com.xmen.xteam.communication.bean.MsgListBean;
import com.xmen.xteam.communication.bean.MsgReadBean;
import com.xmen.xteam.communication.bean.MultiCallBean;
import com.xmen.xteam.communication.bean.MultiCallVolumeBean;
import com.xmen.xteam.communication.bean.NewTeamGroupMenberInfoBean;
import com.xmen.xteam.communication.bean.NewUploadFileBean;
import com.xmen.xteam.communication.bean.OnLineStateBean;
import com.xmen.xteam.communication.bean.OseersBean;
import com.xmen.xteam.communication.bean.PCLoginStateBean;
import com.xmen.xteam.communication.bean.PhoneAndEmailBean;
import com.xmen.xteam.communication.bean.QRCodeBean;
import com.xmen.xteam.communication.bean.QrcodeRequestTidBean;
import com.xmen.xteam.communication.bean.RobotTokenBean;
import com.xmen.xteam.communication.bean.SPushMamberUpdate;
import com.xmen.xteam.communication.bean.SafetyPictureBean;
import com.xmen.xteam.communication.bean.SettingInfoBean;
import com.xmen.xteam.communication.bean.ShareMediaBean;
import com.xmen.xteam.communication.bean.ShareSearchFilesBean;
import com.xmen.xteam.communication.bean.SignallingBeanEvent;
import com.xmen.xteam.communication.bean.SubpackageListBean;
import com.xmen.xteam.communication.bean.TeamDynamicOrderBean;
import com.xmen.xteam.communication.bean.TeamInfoBeanDB;
import com.xmen.xteam.communication.bean.TeamOrGroupStateChangeBean;
import com.xmen.xteam.communication.bean.UpdateAppBean;
import com.xmen.xteam.communication.bean.UploadFileDataBean;
import com.xmen.xteam.communication.bean.UserInfoBean;
import com.xmen.xteam.communication.bean.UserOnlineConfigBean;
import com.xmen.xteam.communication.bean.UserProtectBean;
import com.xmen.xteam.communication.bean.VerifyUserProtectBean;
import com.xmen.xteam.communication.bean.WholeUserInfoBean;
import com.xmen.xteam.communication.bean.XListTaskBean;
import com.xmen.xteam.communication.bean.XListTaskResultBean;
import com.xmen.xteam.communication.fileSockey.FileSocket;
import com.xmen.xteam.model.event.GetMsgEvent;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.service.MsgSocketService;
import com.xmen.xteam.utils.DBCacheUtils;
import com.xmen.xteam.utils.TimeUtils;
import com.xmen.xteam.wcdb.bean.MsgListDB;

import org.greenrobot.eventbus.EventBus;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ArrayValue;
import org.msgpack.value.FloatValue;
import org.msgpack.value.IntegerValue;
import org.msgpack.value.MapValue;
import org.msgpack.value.Value;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创 建 者:  lwh
 * 创建时间:  2017/12/16
 * 描    述：
 */
public class ProtocolParse {


    static void classify(ReceiveProtocol receiveProtocol) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receiveProtocol.getBody());
        MessageUnpacker messageUnpacker = null;
//         LogX.e("■收到body大小■", receiveProtocol.getBody().length);
        messageUnpacker = MessagePack.newDefaultUnpacker(receiveProtocol.getBody());
        HashMap<String, Object> bodyMap = new HashMap<>();
        int size = messageUnpacker.unpackMapHeader();
        for (int i = 0; i < size; i++) {
            String key = messageUnpacker.unpackString();
            Object o = parseValue(messageUnpacker.unpackValue());
            bodyMap.put(key, o);
        }
        LogX.iT("■收到■" + receiveProtocol.getCmd() + "■" + receiveProtocol + "==" + bodyMap, 0);


        switch (receiveProtocol.getCmd()) {
            case 5: {
                String msg = (String) bodyMap.get("MSG");
                GetMsgEvent getMsgEvent = new GetMsgEvent();
//                getMsgEvent.setUid((int) enterChatEvent.getUid());
                getMsgEvent.setTime(TimeUtils.getServiceTime());
                getMsgEvent.setContentCategory(MsgBean.CONTENT_TYPE_TEXT);
                getMsgEvent.setItemType(MsgBean.ITEM_TYPE_LEFT);
                getMsgEvent.setText(msg);
                getMsgEvent.setNickname("昵称" + 0);
                EventBus.getDefault().post(getMsgEvent);
                break;
            }

            case CMD.RESPONSE_LOGIN_2:
            case CMD.RESPONSE_RESET_PASSWORD_10:
            case CMD.RESPONSE_REGISTER_4: {
                MsgSocketService.responseData(new UserInfoBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.REQUEST_COUNTRY_CODE_RESULT_12:
            case CMD.REQUEST_COUNTRY_CODE_RESULT_181: {
                MsgSocketService.responseData(new CountryCodeBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_ICON_VERIFY_15: {
                MsgSocketService.responseData(new SafetyPictureBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_VERIFY_USER_PROTECT_20: {
                MsgSocketService.responseData(new VerifyUserProtectBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_TEAMS_INFO_51: {
                MsgSocketService.responseData(new GetAllTeamInfosBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_TEAM_CREATE_59: {
                MsgSocketService.responseData(new CreateTeamBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_TEAM_GET_INFO_65: {
                MsgSocketService.responseData(new TeamInfoBeanDB(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_TEAM_JOIN_67: {
                MsgSocketService.responseData(new JoinTeamBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_REFRESH_TEAM_INFO_53: {
                MsgSocketService.responseData(new NewTeamGroupMenberInfoBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_TEAM_GROUP_INFO_103: {
                MsgSocketService.responseData(new BaseListSocketBean(bodyMap, receiveProtocol, "ginfo", GroupInfoBean.class));
                break;
            }
            case CMD.RESPONSE_GET_TEAM_USER_INFO_105: {
                MsgSocketService.responseData(new BaseListSocketBean(bodyMap, receiveProtocol, "uinfo", ContactInfoBeanDB.class));
                break;
            }
            case CMD.RESPONSE_GET_USER_INFO_79: {
                MsgSocketService.responseData(new BaseListSocketBean(bodyMap, receiveProtocol, "uinfo", WholeUserInfoBean.class));
                break;
            }
            case CMD.RESPONSE_GET_USER_INFO_175: {
                BaseListSocketBean uinfo = new BaseListSocketBean(bodyMap, receiveProtocol, "uinfo", WholeUserInfoBean.class);
                for (Object o : uinfo.getList()) {
                    WholeUserInfoBean infoBean = (WholeUserInfoBean) o;
                    infoBean.setTid(uinfo.getTid());
                }
                MsgSocketService.responseData(uinfo);
                break;
            }
            case CMD.RESPONSE_GET_GROUP_USER_INFO_107: {
                MsgSocketService.responseData(new BaseListSocketBean(bodyMap, receiveProtocol, "uinfo", ContactInfoBeanDB.class));
                break;
            }
            case CMD.RESPONSE_SEND_MSG_57: {
                MsgSocketService.responseData(new MsgBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_CREATE_GROUP_85: {
                MsgSocketService.responseData(new CreateGroupInfoBean(bodyMap, receiveProtocol));
                break;
            }
//            case CMD.RESPONSE_CHANGE_USER_INFO_101: {
//                MsgSocketService.responseData(new WholeUserInfoBean(bodyMap, receiveProtocol));
//                break;
//            }
            case CMD.RESPONSE_CHANGE_USER_INFO_177: {
                MsgSocketService.responseData(new WholeUserInfoBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_NEW_DIALOGUE_INFO_55: {
                //当发送完最新会话以后才处理接收到的消息
                ReceivePush.getI().setIsReceiveMsg(true);
                MsgSocketService.responseData(new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {

                    }
                });

                break;
            }
            case CMD.RESPONSE_VERIFY_TOKEN_75: {
                FileSocket.responseData(new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                    }
                });
                MsgSocketService.responseData(new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                    }
                });
                break;
            }
            case CMD.RESPONSE_UPLOAD_FILE_REQUEST_1101: {
                FileSocket.responseData(new NewUploadFileBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_UPLOAD_FILE_DATA_1103: {
                FileSocket.responseData(new UploadFileDataBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_UPLOAD_FILE_CANCEL_1107: {
                FileSocket.responseData(new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {

                    }
                });
                break;
            }
            case CMD.S_TO_PUSH_MSG_LIST_ARRIVE_80: {
                MsgListBean msgListBean = new MsgListBean(bodyMap, receiveProtocol);
                ReceivePush.getI().onPushMsgList(msgListBean);
                break;
            }
            case CMD.S_TO_PUSH_MSG_ARRIVE_81: {
                MsgBean msgBean = new MsgBean(bodyMap, receiveProtocol);
                ReceivePush.getI().onPushMsg(msgBean);
                break;
            }
            case CMD.S_TO_PUSH_MSG_READ_INFO_108: {
                BaseListSocketBean<MsgReadBean> readBeans = new BaseListSocketBean<MsgReadBean>(bodyMap, receiveProtocol, "rdinfo", MsgReadBean.class) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                        super.parse(receiveProtocol, preseq);
                        int tid = (int) receiveProtocol.get("tid");

                        List<MsgReadBean> list = getList();
                        Object o = receiveProtocol.get("uid");
                        int uid = 0;
                        if (o != null) {
                            uid = (int) o;
                        }
                        for (MsgReadBean msgReadBean : list) {
                            msgReadBean.setTid(tid);
                            msgReadBean.setUid(uid);
                        }
                        ReceivePush.getI().pushMsgRead(list);
                    }
                };
                break;
            }
            case CMD.S_TO_PUSH_TEAM_USERINFO_178:
            case CMD.S_TO_PUSH_USER_INFO_109: {
                BaseListSocketBean uinfo = new BaseListSocketBean(bodyMap, receiveProtocol, "uinfo", WholeUserInfoBean.class);
                for (Object o : uinfo.getList()) {
                    WholeUserInfoBean infoBean = (WholeUserInfoBean) o;
                    infoBean.setTid(uinfo.getTid());
                }
                ReceivePush.getI().pushUpdateUserInfo(uinfo);
                break;
            }
            case CMD.S_TO_PUSH_OTHER_LOGIN_1002: {
                ReceivePush.getI().onOtherLogin(new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                    }
                });
                break;
            }
            case CMD.S_TO_PUSH_PC_STATE_214: {
                PCLoginStateBean pcLoginStateBean = new PCLoginStateBean(bodyMap, receiveProtocol);
                ReceivePush.getI().pcStateChange(pcLoginStateBean);
                break;
            }
            case CMD.S_TO_PUSH_TEAM_INFO_110: {
                TeamInfoBeanDB teamInfoBeanDB = new TeamInfoBeanDB(bodyMap, receiveProtocol);
                ReceivePush.getI().pushUpdateTeamInfo(teamInfoBeanDB);
                break;
            }
            case CMD.S_TO_PUSH_GROUP_INFO_111: {

                break;
            }
            case CMD.S_TO_PUSH_DISSOLVE_GROUP_193: {
                new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                        Object tid = receiveProtocol.get("tid");
                        Object gid = receiveProtocol.get("gid");
                        if (tid != null && gid != null ) {
                            ReceivePush.getI().dissolveGroup((Integer)tid,(Integer)gid);
                        }
                    }
                };

                break;
            }
//            case CMD.S_TO_PUSH_DISSOLVE_TEAM_196: {
//                new BaseSocketBean(bodyMap, receiveProtocol) {
//                    @Override
//                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
//                        Object tid = receiveProtocol.get("tid");
//                        if (tid != null  ) {
//                            ReceivePush.getInstance().dissolveTeam((Integer)tid);
//                        }
//                    }
//                };
//
//                break;
//            }
            case CMD.S_TO_PUSH_TEAM_MEMBER_STATE_112: {
                new BaseListSocketBean<SPushMamberUpdate>(bodyMap, receiveProtocol, "uinfo", SPushMamberUpdate.class) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                        super.parse(receiveProtocol, preseq);
                        Object tid = receiveProtocol.get("tid");
                        ReceivePush.getI().pushUpdateTeammemberInfo(getList(), (int) tid);
                    }
                };
                break;
            }
            case CMD.S_TO_PUSH_GROUP_MEMBER_STATE_113: {
                new BaseListSocketBean<SPushMamberUpdate>(bodyMap, receiveProtocol, "uinfo", SPushMamberUpdate.class) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                        super.parse(receiveProtocol, preseq);
                        Object tid = receiveProtocol.get("tid");
                        Object gid = receiveProtocol.get("gid");
                        ReceivePush.getI().pushUpdateGroupMemberInfo(getList(), (int) tid, (int) gid);
                    }
                };
                break;
            }
            case CMD.S_TO_PUSH_TEAM_OR_GROUP_STATE_CHANGE_133: {
                TeamOrGroupStateChangeBean teamOrGroupStateChangeBean = new TeamOrGroupStateChangeBean(bodyMap, receiveProtocol);
                ReceivePush.getI().onTeamOrGroupStateChange(teamOrGroupStateChangeBean);
            }
            case CMD.S_TO_PUSH_GROUP_MEMBER_STATE_114: {
                SettingInfoBean settingInfoBean = new SettingInfoBean(bodyMap, receiveProtocol, "sscinfo");
                ReceivePush.getI().updataSettingTable(settingInfoBean);
                break;
            }
            case CMD.S_TO_PUSH_DEL_EMOTION_163: {
                DelEmojiBean delEmojiBean = new DelEmojiBean(bodyMap, receiveProtocol);
                ReceivePush.getI().delEmoji(delEmojiBean);
                break;
            }

            case CMD.S_TO_PUSH_ADD_EMOTION_162: {
                EmojiBean emojiBean = new EmojiBean(bodyMap, receiveProtocol);
                ReceivePush.getI().addEmoji(emojiBean);
                break;
            }
            case CMD.RESPONSE_SET_CONFIG_116: {
                MsgSocketService.responseData(new BackSettingBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.ANSWER_SHARE_MEDIA_PICTURE_128: {
                MsgSocketService.responseData(new ShareMediaBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESULT_SEARCH_SHARE_FILES_130: {
                MsgSocketService.responseData(new ShareSearchFilesBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESULT_HISTORY_CHAT_DATA_135: {
                MsgSocketService.responseData(new MsgListBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESULT_UPDATE_APP_143: {
                MsgSocketService.responseData(new UpdateAppBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESULT_GET_QRCODE_147: {
                MsgSocketService.responseData(new QRCodeBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESULT_QRCODE_REQUEST_TID_149: {
                MsgSocketService.responseData(new QrcodeRequestTidBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESULT_GET_EMOTION_157: {
                MsgSocketService.responseData(new SubpackageListBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.PAGE_GET_EMOTION_182: {
                MsgSocketService.responseData(new BaseListSubpackageSocketBean<EmojiBean>(bodyMap,receiveProtocol,"einfo", EmojiBean.class));
                break;
            }
            case CMD.RESULT_GET_USER_PHONE_EMAIL_184: {
                MsgSocketService.responseData(new BaseListSocketBean<>(bodyMap,receiveProtocol,"uinfo", PhoneAndEmailBean.class));
                break;
            }
            case CMD.RESULT_ADD_EMOTION__159: {
                MsgSocketService.responseData(new EmojiBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESULT_DEL_EMOTION_161: {
                MsgSocketService.responseData(new DelEmojiBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESULT_GET_TEAM_USER_INFO_168: {
                MsgSocketService.responseData(new SubpackageListBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.PAGE_GET_TEAM_USER_INFO_169: {
                MsgSocketService.responseData(new NewTeamGroupMenberInfoBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_SYNCHRONIZE_COLLECT_190: {
                MsgSocketService.responseData(new BaseListSocketBean<>(bodyMap,receiveProtocol,"cinfo", CollectBean.class));
                break;
            }
            case CMD.RESULT_NEW_DIALOGUE_INFO_165: {
                MsgSocketService.responseData(new SubpackageListBean(bodyMap, receiveProtocol){
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
                        super.parse(receiveProtocol, preseq);
                        Object tntc = receiveProtocol.get("tntc");
                        Object tid = receiveProtocol.get("tid");
                        if (tntc != null && tid != null) {
                            DBCacheUtils.setTidNotifi((int)tid,(int)tntc);
                        }
                    }
                });
                break;
            }
            case CMD.PAGE_NEW_DIALOGUE_INFO_166: {
                SettingInfoBean settingInfoBean = new SettingInfoBean(bodyMap, receiveProtocol, "minfo");
                LogX.e(settingInfoBean);
                ReceivePush.getI().updataSettingTable(settingInfoBean);

                BaseListSubpackageSocketBean minfo = new BaseListSubpackageSocketBean(bodyMap, receiveProtocol, "minfo", MsgListDB.class);
                MsgSocketService.responseData(minfo);
//                List<MsgListDB> list = minfo.getList();
//                ChatDistribute.getInstance().updateToMsglist(minfo.getTid(),list);
                break;
            }
            case CMD.RESPONSE_GET_CHAT_MSG_173: {
                MsgSocketService.responseData(new SubpackageListBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_USER_PROTECT_201: {
                MsgSocketService.responseData(new UserProtectBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_DOWNLOAD_FILE_TIMESTAMP_213: {
                MsgSocketService.responseData(new DownloadFileSTBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_SET_ONLINE_STATE_240: {
                MsgSocketService.responseData(new OnLineStateBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_SET_USER_ONLINE_CONFIG_245:
            case CMD.RESPONSE_GET_USER_ONLINE_CONFIG_247: {
                MsgSocketService.responseData(new UserOnlineConfigBean(bodyMap, receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_ONLINE_STATE_242: {
                MsgSocketService.responseData(new SubpackageListBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_XLIST_TASK_252: {
                MsgSocketService.responseData(new SubpackageListBean(bodyMap,receiveProtocol));
                break;
            }

            case CMD.RESPONSE_CGET_ROBOT_TOKEN_234: {
                RobotTokenBean robotTokenBean = new RobotTokenBean(bodyMap, receiveProtocol);
                MsgSocketService.responseData(robotTokenBean);
                break;
            }
            case CMD.PAGE_GET_ONLINE_STATE_243: {
                MsgSocketService.responseData(new BaseListSubpackageSocketBean<OnLineStateBean>(bodyMap,receiveProtocol,"sinfo", OnLineStateBean.class));
                break;
            }
            case CMD.PAGE_GET_XLIST_TASK_253: {
                MsgSocketService.responseData(new BaseListSubpackageSocketBean(bodyMap,receiveProtocol,"tkinfo", XListTaskBean.class));
                break;
            }
            case CMD.RESPONSE_XLIST_TASK_250: {
                MsgSocketService.responseData(new XListTaskResultBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_OSEERS_257: {
                MsgSocketService.responseData(new OseersBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_ADD_OR_DEL_OSEERS_255: {
                MsgSocketService.responseData(new OseersBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_DYNAMIC_ORDER_262: {
                MsgSocketService.responseData(new TeamDynamicOrderBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_CALL_REQUEST_264: {
                MsgSocketService.responseData(new CallRequestBeanEvent(bodyMap,receiveProtocol));
                break;
            }
            case CMD.RESPONSE_GET_ALL_MULTI_CALL_USER_282:
            case CMD.RESPONSE_MULTI_CALL_274: {
                MsgSocketService.responseData(new MultiCallBean(bodyMap,receiveProtocol));
                break;
            }
            case CMD.S_TO_MULTI_CALL_275: {
                MultiCallBean multiCallBean = new MultiCallBean(bodyMap, receiveProtocol);
                ReceivePush.getI().onMultiCallBean(multiCallBean);
                break;
            }
            case CMD.S_TO_MULTI_CALL_VOLUME_278: {
                MultiCallVolumeBean multiCallVolumeBean = new MultiCallVolumeBean(bodyMap, receiveProtocol);
                ReceivePush.getI().onMultiVolumeBean(multiCallVolumeBean);
                break;
            }
            case CMD.S_TO_SIGNALLING_268: {
                SignallingBeanEvent signallingEventBean = new SignallingBeanEvent(bodyMap, receiveProtocol);
                ReceivePush.getI().onSignallingEvent(signallingEventBean);
                break;
            }
            case CMD.S_TO_REQUEST_CALL_265: {
                CallRequestBeanEvent callRequestBean = new CallRequestBeanEvent(bodyMap, receiveProtocol);
                ReceivePush.getI().onCallRequest(callRequestBean);
                break;
            }
            case CMD.PAGE_CHAT_MSG_179: {
                BaseListSubpackageSocketBean<MsgBean> minfo = new BaseListSubpackageSocketBean<MsgBean>(bodyMap, receiveProtocol, "minfo", MsgBean.class){
                    @Override
                    protected void parse(HashMap receiveProtocol, ReceiveProtocol preseq) {
                        super.parse(receiveProtocol, preseq);
                        int  stp = (int) receiveProtocol.get("stp");
                        for (MsgBean msgBean : this.getList()) {
                            msgBean.setTid(this.getTid());
                            msgBean.setStp(stp);
                        }
                    }
                };

                MsgSocketService.responseData(minfo);
//                List<MsgListDB> list = minfo.getList();
//                ChatDistribute.getInstance().updateToMsglist(minfo.getTid(),list);
                break;
            }
            default: {
                MsgSocketService.responseData(new BaseSocketBean(bodyMap, receiveProtocol) {
                    @Override
                    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {

                    }
                });
            }
        }
    }

    private static Object parseValue(Value v) {
        switch (v.getValueType()) {
            case NIL:
                v.isNilValue(); // true
                break;
            case BOOLEAN:
                return v.asBooleanValue().getBoolean();
            case INTEGER:
                IntegerValue iv = v.asIntegerValue();
                if (iv.isInIntRange()) {
                    return iv.toInt();
                } else if (iv.isInLongRange()) {
                    return iv.toLong();
                } else {
                    return iv.toBigInteger();
                }
            case FLOAT:
                FloatValue fv = v.asFloatValue();
                if (fv.isFloatValue()) {
                    return fv.toFloat();
                } else {
                    return fv.toDouble();
                }
            case STRING:
                return v.asStringValue().asString();
            case BINARY:
                byte[] mb = v.asBinaryValue().asByteArray();
                return mb;
            case ARRAY:
                ArrayValue a = v.asArrayValue();
                List<Object> objects = new ArrayList<>();
                for (Value e : a) {
                    objects.add(parseValue(e));
                }
                return objects;
            case EXTENSION:
//                ExtensionValue ev = v.asExtensionValue();
//                byte extType = ev.getType();
//                byte[] extValue = ev.getData();
                break;
            case MAP:
                MapValue mapValue = v.asMapValue();
                Map<Value, Value> map = mapValue.map();
//                    map.get()
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();

                for (Value key : map.keySet()) {
                    Value value = map.get(key);
                    objectObjectHashMap.put(parseValue(key), parseValue(value));
                }
                return objectObjectHashMap;
        }
        return null;
    }
}
