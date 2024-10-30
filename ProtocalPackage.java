package com.xmen.xteam.communication;

import android.annotation.SuppressLint;

import com.common.lib.log.LogX;
import com.common.lib.util.SettingUtil;
import com.vdurmont.emoji.EmojiParser;
import com.xmen.xteam.app.App;
import com.xmen.xteam.app.Common;
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
import com.xmen.xteam.communication.bean.MultiCallBean;
import com.xmen.xteam.communication.bean.MultiCallRequestBean;
import com.xmen.xteam.communication.bean.NewTeamGroupMenberInfoBean;
import com.xmen.xteam.communication.bean.NewUploadFileBean;
import com.xmen.xteam.communication.bean.OnLineStateBean;
import com.xmen.xteam.communication.bean.OseersBean;
import com.xmen.xteam.communication.bean.PhoneAndEmailBean;
import com.xmen.xteam.communication.bean.QRCodeBean;
import com.xmen.xteam.communication.bean.QrcodeRequestTidBean;
import com.xmen.xteam.communication.bean.RobotTokenBean;
import com.xmen.xteam.communication.bean.SafetyPictureBean;
import com.xmen.xteam.communication.bean.SettingInfoArrBean;
import com.xmen.xteam.communication.bean.ShareMediaBean;
import com.xmen.xteam.communication.bean.ShareSearchFilesBean;
import com.xmen.xteam.communication.bean.TeamDynamicOrderBean;
import com.xmen.xteam.communication.bean.TeamInfoBeanDB;
import com.xmen.xteam.communication.bean.UpdateAppBean;
import com.xmen.xteam.communication.bean.UploadFileDataBean;
import com.xmen.xteam.communication.bean.UserInfoBean;
import com.xmen.xteam.communication.bean.UserOnlineConfigBean;
import com.xmen.xteam.communication.bean.UserProtectBean;
import com.xmen.xteam.communication.bean.VerifyUserProtectBean;
import com.xmen.xteam.communication.bean.WholeUserInfoBean;
import com.xmen.xteam.communication.bean.XListTaskBean;
import com.xmen.xteam.communication.bean.XListTaskResultBean;
import com.xmen.xteam.control.ChatDistribute;
import com.xmen.xteam.model.event.OnReceptionMsgEvent;
import com.xmen.xteam.msgsockey.socket.adapter.SendProtocol;
import com.xmen.xteam.utils.DeviceInfoUtil;
import com.xmen.xteam.utils.JsonUtils;
import com.xmen.xteam.utils.StringUtil;
import com.xmen.xteam.wcdb.bean.MsgListDB;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 创 建 者:  lwh
 * 创建时间:  2017/12/16
 * 描    述：
 */
public class ProtocalPackage {

    @SuppressLint("MissingPermission")
    public NewSendBuilder<UserInfoBean> login_1(String pid, String password, int type, int code) {

        return login_1(pid, password, type, code, "", "");
    }

    @SuppressLint("MissingPermission")
    public NewSendBuilder<UserInfoBean> login_1(String pid, String password, int type) {

        return login_1(pid, password, type, 0, "", "");
    }

    @SuppressLint("MissingPermission")
    public NewSendBuilder<UserInfoBean> login_1(String pid, String password, int type, String imgCode) {
        return login_1(pid, password, type, 0, imgCode, "");
    }


    @SuppressLint("MissingPermission")
    public NewSendBuilder<UserInfoBean> login_1(String pid, String password, int type, int code, String imgcode, String ggcode) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        if (imgcode == null) {
            imgcode = "";
        }
        int updated = 0;
        if (ggcode == null) {
            ggcode = "";
        } else {
            updated = (int) (System.currentTimeMillis() / 1000);
        }
        //
        ArrayList<HashMap<Object, Object>> deviceInfo = DeviceInfoUtil.getDeviceInfo();

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("psw", password)//用户密码
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("pf", 3) //平台类型 1:PC 2:移动端
                .pack("imgcode", imgcode) //平台类型 1:PC 2:移动端
                .pack("ggcode", ggcode) //平台类型 1:PC 2:移动端
                .pack("devinfo", deviceInfo) //平台类型 1:PC 2:移动端
                .pack("code", code) //平台类型 1:PC 2:移动端
                .pack("updated", updated) //平台类型 1:PC 2:移动端
                .build()
                .setCmd((short) CMD.LOGIN_1);
        return NewIMSocket.Builder(protocol);
    }


    public NewSendBuilder<UserInfoBean> register_3(String pid, String password, int type, int code) {
        ArrayList<HashMap<Object, Object>> deviceInfo = DeviceInfoUtil.getDeviceInfo();
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("psw", password)//用户密码
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("code", code) //验证码
                .pack("pf", 3) //平台类型 1:PC 2:移动端
                .pack("devinfo", deviceInfo) //平台类型 1:PC 2:移动端
                .build()
                .setCmd((short) CMD.REGISTER_3);
        return NewIMSocket.Builder(protocol);
    }

    public NewSendBuilder<UserInfoBean> resetPassword_9(String pid, String password, int type, int code) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("psw", password)//用户新密码
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("code", code) //验证码
                .pack("pf", 3) //平台类型 1:PC 2:移动端
                .build()
                .setCmd((short) CMD.RESET_PASSWORD_9);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 判断手机号 和验证码是否是正确对应的
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> verifyCode_7(String pid, int type, int code) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("code", code) //验证码
                .build()
                .setCmd((short) CMD.VERIFY_MOBILE_7);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> registerCodeSend_5(String pid, int type) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", 1) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.REGISTER_VERIFY_5);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> sendCode_5(String pid, int type, int scene) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", scene) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱4：账号保护
                .build()
                .setCmd((short) CMD.REGISTER_VERIFY_5);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> sendCode_17(String pid, int scene, String imgcode) {

        int type = 1;
        if (StringUtil.isMobile(pid)) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
            type = 0;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", scene) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .pack("imgcode", imgcode) //图形验证码
                .pack("pf", 3) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.ICON_VERIFY_SEND_CODE_17);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
     *
     * @return
     */
    public NewSendBuilder<VerifyUserProtectBean> verifyUserProtect_19(String pid, int type, String psw) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("pf", 3) //平台类型 1:WEB 2:PC 3:安卓 4:IOS 5：个人管理后台
                .pack("psw", psw)//用户密码
                .pack("devinfo", DeviceInfoUtil.getDeviceInfo())
                .build()
                .setCmd((short) CMD.VERIFY_USER_PROTECT_19);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
     *
     * @return
     */
    public NewSendBuilder<SafetyPictureBean> getSafetyPicture_15(String pid, int type, int scene) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", scene) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .pack("pf", 3) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.ICON_VERIFY_15);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> resetPswCodeSend_5(String pid, int type) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", 2) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.REGISTER_VERIFY_5);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端修改手机号的时候请求服务器获取验证码；
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> photoNumAndEmileForCode_119(String pid, int type) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", 3) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.REQUEST_YZM_SET_PHOTO_119);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端修改手机号的时候请求服务器获取验证码；
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> photoNumAndEmileForCode_119(String pid, int type, String pw) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("scene", 3) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .pack("pw", pw) //密码
                .build()
                .setCmd((short) CMD.REQUEST_YZM_SET_PHOTO_119);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 设置置顶状态
     *
     * @return
     */
    public NewSendBuilder<SettingInfoArrBean> setDialogueStick_117(int tid, int toid, int stp, int stick, int sntc) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("toid", toid)
                .pack("stp", stp)
                .pack("stick", stick)
                .pack("sntc", sntc)
                .pack("stat", -1)
                .build()
                .setCmd((short) CMD.SET_USER_CONFIG_117);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 设置消息列表隐藏
     *
     * @return
     */
    public NewSendBuilder<SettingInfoArrBean> setDialogueHint_117(int tid, int toid, int stp, int stat) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)// 用户手机号或者邮箱
                .pack("toid", toid)// 用户手机号或者邮箱
                .pack("stp", stp) //账号类型，0：手机，1：邮箱
                .pack("stick", -1) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .pack("sntc", -1) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .pack("stat", stat) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.SET_USER_CONFIG_117);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端修改手机号的时候请求服务器获取验证码；
     *
     * @return
     */
    public NewSendBuilder<BackSettingBean> requestSettingNoti_115(int tid, int tntc) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("tntc", tntc)
                .build()
                .setCmd((short) CMD.SET_CONFIG_115);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 校验修改手机号的验证码；
     *
     * @param pid
     * @param type
     * @return
     */
    public NewSendBuilder<BaseSocketBean> resetPhotoNum_checkyzm_121(String pid, int type, int code) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("code", code) //验证码
                .build()
                .setCmd((short) CMD.SET_PHOTO_NOMBER_121);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 请求提交意见反馈；
     */
    public NewSendBuilder<BaseSocketBean> resetCommitFeedback_123(int uid, int tid, String cont) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uid)// 用户id
                .pack("tid", tid) //团队id
                .pack("cont", cont) //已经反馈
                .build()
                .setCmd((short) CMD.REQUEST_COMMIT_FEEDBACK_123);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取团队信息
     *
     * @return
     */
    public NewSendBuilder<GetAllTeamInfosBean> teamInfosGet_50() {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .build()
                .setCmd((short) CMD.TEAMS_INFO_50);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求用户是否存在
     *
     * @return 3：用户未注册 5： 用户已注册
     */
    public NewSendBuilder<BaseSocketBean> judgeUserIsExist_60(String pid, int type) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .build()
                .setCmd((short) CMD.JUDGE_USER_IS_EXIST_60);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求用户是否存在
     *
     * @return 3：用户未注册 5： 用户已注册
     */
    public NewSendBuilder<BaseSocketBean> judgeUserIsExist_13(String pid, int type) {
        if (type == 0) {
            String[] countryCode = SettingUtil.getI().getCountryCode();
            pid = countryCode[1] + "+" + pid;
        }
        ArrayList<HashMap<Object, Object>> deviceInfo = DeviceInfoUtil.getDeviceInfo();
        String imei = "";
        Object o = deviceInfo.get(0).get("imei");
        if (o != null && o instanceof String) {
            imei = (String) o;
        }
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .pack("pf", 3) //账号类型，0：手机，1：邮箱
                .pack("cid", imei) //账号类型，0：手机，1：邮箱
                .build()
                .setCmd((short) CMD.JUDGE_USER_IS_EXIST_13);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 邀请手机号注册
     */
    public NewSendBuilder<BaseSocketBean> inviteUserRegister_72(String pid, int type) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)// 用户手机号或者邮箱
                .pack("type", type) //账号类型，0：手机，1：邮箱
                .build()
                .setCmd((short) CMD.INVITE_USER_REGISTER_72);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 创建团队
     *
     * @param tname 团队名称
     * @return
     */
    public NewSendBuilder<CreateTeamBean> teamCreate_58(String tname) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tnm", tname) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.TEAM_CREATE_58);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求把联系人加入团队
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> teamInvitation_62(String pid, int type, int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pid", pid)
                .pack("type", type)
                .pack("tid", tid) //场景 0：其他场景 1：用户注册  2：忘记密码  3：修改手机或邮箱
                .build()
                .setCmd((short) CMD.TEAM_INVITATION_62);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端申请加入团队
     *
     * @return
     */
    public NewSendBuilder<JoinTeamBean> teamJoin_66(int tid, int uid, int iid, int src, String qrcode) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .pack("uid", uid)//	扫码用户id
                .pack("iid", iid)//邀请者id
                .pack("src", src) //来源： 0：扫二维码 1：分享团队邀请
                .pack("qrcode", qrcode) //来源： 0：扫二维码 1：分享团队邀请
                .build()
                .setCmd((short) CMD.TEAM_JOIN_66);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端申请加入团队
     *
     * @return
     */
    public NewSendBuilder<JoinTeamBean> teamDynamicJoin_66(int dynpw) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("src", 4) //来源： 0：扫二维码 1：分享团队邀请
                .pack("dynpw", dynpw) //来源： 0：扫二维码 1：分享团队邀请
                .pack("iid", Common.getI().getCurrentUserID()) //来源： 0：扫二维码 1：分享团队邀请
                .pack("uid", Common.getI().getCurrentUserID()) //来源： 0：扫二维码 1：分享团队邀请
                .build()
                .setCmd((short) CMD.TEAM_JOIN_66);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 修改团队信息
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> teamChangeInfo_68(int tid, String tname, String des, String avatar) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .pack("tnm", tname)//	团队名称
                .pack("des", des)//团队描述
                .pack("avt", avatar) //	团队头像
                .build()
                .setCmd((short) CMD.TEAM_CHANGE_INFO_68);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 退出团队
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> teamQuit_70(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .build()
                .setCmd((short) CMD.TEAM_QUIT_70);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取团队信息
     *
     * @return
     */
    public NewSendBuilder<TeamInfoBeanDB> teamGetInfo_64(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .build()
                .setCmd((short) CMD.TEAM_GET_INFO_64);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 校验token
     */
    public NewSendBuilder<BaseSocketBean> verifyToken_74(String tk) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tk", tk)//token
                .pack("pf", 3) //平台类型 1:PC 2:移动端 3安卓 4 IOS
                .build()
                .setCmd((short) CMD.VERIFY_TOKEN_74);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 刷新团队群 和成员 所有资料
     */
    public NewSendBuilder<NewTeamGroupMenberInfoBean> refreshTeamInfo_52(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .build()
                .setCmd((short) CMD.REFRESH_TEAM_INFO_52);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 刷新聊天记录
     */
    public NewSendBuilder<BaseSocketBean> refreshChatInfo_54(int tid, List<HashMap> sinfo) {
        EventBus.getDefault().postSticky(new OnReceptionMsgEvent(true));
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("sinfo", sinfo)
                .build()
                .setCmd((short) CMD.NEW_DIALOGUE_INFO_54);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取用户信息
     */
    public NewSendBuilder<BaseListSocketBean<WholeUserInfoBean>> getUserInfo_78(int uid) {
        LogX.e5(uid);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(uid);
        return getUserInfo_78(integers);
    }

    /**
     * 获取用户信息
     */
    public NewSendBuilder<BaseListSocketBean<WholeUserInfoBean>> getUserInfo_78(List<Integer> uid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uid)//团队ID
                .build()
                .setCmd((short) CMD.GET_USER_INFO_78);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取用户信息
     */
    public NewSendBuilder<BaseListSocketBean<WholeUserInfoBean>> getUserInfo_174(int tid, List<Integer> uid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .pack("uid", uid)//团队ID
                .build()
                .setCmd((short) CMD.GET_USER_INFO_174);
        return NewIMSocket.Builder(protocol).setOnResultErrorListener(new OnResultErrorListener() {
            @Override
            public void onResultError(BaseSocketBean socketBean, int code, String errorStr) {
                for (Integer integer : uid) {
                    ChatDistribute.getI().removeMsgList(tid, integer, 0);
                }
            }
        });
    }


    /**
     * 获取用户信息
     */
    public NewSendBuilder<BaseListSocketBean<WholeUserInfoBean>> getUserInfo_174(int tid, int uid) {
        LogX.e5(uid);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(uid);
        return getUserInfo_174(tid, integers);
    }


    /**
     * 请求团队里的用户信息及状态信息
     */
    public NewSendBuilder<BaseListSocketBean<WholeUserInfoBean>> getTeamUserInfo_174(int tid, int uid) {
        LogX.eMax("■174");
        if (tid == 0) {
            return null;
        }
        ArrayList<Integer> uids = new ArrayList<>();
        uids.add(uid);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uids)//团队ID
                .pack("tid", tid)//团队ID
                .build()
                .setCmd((short) CMD.GET_USER_INFO_174);

        return NewIMSocket.Builder(protocol).setOnResultErrorListener(new OnResultErrorListener() {
            @Override
            public void onResultError(BaseSocketBean socketBean, int code, String errorStr) {
                ChatDistribute.getI().removeMsgList(tid, uid, 0);
            }
        });
    }

    /**
     * 请求团队里的用户信息及状态信息
     */
    public NewSendBuilder<BaseListSocketBean<WholeUserInfoBean>> getRobot_174() {

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("type", 1)//团队ID
                .pack("tid", Common.getI().getCurrentTeamID())//团队ID
                .build()
                .setCmd((short) CMD.GET_USER_INFO_174);

        return NewIMSocket.Builder(protocol);
    }


    /**
     * 请求团队里的用户信息及状态信息
     */
    public NewSendBuilder<BaseListSocketBean<ContactInfoBeanDB>> getGroupUserInfo_106(int gid) {
//        ArrayList<Integer> gids = new ArrayList<>();
//        gids.add(gid);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("gid", gid)//团队ID
                .build()
                .setCmd((short) CMD.GET_GROUP_USER_INFO_106);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 请求团队里的用户信息及状态信息
     */
    public NewSendBuilder<BaseListSocketBean<GroupInfoBean>> getTeamGroupInfo_102(int tid, int gid) {
        ArrayList<Integer> gids = new ArrayList<>();
        gids.add(gid);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("gid", gids)//团队ID
                .pack("tid", tid)//团队ID
                .build()
                .setCmd((short) CMD.GET_TEAM_GROUP_INFO_102);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 请求团队里的用户信息及状态信息
     */
    public NewSendBuilder<BaseListSocketBean<GroupInfoBean>> getAllTeamGroupInfo_102(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//团队ID
                .build()
                .setCmd((short) CMD.GET_TEAM_GROUP_INFO_102);
        return NewIMSocket.Builder(protocol);
    }

//    /**
//     * 发送信息
//     */
//    public NewSendBuilder<MsgBean> sendMsg_56(int tid, int mtp, int stp, int toid, String msg, String param) {
//        msg = EmojiParser.parseToAliases(App.getContext(), msg);
//
//        SendProtocol protocol = SendProtocol
//                .newBufferPacker()
//                .pack("tid", tid)//
//                .pack("mtp", mtp)//消息类型 0为系统通知 1为文本 2为图片 3为文件 4为语音 5为视频
//                .pack("stp", stp)//会话类型 0私聊 1群聊
//                .pack("opt", 0)//会话类型 0私聊 1群聊
//                .pack("toid", toid)//目标用户id 如果是群聊为群id
//                .pack("msg", msg)//消息内容 如果是图片文件等，则为缩略图url
//                .pack("param", param)//图片文件等源url，点击放大图用
//                .build()
//                .setCmd((short) CMD.SEND_MSG_56);
//        return NewIMSocket.Builder(protocol);
//    }

    /**
     * 发送信息
     */
    public NewSendBuilder<MsgBean> sendMsg_56(int tid, int mtp, int stp, int toid, String msg, String param) {
        msg = EmojiParser.parseToAliases(App.getContext(), msg);
        int opt = 0;
        switch (mtp) {
            case 10: {
                opt = 2;
                break;
            }
            case 11: {
                opt = 3;
                break;
            }
            default: {
                String reply = JsonUtils.newJson(param)
                        .getString("reply", null);
                if (reply != null) {
                    opt = 4;
                }
            }
        }

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//
                .pack("mtp", mtp)//消息类型 0为系统通知 1为文本 2为图片 3为文件 4为语音 5为视频
                .pack("stp", stp)//会话类型 0私聊 1群聊
                .pack("opt", opt)//会话类型 0私聊 1群聊
                .pack("toid", toid)//目标用户id 如果是群聊为群id
                .pack("msg", msg)//消息内容 如果是图片文件等，则为缩略图url
                .pack("param", param)//图片文件等源url，点击放大图用
                .build()
                .setCmd((short) CMD.SEND_MSG_56);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 发送信息
     */
    public NewSendBuilder<MsgBean> sendMsg_56(int tid, int mtp, int stp, int toid, String msg, String param, int opt) {
        msg = EmojiParser.parseToAliases(App.getContext(), msg);

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)//
                .pack("mtp", mtp)//消息类型 0为系统通知 1为文本 2为图片 3为文件 4为语音 5为视频
                .pack("stp", stp)//会话类型 0私聊 1群聊
                .pack("opt", opt)///消息扩展操作类型： 1 @用户类型 2消息撤回 3编辑消息 4引用回复
                .pack("toid", toid)//目标用户id 如果是群聊为群id
                .pack("msg", msg)//消息内容 如果是图片文件等，则为缩略图url
                .pack("param", param)//图片文件等源url，点击放大图用
                .build()
                .setCmd((short) CMD.SEND_MSG_56);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 发送信息
     */
    public NewSendBuilder<MsgBean> removeAllGroupMsg_56(int gid) {

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())//
                .pack("mtp", 13)//13删除所有消息 只能是管理员创建者
                .pack("stp", 1)//会话类型 0私聊 1群聊
                .pack("opt", 0)///消息扩展操作类型： 1 @用户类型 2消息撤回 3编辑消息 4引用回复
                .pack("toid", gid)//目标用户id 如果是群聊为群id
                .pack("msg", "")//消息内容 如果是图片文件等，则为缩略图url
                .pack("param", "")//图片文件等源url，点击放大图用
                .build()
                .setCmd((short) CMD.SEND_MSG_56);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 发送信息
     */
    public NewSendBuilder<MsgBean> sendMsg_56(MsgBean msgBean) {
        String msg = EmojiParser.parseToAliases(App.getContext(), msgBean.getMsg().toString());


        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", msgBean.getTid())//
                .pack("mtp", msgBean.getMtp())//消息类型 0为系统通知 1为文本 2为图片 3为文件 4为语音 5为视频
                .pack("stp", msgBean.getStp())//会话类型 0私聊 1群聊
                .pack("opt", msgBean.getOpt())//会话类型 0私聊 1群聊
                .pack("toid", msgBean.getToid())//目标用户id 如果是群聊为群id
                .pack("msg", msg)//消息内容 如果是图片文件等，则为缩略图url
                .pack("param", msgBean.getParam())//图片文件等源url，点击放大图用
                .build()
                .setCmd((short) CMD.SEND_MSG_56);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 发送信息
     */
    public NewSendBuilder<MsgBean> withdrawMsg(MsgBean msgBean) {
        String msg = EmojiParser.parseToAliases(App.getContext(), msgBean.getMsg().toString());


        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", msgBean.getTid())//
                .pack("mtp", MsgBean.CONTENT_WITHDRAW)//消息类型 0为系统通知 1为文本 2为图片 3为文件 4为语音 5为视频
                .pack("stp", msgBean.getStp())//会话类型 0私聊 1群聊
                .pack("opt", msgBean.getOpt())//会话类型 0私聊 1群聊
                .pack("toid", msgBean.getToid())//目标用户id 如果是群聊为群id
                .pack("msg", msg)//消息内容 如果是图片文件等，则为缩略图url
                .pack("param", msgBean.getParam())//图片文件等源url，点击放大图用
                .build()
                .setCmd((short) CMD.SEND_MSG_56);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 已读数据传递上去
     */
    public NewSendBuilder<BaseSocketBean> refreshChatUnread_76(int tid, int toid, int tp, int mid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("toid", toid)
                .pack("tp", tp)
                .pack("mid", mid)
                .build()
                .setCmd((short) CMD.REFRESH_CHAT_UNREAD_76);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 删除群成员
     */
    public NewSendBuilder<BaseSocketBean> removeGroupMember_96(int tid, int gid, int uid) {

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("gid", gid)
                .pack("uid", uid)
                .build()
                .setCmd((short) CMD.DEL_GROUP_MEMBER_96);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 创建群
     */
    public NewSendBuilder<CreateGroupInfoBean> crateGroup_84(int tid, String nm, String avt, List<Integer> uids) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("nm", nm)
                .pack("avt", avt)
                .pack("uid", uids)
                .build()
                .setCmd((short) CMD.CREATE_GROUP_84);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 创建群
     */
    public <T extends Map> NewSendBuilder<BaseSocketBean> addGroupManage_98(int tid, int gid, List<T> setinfo) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("gid", gid)
                .pack("setinfo", setinfo)
                .build()
                .setCmd((short) CMD.ADD_GROUP_MANAGE_98);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 创建群
     */
    public NewSendBuilder<BaseSocketBean> inviteGroupMember_90(int tid, int gid, List<Integer> uids) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("gid", gid)
                .pack("uid", uids)
                .build()
                .setCmd((short) CMD.ADD_GROUP_MEMBER_90);
        return NewIMSocket.Builder(protocol);
    }


//    public NewSendBuilder<WholeUserInfoBean> changeMyUserInfo_100(String nm, String avt, int sex, String des, int dm, String pho, String eml) {
//        SendProtocol protocol = SendProtocol
//                .newBufferPacker()
//                .pack("nm", nm)
//                .pack("avt", avt)
//                .pack("sex", sex)
//                .pack("des", des)
//                .pack("dm", dm)
//                .pack("pho", pho)
//                .pack("eml", eml)
//                .build()
//                .setCmd((short) CMD.CHANGE_USER_INFO_100);
//        return NewIMSocket.Builder(protocol);
//    }

    public NewSendBuilder<WholeUserInfoBean> changeMyUserInfo_176(int tid, String nm, String avt, int sex, String des) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("nm", nm)
                .pack("avt", avt)
                .pack("sex", sex)
                .pack("des", des)
                .pack("spf", -1)
                .pack("sef", -1)
                .build()
                .setCmd((short) CMD.CHANGE_USER_INFO_176);
        return NewIMSocket.Builder(protocol);
    }

    public NewSendBuilder<WholeUserInfoBean> hidePhone_176(boolean hidePhone) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("nm", "")
                .pack("avt", "")
                .pack("sex", -1)
                .pack("des", "")
                .pack("spf", hidePhone ? 1 : 0)
                .pack("sef", -1)
                .build()
                .setCmd((short) CMD.CHANGE_USER_INFO_176);
        return NewIMSocket.Builder(protocol);
    }

    public NewSendBuilder<WholeUserInfoBean> hideEmail_176(boolean hideEmali) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("nm", "")
                .pack("avt", "")
                .pack("sex", -1)
                .pack("des", "")
                .pack("spf", -1)
                .pack("sef", hideEmali ? 1 : 0)
                .build()
                .setCmd((short) CMD.CHANGE_USER_INFO_176);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 修改全信息；
     */
    public NewSendBuilder<BaseSocketBean> changeGroupInfo_94(int tid, int gid, String nm, String des, String avt) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("gid", gid)
                .pack("nm", nm)
                .pack("avt", avt)
                .pack("des", des)
                .build()
                .setCmd((short) CMD.CHANGE_GROUP_INFO_94);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 创建群
     */
    public NewSendBuilder<BaseSocketBean> quitGroup_92(int tid, int gid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("gid", gid)
                .build()
                .setCmd((short) CMD.QUIT_GROUP_92);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 上传文件
     */
    public NewSendBuilder<NewUploadFileBean> newUploadFile_1100(int uid, int toid, int fscene, int ftype, String fnm, long fsz, int flen, String md5) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uid)//用户id
                .pack("tid", Common.getI().getCurrentTeamID())//用户id
                .pack("toid", toid)//聊天对象id，群为群id，私聊为私聊对象id，如只是上传头像等无关聊天的文件则填对应的目标群或用户id
                .pack("fscene", fscene)//文件场景 0私聊 1群聊 2上传头像
                .pack("ftype", ftype)//文件类型 0图片 1文件 2链接(暂不用)
                .pack("fnm", fnm)//文件名称
                .pack("fsz", fsz)//文件大小 字节数
                .pack("md5", md5)//上传文件的md5串
                .pack("flen", flen)//上传文件的md5串
                .build()
                .setCmd((short) CMD.UPLOAD_FILE_REQUEST_1100);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 上传文件
     */
    public NewSendBuilder<UploadFileDataBean> uploadFileData_1102(int fid, long offset, int dsz, byte[] datas) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("fid", fid)//	服务器生成的上传文件id
                .pack("offset", offset)//此次数据块开始位置的偏移量，从文件的第一个byte开始算的位置
                .pack("dsz", dsz)//此次发送数据块大小 （按照上一次服务器回应的大小）
                .pack("data", datas)//数据块字节流 （暂定大小为 2^15=32768，最后一块不够这个数，则按实际大小）
                .build()
                .setCmd((short) CMD.UPLOAD_FILE_DATA_1102);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 请求共享媒体
     * 图片
     */
    public NewSendBuilder<ShareMediaBean> request_share_media_picture_127(int tid, int toid, int sid, int stp, int ftp, int page) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("toid", toid)
                .pack("sid", sid)
                .pack("stp", stp)
                .pack("ftp", ftp)
                .pack("page", page)
                .build()
                .setCmd((short) CMD.REQUEST_SHARE_MEDIA_PICTURE_127);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 共享文件搜索
     *
     * @param tid
     * @param toid
     * @param sid
     * @param stp
     * @param page
     * @param str
     * @return
     */
    public NewSendBuilder<ShareSearchFilesBean> request_searche_share_files_129(int tid, int toid, int sid, int stp, int page, String str) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("toid", toid)
                .pack("sid", sid)
                .pack("stp", stp)
                .pack("page", page)
                .pack("str", str)
                .build()
                .setCmd((short) CMD.SEARCH_SHARE_FILES_129);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 提出成员
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> kickMember_131(int tid, int uid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("uid", uid)
                .build()
                .setCmd((short) CMD.KICK_MEMBER_131);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 添加管理员
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> addTeamAdmin_136(int tid, int id) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("id", id)
                .pack("role", 1)
                .build()
                .setCmd((short) CMD.ADD_OR_CANCEL_ADMIN_136);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 取消管理员
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> cancelTeamAdmin_136(int tid, int id) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("id", id)
                .pack("role", 0)
                .build()
                .setCmd((short) CMD.ADD_OR_CANCEL_ADMIN_136);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 请求消息列表
     *
     * @return
     */
    public NewSendBuilder<MsgListBean> getHistoryChatData134(MsgBean msgBean, int cnt) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("stp", msgBean.getStp())
                .pack("sid", Common.getI().getCurrentUserID())
                .pack("toid", msgBean.getToid())
                .pack("mid", msgBean.getMid())
                .pack("cnt", cnt)
                .build()
                .setCmd((short) CMD.HISTORY_CHAT_DATA_134);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * @return
     */
    public NewSendBuilder<UpdateAppBean> checkUpdate_142(String ver) {

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("ver", ver)
                .pack("pf", 3)
                .build()
                .setCmd((short) CMD.UPDATE_APP_142);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取二维码
     *
     * @return
     */
    public NewSendBuilder<QRCodeBean> getQRCode_146(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .build()
                .setCmd((short) CMD.GET_QRCODE_146);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取二维码
     *
     * @return
     */
    public NewSendBuilder<QRCodeBean> getInviteURL_146(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("typ", 3)

                .build()
                .setCmd((short) CMD.GET_QRCODE_146);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 请求消息列表
     *
     * @return
     */
    public NewSendBuilder<QrcodeRequestTidBean> getQrcodeRequestTid_148(String qrcode, int fromId) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("fromId", fromId)
                .pack("qrcode", qrcode)
                .build()
                .setCmd((short) CMD.QRCODE_REQUEST_TID_148);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 请求消息列表
     *
     * @return
     */
    public NewSendBuilder<BaseSocketBean> batchDeleteMsg_152(int tid, int stp, int toid, ArrayList<HashMap<String, Object>> mids) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("stp", stp)
                .pack("toid", toid)
                .pack("delMsg", mids)
                .build()
                .setCmd((short) CMD.BATCH_DELETE_MSG_152);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 请求国际区号接口；
     */
    public NewSendBuilder<CountryCodeBean> getCountryCode_11(String version) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("ver", version)
                .build()
                .setCmd((short) CMD.REQUEST_COUNTRY_CODE_11);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 请求国际区号接口；
     */
    public NewSendBuilder<CountryCodeBean> getCountryCode_180(String version) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("ver", version)
                .build()
                .setCmd((short) CMD.REQUEST_COUNTRY_CODE_180);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求自定义表情(156)；
     */
    public NewSubpackageSendBuilder<BaseListSubpackageSocketBean<EmojiBean>> getEmotion_156(int emojiVer) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("eid", emojiVer)
                .pack("tid", Common.getI().getCurrentTeamID())
                .build()
                .setCmd((short) CMD.GET_EMOTION_156);
        return NewIMSocket.BuilderPage(protocol);
    }

    /**
     * 客户端请求添加自定义表情(158)；
     */
    public NewSendBuilder<EmojiBean> addEmotion_158(String param) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("param", param)
                .build()
                .setCmd((short) CMD.ADD_EMOTION_158);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求删除自定义表情(160)；
     */
    public NewSendBuilder<DelEmojiBean> delEmotion_160(int emojiId) {
        ArrayList<Integer> delEmojis = new ArrayList<>();
        delEmojis.add(emojiId);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("einfo", delEmojis)
                .build()
                .setCmd((short) CMD.DEL_EMOTION_160);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求自定义表情(156)；
     */
    public NewSendBuilder<CountryCodeBean> delEmotion_160(ArrayList<Integer> delEmojis) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("einfo", delEmojis)
                .build()
                .setCmd((short) CMD.DEL_EMOTION_160);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 新 客户端请求同步团队里群组、用户昵称头像信息等（167）
     */
    public NewSubpackageSendBuilder<NewTeamGroupMenberInfoBean> getTeamUserInfo_167(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("all", 0)
                .build()
                .setCmd((short) CMD.GET_TEAM_USER_INFO_167);
        return NewIMSocket.BuilderPage(protocol);
    }


    /**
     * 新 客户端请求同步团队里群组、用户昵称头像信息等（172）
     */
    public NewSubpackageSendBuilder<BaseListSubpackageSocketBean<MsgBean>> getChatMsg_172(int stp, int tid, int uid, int toid, int mid, int cnt) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("stp", stp)
                .pack("tid", tid)
                .pack("uid", uid)
                .pack("toid", toid)
                .pack("mid", mid)
                .pack("cnt", cnt)
                .build()
                .setCmd((short) CMD.GET_CHAT_MSG_172);
        return NewIMSocket.BuilderPage(protocol);
    }


    /**
     * 客户端请求指定用户手机邮箱（183）
     */
    public NewSendBuilder<BaseListSocketBean<PhoneAndEmailBean>> getUserPhoneEmail_183(int uid) {
        ArrayList<Integer> uids = new ArrayList<>();
        uids.add(uid);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uids)
                .pack("tid", Common.getI().getCurrentTeamID())
                .build()
                .setCmd((short) CMD.GET_USER_PHONE_EMAIL_183);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 客户端请求指定用户手机邮箱（185）
     * map参数
     * sid	int	发送者ID
     * stp	int	会话类型 0私聊 1群聊
     * mid	int	消息id
     * toid	int	会话对象id，如果是群聊，则群id 如果是私聊，则为私聊对象的id
     */
    public NewSendBuilder<BaseSocketBean> addCollect_185(int tid, int uid, Map<String, Object> minfo) {

        List<Map<String, Object>> maps = new ArrayList<>();
        maps.add(minfo);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("uid", uid)
                .pack("minfo", maps)
                .build()
                .setCmd((short) CMD.ADD_COLLECT_185);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求指定用户手机邮箱（187）
     * map参数
     * sid	int	发送者ID
     * stp	int	会话类型 0私聊 1群聊
     * mid	int	消息id
     * toid	int	会话对象id，如果是群聊，则群id 如果是私聊，则为私聊对象的id
     */
    public NewSendBuilder<BaseSocketBean> delCollect_187(int tid, int uid, List<Integer> cid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("uid", uid)
                .pack("cid", cid)
                .build()
                .setCmd((short) CMD.DEL_COLLECT_187);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求指定用户手机邮箱（187）
     * map参数
     * sid	int	发送者ID
     * stp	int	会话类型 0私聊 1群聊
     * mid	int	消息id
     * toid	int	会话对象id，如果是群聊，则群id 如果是私聊，则为私聊对象的id
     */
    public NewSendBuilder<BaseListSocketBean<CollectBean>> synchronize_collect_189(int tid, int uid, int cid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("uid", uid)
                .pack("cid", cid)
                .build()
                .setCmd((short) CMD.SYNCHRONIZE_COLLECT_189);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 创建群
     */
    public NewSendBuilder<BaseSocketBean> dissolveGroup_191(int tid, int uid, int gid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("uid", uid)
                .pack("gid", gid)
                .build()
                .setCmd((short) CMD.DISSOLVE_GROUP_191);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 创建群
     */
    public NewSendBuilder<BaseSocketBean> dissolveTeam_194(int tid, int uid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("uid", uid)
                .build()
                .setCmd((short) CMD.DISSOLVE_TEAM_194);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 新 客户端请求同步团队里群组、用户昵称头像信息等（167）
     */
    public NewSubpackageSendBuilder<BaseListSubpackageSocketBean<MsgListDB>> refreshChatInfo_164(int tid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
//                .pack("sinfo", null)
                .build()
                .setCmd((short) CMD.NEW_DIALOGUE_INFO_164);
        return NewIMSocket.BuilderPage(protocol);
    }

    /**
     * 新 客户端请求同步团队里群组、用户昵称头像信息等（167）
     */
    public NewSendBuilder<BaseSocketBean> changePsw_196(int uid, String opw, String npw) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uid)
                .pack("opw", opw)
                .pack("npw", npw)
                .build()
                .setCmd((short) CMD.CHANGE_PSW_196);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求设置账号保护（198）
     */
    public NewSendBuilder<BaseSocketBean> setUserProtect_198(int uid, int aprt) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uid)
                .pack("aprt", aprt)
                .build()
                .setCmd((short) CMD.SET_USER_PROTECT_198);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求设置账号保护（198）
     */
    public NewSendBuilder<UserProtectBean> getUserProtect_200(int uid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", uid)
                .build()
                .setCmd((short) CMD.GET_USER_PROTECT_200);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * /客户端下载文件时请求验证码时间戳
     */
    public NewSendBuilder<DownloadFileSTBean> getDownloadFileTimestamp_212(String url) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("url", url)
                .build()
                .setCmd((short) CMD.GET_DOWNLOAD_FILE_TIMESTAMP_212);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求绑定登陆二维码（227）
     */
    public NewSendBuilder<BaseSocketBean> getPcLogin_227(String qrcode) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("qrcode", qrcode)
                .pack("uid", Common.getI().getCurrentUserID())
                .build()
                .setCmd((short) CMD.GET_PC_LOGIN_227);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 客户端请求修改全局手机通知配置（225）
     */
    public NewSendBuilder<BaseSocketBean> changeGlobalNotification_225(boolean IsNotification) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("type", IsNotification ? 1 : 0)
                .pack("uid", Common.getI().getCurrentUserID())
                .build()
                .setCmd((short) CMD.CHANGE_GLOBAL_NOTIFICATION_225);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求修改全局手机通知配置（225）
     */
    public NewSendBuilder<BaseSocketBean> logout_229() {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pf", 3)
                .pack("uid", Common.getI().getCurrentUserID())
                .build()
                .setCmd((short) CMD.LOGOUT_229);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * //客户端请求退出X.Team的WEB、PC端（223）
     */
    public NewSendBuilder<BaseSocketBean> quitPc_223() {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("pf", 3)
                .pack("uid", Common.getI().getCurrentUserID())
                .build()
                .setCmd((short) CMD.GET_QUIT_PC_223);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求创建机器人小可爱（231）
     */
    public NewSendBuilder<BaseSocketBean> createRobot_231() {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("teamId", Common.getI().getCurrentTeamID())
                .build()
                .setCmd((short) CMD.CREATE_ROBOT_231);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求机器人小可爱的token（233
     * teamId	int	团队ID
     * type	int	0:用户输入/token 1：用户输入/newtoken
     */
    public NewSendBuilder<RobotTokenBean> getRobotToken_233(int type, int groupId) {

        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("teamId", Common.getI().getCurrentTeamID())
                .pack("type", type)
                .pack("groupId", groupId)
                .build()
                .setCmd((short) CMD.GET_ROBOT_TOKEN_233);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * mode	int	模式： 0：自动 1：主动
     * stat	int	活动状态： 0为正常，1为离开/忙碌，2为请勿打扰，3为离线状态
     */
    public NewSendBuilder<OnLineStateBean> set_online_state_237(int mode, int stat) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("pf", 3)
                .pack("mode", mode)
                .pack("stat", stat)
                .build()
                .setCmd((short) CMD.SET_ONLINE_STATE_239);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * tid	int	团队id
     * uid	int	用户id
     * type	int	获取状态的类型： 1 个人 2 群组 3 团队
     * toid	int	类型1，为对方用户的id，类型2，为群组id，类型3，为0
     */
    public NewSubpackageSendBuilder<BaseListSubpackageSocketBean<OnLineStateBean>> get_online_state_241(int type, int toid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("type", type)
                .pack("toid", toid)
                .build()
                .setCmd((short) CMD.GET_ONLINE_STATE_241);
        return NewIMSocket.BuilderPage(protocol);
    }

    /**
     * uid	int	用户ID
     * type	int	用户信息类型 1 离开/忙碌配置 2 客户端无动作时间配置（分钟）
     * lorb	int	离开/忙碌配置 0 离开 1 忙碌 type为1时为必填
     * time	int	客户端无动作时间配置（分钟） 默认为3分钟
     */
    public NewSendBuilder<UserOnlineConfigBean> set_user_online_config_242(int type, int lorb, int time) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("type", type)
                .pack("lorb", lorb)
                .pack("time", time)
                .build()
                .setCmd((short) CMD.SET_USER_ONLINE_CONFIG_244);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * uid	int	用户ID
     * type	int	用户信息类型 1 离开/忙碌配置 2 客户端无动作时间配置（分钟）
     */
    public NewSendBuilder<UserOnlineConfigBean> get_user_online_config_246() {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .build()
                .setCmd((short) CMD.GET_USER_ONLINE_CONFIG_246);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求获取xlist团队个人任务列表（251）
     */
    public NewSubpackageSendBuilder<BaseListSubpackageSocketBean<XListTaskBean>> get_xlist_task_251(int tkuid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("tkuid", tkuid)
                .build()
                .setCmd((short) CMD.GET_XLIST_TASK_251);
        return NewIMSocket.BuilderPage(protocol);
    }

    /**
     * 获取参与者列表
     */
    public NewSendBuilder<OseersBean> get_xlist_oseers_256(int tkuid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("tkuid", tkuid)
                .build()
                .setCmd((short) CMD.GET_OSEERS_256);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 获取参与者列表
     */
    public NewSendBuilder<OseersBean> add_or_del_xlist_oseers_254(int type, ArrayList<Integer> tkuids) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid", Common.getI().getCurrentUserID())
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("type", type)
                .pack("oseers", tkuids)
                .build()
                .setCmd((short) CMD.ADD_OR_DEL_OSEERS_254);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 名称	类型	描述
     * tid	int	团队ID
     * type	int	请求类型：1：设置 2：获取动态口令 3：刷新动态口令
     * chgtm	int	设置动态口令改变时间（n天后改变）默认3天
     * viwtyp	int	可见类型：1：创建者、管理员 2：全团队 默认1
     */
    public NewSendBuilder<TeamDynamicOrderBean> set_dynamic_order_261(int type, int chgtm, int viwtyp) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("type", type)
                .pack("chgtm", chgtm)
                .pack("viwtyp", viwtyp)
                .build()
                .setCmd((short) CMD.DYNAMIC_ORDER_261);
        return NewIMSocket.Builder(protocol);
    }

   /**
     名称	类型	描述
     tid	int	团队ID
     type	int	请求类型：1：设置 2：获取动态口令 3：刷新动态口令
     chgtm	int	设置动态口令改变时间（n天后改变）默认3天
     viwtyp	int	可见类型：1：创建者、管理员 2：全团队 默认1
     */
    public NewSendBuilder<TeamDynamicOrderBean> get_dynamic_order_261(int type) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("type",type)
                .build()
                .setCmd((short) CMD.DYNAMIC_ORDER_261);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 客户端请求添加、修改、删除xlist团队个人任务（249）
     *
     * @param xListTaskBean
     * @return
     */
    public NewSendBuilder<XListTaskResultBean> add_or_delXlist_249(int type, int uid, XListTaskBean xListTaskBean) {

        ArrayList<HashMap> tkinfo = new ArrayList<>();
        HashMap<Object, Object> hashMap = new HashMap<>();
        tkinfo.add(hashMap);

        hashMap.put("tkid", xListTaskBean.getTkid());
        hashMap.put("tktitle", xListTaskBean.getTktitle());
        hashMap.put("frsppsn", xListTaskBean.getFrsppsn());
        hashMap.put("excut", xListTaskBean.getExcut());
        hashMap.put("tkdes", xListTaskBean.getTkdes());
        hashMap.put("tkstat", xListTaskBean.getTkstat());
        hashMap.put("level", xListTaskBean.getLevel());
        hashMap.put("bgntm", xListTaskBean.getBgntm());
        hashMap.put("endtm", xListTaskBean.getEndtm());
        hashMap.put("plntm", xListTaskBean.getPlntm());
        hashMap.put("acttm", xListTaskBean.getActtm());
        hashMap.put("remark", xListTaskBean.getRemark());
        hashMap.put("wkpro", xListTaskBean.getWkpro());
        hashMap.put("crttm", xListTaskBean.getCrttm());
        hashMap.put("updtm", xListTaskBean.getUpdtm());


        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", Common.getI().getCurrentTeamID())
                .pack("uid", uid)
                .pack("type", type)
                .pack("tkinfo", tkinfo)
                .build()
                .setCmd((short) CMD.XLIST_TASK_249);
        return NewIMSocket.Builder(protocol);
    }


    /**
     * 名称	类型	描述
     * tid	int	团队ID
     * type	type	int	请求类型: 1 发起 2 回应
     * chgtm	int	设置动态口令改变时间（n天后改变）默认3天
     * opt	int	操作类型：1 请求通话 2 取消通话 3 同意通话 4 拒绝通话 5 关闭通话(请求类型为1(发起)时，opt为1(请求通话)、2(取消通话)、5(关闭通话)，请求类型为2(回应)时，opt可以为3(同意通话)、4(拒绝通话))
     */
    public NewSendBuilder<CallRequestBeanEvent> call_request_263(int avtype, int tid, int toid, int type, int opt) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid", tid)
                .pack("toid", toid)
                .pack("type", type)
                .pack("opt", opt)
                .pack("avtype", avtype)
                .pack("uid", Common.getI().getCurrentUserID())
                .build()
                .setCmd((short) CMD.CALL_REQUEST_263);
        return NewIMSocket.Builder(protocol);
    }

    /**
     * 名称	类型	描述
     * tid	int	团队ID
     * type	int	请求类型：1：设置 2：获取动态口令 3：刷新动态口令
     * chgtm	int	设置动态口令改变时间（n天后改变）默认3天
     * viwtyp	int	可见类型：1：创建者、管理员 2：全团队 默认1
     */
    public NewSendBuilder<BaseSocketBean> send_signalling_266(String event, String data) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("event",event)
                .pack("data", data)
                .build()
                .setCmd((short) CMD.SIGNALLING_266);
        return NewIMSocket.Builder(protocol);
    }
    /**
     tid	int	团队ID
     uid	int	用户id
     type	int	请求类型: 1 发起 2 回应 3系统通知

     opt	int	操作类型：1 会议邀请 2 会议关闭 3 退出会议 4 会议中邀请用户 5 会议中移除用户 6 会议类型更改
     7 管理会议中用户 8 同意邀请 9 拒绝邀请 10 会议用户通话状态变更 11 与IM网络断开连接 12 与IM网络恢复连接
     13 与FS网络断开连接(预留) 14 与FS网络恢复连接(预留)
     avtype	int	音视频类型： 3 音频 4 音频+屏幕共享
     cid	int	会议id
     ctype	int	控制类型：1 单个用户静音 2 解除单个用户静音 3 全员静音 4 解除全员静音
     invuid	array(int)	被邀请用户id，可多个用户
     */
    public NewSendBuilder<MultiCallBean> multiCall_273(MultiCallRequestBean requestBean) {
        LogX.e(requestBean);
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid",requestBean.getTid())
                .pack("uid", requestBean.getUid())
                .pack("type", requestBean.getType())
                .pack("opt", requestBean.getOpt())
                .pack("avtype", requestBean.getAvtype())
                .pack("cid", requestBean.getCid())
                .pack("ctype", requestBean.getCtype())
                .pack("invuid", requestBean.getInvuid())
                .build()
                .setCmd((short) CMD.MULTI_CALL_273);
        return NewIMSocket.Builder(protocol);
    }

    public NewSendBuilder<BaseSocketBean> multiCallVolume_276(int cid,int sgnl) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("uid",Common.getI().getCurrentUserID())
                .pack("cid",cid)
                .pack("sgnl", sgnl)
                .build()
                .setCmd((short) CMD.MULTI_CALL_VOLUME_276);
        return NewIMSocket.Builder(protocol);
    }

    public NewSendBuilder<BaseSocketBean > multiCallUpState_279(int tid,int cid,int stat) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid",tid)
                .pack("uid",Common.getI().getCurrentUserID())
                .pack("cid",cid)
                .pack("stat", stat)
                .build()
                .setCmd((short) CMD.MULTI_CALL_UPSTATE_279);
        return NewIMSocket.Builder(protocol);
    }
    public NewSendBuilder<MultiCallBean > getAllMultiCallUser_281(int tid,int cid) {
        SendProtocol protocol = SendProtocol
                .newBufferPacker()
                .pack("tid",tid)
                .pack("uid",Common.getI().getCurrentUserID())
                .pack("cid",cid)
                .build()
                .setCmd((short) CMD.GET_ALL_MULTI_CALL_USER_281);
        return NewIMSocket.Builder(protocol);
    }


    private static ProtocalPackage instance;

    private ProtocalPackage() {
    }

    public static ProtocalPackage getI() {
        if (instance == null) {
            instance = new ProtocalPackage();
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }


}

