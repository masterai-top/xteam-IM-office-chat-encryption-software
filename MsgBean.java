package com.xmen.xteam.communication.bean;

import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.common.lib.util.span.RegexBean;
import com.common.lib.util.span.SpanUtil;
import com.common.lib.util.span.TouchableSpan;
import com.vdurmont.emoji.EmojiParser;
import com.xmen.xteam.R;
import com.xmen.xteam.app.App;
import com.xmen.xteam.app.Common;
import com.xmen.xteam.app.Constant;
import com.xmen.xteam.msgsockey.socket.adapter.ReceiveProtocol;
import com.xmen.xteam.mvp.ui.fragment.ChatSingleFragment;
import com.xmen.xteam.utils.FileUtil;
import com.xmen.xteam.utils.JsonBuilde;
import com.xmen.xteam.utils.JsonUtils;
import com.xmen.xteam.utils.TimeUtils;
import com.xmen.xteam.wcdb.bean.MsgListDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.commons.models.IUser;
import cn.jiguang.imui.commons.models.IUserDataCallBack;
import comxmen.emotion.utils.SpanStringUtils;
import io.github.rockerhieu.emojicon.EmojiconHandler;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public class MsgBean extends BaseSocketBean implements IMessage,MultiItemEntity, Comparable<MsgBean>  {


    public static final byte ITEM_TYPE_LEFT = 55;
    public static final byte ITEM_TYPE_RLGHT = 56;
    public static final byte ITEM_TYPE_UNREAD = 57; //未读布局
    public static final byte ITEM_TYPE_PROMPT = 58; //提示布局
    public static final byte ITEM_TYPE_DATE = 59; //时间布局
    public static final byte ITEM_NOTICE_ONE = 60; //第一天公告布局

    public static final byte CONTENT_TYPE_TEXT = 1;
    public static final byte CONTENT_TYPE_IMG = 2;
    public static final byte CONTENT_TYPE_VIDEO = 5;
    public static final byte CONTENT_TYPE_VOICE = 4;
    public static final byte CONTENT_TYPE_FILE = 3;
    public static final byte CONTENT_ATE = 9;//@用户类型
    public static final byte CONTENT_NOTICE = 0;//系统通知
    public static final byte CONTENT_JOIN_GROUP = 6;//被拉入群消息
    public static final byte CONTENT_LEAVE_GROUP = 6;//离开群消息
    public static final byte CONTENT_JOIN_TEAM = 7;// 被拉入群团队
    public static final byte CONTENT_NOTICE_ONE = 8;//(公告板的第一条消息)

    public static final byte CONTENT_WITHDRAW = 10;//消息撤回
    public static final byte CONTENT_MSG_CHANGE = 11;//消息编辑
    public static final byte CONTENT_MSG_EMOJITION = 12;//表情
    public static final byte CONTENT_REMOVE_ALL_GROUP_MSG = 13;//删除所有群消息
    public static final byte CONTENT_CALL_MSG = 14;// 14音视频呼叫结果消息(发起方取消、对方取消、通话结束后发送)
    public static final byte CONTENT_UNREAD_LINE = -1;//未读消息分界线.
    public static final byte CONTENT_TIME = -2;//


    public static final int UI_TYPE_VOICE_NORMAL = 0;
    public static final int UI_TYPE_VOICE_PLAY = 1;

    public static final int UI_TYPE_FILE_NORMAL = 0;
    public static final int UI_TYPE_FILE_DOWNLOAD = 1;
    public static final int UI_TYPE_FILE_UPLOAD = 3;
    public static final int UI_TYPE_FILE_COMPLETE = 2;

    public static final int UI_TYPE_IMAGE_NORMAL = 0;
    public static final int UI_TYPE_IMAGE_SEND = 1;
    public static final int UI_TYPE_IMAGE_COMPLETE = 2;
    public static final int UI_TYPE_IMAGE_UPLOAD = 3;


    public static final int STATE_SEND = 1;
    public static final int STATE_SUCCEED = 2;
    public static final int STATE_FAILURE = 3;
    public static final int STATE_DEL = 0;

    private int itemType = -1;  //左边还是右边
    boolean isParseEmotion = false;


    public int getItemType() {
        return itemType;
    }

    private int stp; //会话类型0私聊 1群聊
    private int mtp; //消息类型0为系统通知1为文本2为图片3为文件4为语音5为视频
    private int mid; //消息id
    private int toid; //会话ID
    private CharSequence msg; //消息内容,如果是图片文件等，则为缩略图url
    private String param; //图片文件等源url，点击放大图用
    private int sid; //发送者id
    private long st; //发送时间时间戳
    private int rd; // 对于自己来说 是否已读0未读1已读（预留其他数字为已读人数）
    private int ord; //对于别人来说 是否已读0未读1已读（预留其他数字为已读人数）
    private int tid;
    private int opt;//消息扩展操作类型： 1 @用户类型 2消息撤回 3编辑消息 4引用回复
    private int localTag = 0 ;//本地自定义的标识

    private int state = MsgBean.STATE_SUCCEED;
    private float progress;//存放进度 上传或者下载

    private int uiType; //当前UI的展示样式  比如 语音  0正常状态 1播放状态

    private String url; //文件URL
    private String path; //文件URL
    private long duration;//如果是语音 代表的是语音长度
    private HashMap<String, Object> extras;


    //进入聊天消息的时候 主动获取的消息为false
    //在这期间服务器推送过来的消息为true
    private boolean tag = false;

    public MsgBean(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        super(receiveProtocol, preseq);
    }


    public void setTag(boolean tag) {
        this.tag = tag;
    }


    //进入聊天消息的时候 主动获取的消息为false
    //在这期间服务器推送过来的消息为true
    public boolean isTag() {
        return tag;
    }

    public MsgListDB toMsgListDB(int unread) {
        int msglistState = MsgListDB.STATE_HIDE;
        switch (state) {
            case STATE_SEND: {
                msglistState = MsgListDB.STATE_SEND;
                break;
            }
            case STATE_SUCCEED: {
                //是否已经被对方读过
                if (ord == 0) {
                    msglistState = MsgListDB.STATE_SUCCEED;
                } else if (ord == 1) {
                    msglistState = MsgListDB.STATE_READ;
                }
                break;
            }
            case STATE_FAILURE: {
                msglistState = MsgListDB.STATE_FAILURE;
                break;
            }
        }


        return new MsgListDB(toid, sid, mid, stp, mtp, unread, st, getMsgListMsgContent(), param, msglistState);
    }

    /**
     * 获取用于显示消息列表的聊天内容
     *
     * @return
     */
    public CharSequence getMsgListMsgContent() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

        switch (mtp) {
            case CONTENT_TYPE_TEXT:
            case CONTENT_TYPE_IMG:
            case CONTENT_TYPE_VIDEO:
            case CONTENT_TYPE_VOICE:
            case CONTENT_TYPE_FILE:
            case CONTENT_ATE: {
                //给群成员加上名字的前缀
                if (stp == 1 && sid != Common.getI().getCurrentUserID()) {
                    ContactInfoBeanDB contactInfo = Common.getI().getContactInfo(sid);

                    if (contactInfo != null) {
                        SpannableStringBuilder append = spannableStringBuilder.append(contactInfo.getUname()).append("：").append(getMsg());

                        TouchableSpan[] spans = append.getSpans(0, append.length(), TouchableSpan.class);
                        for (TouchableSpan span : spans) {
                            append.removeSpan(span);
                        }

                        return append;
                    }
                }
                break;
            }
        }


        SpannableStringBuilder append = spannableStringBuilder.append(getMsg());
        TouchableSpan[] spans = append.getSpans(0, append.length(), TouchableSpan.class);
        for (TouchableSpan span : spans) {
            append.removeSpan(span);
        }

        return append;
    }


    @Override
    protected void parse(HashMap<String, Object> receiveProtocol, ReceiveProtocol preseq) {
        itemType = -1;
        stp = getInt("stp");
        mtp = getInt("mtp");
        mid = getInt("mid");
        toid = getInt("toid");
        msg = getString("msg");
        param = getString("param");
        sid = getInt("sid");
        st = getLong("st");
        rd = getInt("rd");
        ord = getInt("ord");
        tid = getInt("tid");
        opt = getInt("opt");
        initParam();
        initItemType();

    }

    public void initParam() {
        if (TextUtils.isEmpty(param)) {
            return;
        }
        extras = extras == null ? new HashMap<>() : extras;
        JsonBuilde jsonBuilde = JsonUtils.newJson(param);

        switch (mtp) {
            case CONTENT_TYPE_TEXT: {

                boolean isChange = jsonBuilde.getBoolean("isChange");
                extras = extras == null ? new HashMap<>() : extras;
                extras.put("isChange", isChange);
            }
            case CONTENT_CALL_MSG: {
                extras = extras == null ? new HashMap<>() : extras;
                extras.put("avtype", jsonBuilde.getInt("avtype"));
                extras.put("avres", jsonBuilde.getInt("avres"));
                extras.put("time", jsonBuilde.getString("time"));
            }
            case CONTENT_ATE: {
                if (!param.contains("uid")) {
                    boolean isChange = jsonBuilde.getBoolean("isChange");
                    extras = extras == null ? new HashMap<>() : extras;
                    extras.put("isChange", isChange);
                } else {
                    extras = extras == null ? new HashMap<>() : extras;
                    try {
                        JSONArray at = JsonUtils.newJson(param).getJSONArray("at");
                        JSONArray jsonArray = at;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int uid = (int) jsonObject.get("uid");
                            extras.put("uid" + i, uid);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case CONTENT_TYPE_IMG: {
                url = jsonBuilde.getString("url", "");
                path = jsonBuilde.getString("path", "");
                int width = jsonBuilde.getInt("tw", 0);
                int height = jsonBuilde.getInt("th", 0);
                if (width == 0 && height == 0) {
                    width = jsonBuilde.getInt("ow", 0);
                    height = jsonBuilde.getInt("oh", 0);
                }
//                int thumbnailWidth = jsonBuilde.getInt("tw", 0);
//                int thumbnailheight = jsonBuilde.getInt("th", 0);
                extras = extras == null ? new HashMap<>() : extras;
                extras.put("width", width);
                extras.put("height", height);
                break;
            }
            case CONTENT_TYPE_VIDEO:
            case CONTENT_TYPE_FILE: {
                url = jsonBuilde.getString("url", "");
                path = jsonBuilde.getString("path", path);
                //判断文件是否存在 如果存在则设置为已经下载完的文件
                if (!TextUtils.isEmpty(path) && new File(path).isFile()) {
                    uiType = UI_TYPE_FILE_COMPLETE;
                }
                extras = extras == null ? new HashMap<>() : extras;
                extras.put("name", jsonBuilde.getString("fnm", ""));
                extras.put("size", FileUtil.formatSize(jsonBuilde.getLong("odsz")));
                if (mtp == CONTENT_TYPE_VIDEO) {
                    duration = jsonBuilde.getInt("flen", 0);
                }
                break;
            }

            case CONTENT_TYPE_VOICE: {
                url = jsonBuilde.getString("url", "");
                duration = jsonBuilde.getInt("flen", 0);
                path = jsonBuilde.getString("path", "");
                break;
            }
            case CONTENT_MSG_EMOJITION: {
                url = jsonBuilde.getString("url", "");
                int emojiId = jsonBuilde.getInt("emoji", 0);
                path = jsonBuilde.getString("path", "");


                extras = extras == null ? new HashMap<>() : extras;
                extras.put("emojiId", emojiId);
                extras.put("size", FileUtil.formatSize(jsonBuilde.getLong("odsz")));

                int width = jsonBuilde.getInt("tw", 0);
                int height = jsonBuilde.getInt("th", 0);
//                int thumbnailWidth = jsonBuilde.getInt("tw", 0);
//                int thumbnailheight = jsonBuilde.getInt("th", 0);
                if (width == 0 && height == 0) {
                    width = jsonBuilde.getInt("ow", 0);
                    height = jsonBuilde.getInt("oh", 0);
                }
                extras.put("width", width);
                extras.put("height", height);
                break;
            }



        }
        if (opt == 4) {
            JsonBuilde reply = jsonBuilde.getJsonBuilde("reply");
            int stp = 0;
            int mtp = reply.getInt("mtp");//
            int mid = reply.getInt("mid");//
            int toid = reply.getInt("toid");//
            String text = reply.getString("msg").replace("\r", "");
            text =text.replace("\n", "");
            text =text.replace(" ", "");
            text = text.replace("\t", "");
            String param = reply.getString("param");//
            int sid = reply.getInt("sid");//
            long st = 0;
            int rd = 0;
            int ord = 0;
            int tid = 0;
            int opt = reply.getInt("opt");//
            MsgBean msgBean = new MsgBean(stp, mtp, mid, toid, text, param, sid, st, rd, ord, tid);
            msgBean.setOpt(opt);
            extras.put("reply", msgBean);
        }

    }

    public void initItemType() {

        if (getItemType() == -1) {
            switch (getMtp()) {
                case CONTENT_TYPE_TEXT:
                case CONTENT_ATE:
                case CONTENT_TYPE_IMG:
                case CONTENT_MSG_EMOJITION:
                case CONTENT_TYPE_VIDEO:
                case CONTENT_TYPE_VOICE:
                case CONTENT_CALL_MSG:
                case CONTENT_TYPE_FILE: {
                    if (Common.getI().getCurrentUserID() == getSid()) {
                        itemType = getRightType(mtp);
                    } else {
                        itemType = getLeftType(mtp);
                    }
                    break;
                }
                case CONTENT_NOTICE:
                case CONTENT_JOIN_GROUP:
                case CONTENT_REMOVE_ALL_GROUP_MSG:
                case CONTENT_JOIN_TEAM: {
                    if (getMtp() == CONTENT_REMOVE_ALL_GROUP_MSG) {
                        msg = "历史消息已被管理员清除";
                    }
                    itemType = MessageType.EVENT.ordinal();
                    break;
                }
                case CONTENT_NOTICE_ONE: {
                    itemType = MessageType.HEAD_NOTICE.ordinal();
                    break;
                }
                case CONTENT_UNREAD_LINE: {
                    itemType = MessageType.UNREAD_LINE.ordinal();
                    break;
                }
                case CONTENT_TIME: {
                    itemType = MessageType.TIME.ordinal();
                    break;
                }
            }
        }
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getStp() {
        return stp;
    }

    public int getMtp() {
        return mtp;
    }

    public int getMid() {
        return mid;
    }

    public void setStp(int stp) {
        this.stp = stp;
    }

    public CharSequence getMsg() {
        if (mtp == MsgBean.CONTENT_TYPE_TEXT || mtp == MsgBean.CONTENT_ATE && !isParseEmotion) {
            int msgTextSize = (int) App.getContext().getResources().getDimension(R.dimen.x32);
            String str = this.msg.toString();
            this.msg = EmojiParser.parseToUnicode(App.getContext(), str);
            SpannableStringBuilder builder = new SpannableStringBuilder(this.msg);
            EmojiconHandler.addEmojis(App.getContext(), builder, msgTextSize, DynamicDrawableSpan.ALIGN_BASELINE, msgTextSize);
            this.msg = SpanStringUtils.getEmotionContentAll(App.getContext(), null, builder);
            SpannableStringBuilder spannable = (SpannableStringBuilder) this.msg;

            List<RegexBean> urlRegexs = SpanUtil.getRegex(spannable, SpanUtil.REGEX_URL);
            regexs(spannable, urlRegexs, TouchableSpan.TYPE_WEB);
            List<RegexBean> phoneRegexs = SpanUtil.getRegex(spannable, SpanUtil.REGEX_Phone,urlRegexs);
            regexs(spannable, phoneRegexs, TouchableSpan.TYPE_PHONE);

            msg = spannable;
            isParseEmotion = true;
        }
        return msg;
    }


    /**
     * 超链接点击
     *
     * @param spannable
     * @param regexs
     * @param type
     */
    private void regexs(SpannableStringBuilder spannable, List<RegexBean> regexs, int type) {
        for (RegexBean bean : regexs) {
            TouchableSpan touchableSpan = new TouchableSpan(type, bean.url, ChatSingleFragment.sOnSpanClickListener);
            spannable.setSpan(touchableSpan, bean.start,
                    bean.end, spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    public String getParam() {
        return param;
    }

    public int getSid() {
        return sid;
    }

    public long getSt() {
        return st;
    }

    public int getRd() {
        return rd;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "MsgBean{" +
                "itemType=" + itemType +
                ", isParseEmotion=" + isParseEmotion +
                ", duration=" + duration +
                ", stp=" + stp +
                ", mtp=" + mtp +
                ", mid=" + mid +
                ", toid=" + toid +
                ", msg=" + msg +
                ", param='" + param + '\'' +
                ", sid=" + sid +
                ", st=" + st +
                ", rd=" + rd +
                ", tid=" + tid +
                ", state=" + state +
                ", progress=" + progress +
                ", url='" + url + '\'' +
                '}';
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public MsgBean(int stp, int mtp, int mid, int toid, String msg, String param, int sid, long st, int rd, int ord, int tid) {
        itemType = -1;
        this.stp = stp;
        this.mtp = mtp;
        this.mid = mid;
        this.toid = toid;
        this.msg = msg;
        this.param = param;
        this.sid = sid;
        this.st = st;
        this.rd = rd;
        this.tid = tid;
        this.ord = ord;
        initParam();
        initItemType();
    }

    public MsgBean(int stp, int mtp, int mid, int toid, String msg, String param, int sid, long st, int rd, int ord, int tid,int opt,int local) {
        itemType = -1;
        this.stp = stp;
        this.mtp = mtp;
        this.mid = mid;
        this.toid = toid;
        this.msg = msg;
        this.param = param;
        this.sid = sid;
        this.st = st;
        this.rd = rd;
        this.tid = tid;
        this.ord = ord;
        this.opt = opt;
        initParam();
        initItemType();
    }

    public MsgBean(int stp, int mtp, int mid, int toid) {
        itemType = -1;
        this.stp = stp;
        this.mtp = mtp;
        this.mid = mid;
        this.toid = toid;
        this.msg = "";
        this.param = "";
        this.sid = Common.getI().getCurrentUserID();
        this.st = TimeUtils.getServiceTime();
        this.rd = 1;
        this.tid = Common.getI().getCurrentTeamID();
        initParam();
        initItemType();

    }

    public int getToid() {
        return toid;
    }

    public int getTid() {
        return tid;
    }


    public void setRd(int rd) {
        this.rd = rd;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    @Override
    public int getMsgId() {
        return mid;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public void getFromUser(IUserDataCallBack iUserDataCallBack) {
        Common.getI().getContactInfo(sid, contactInfoBeanDB -> {
            IUser iUser = new IUser() {
                @Override
                public int getId() {
                    return sid;
                }

                @Override
                public String getDisplayName() {
                    return contactInfoBeanDB.getUname();
                }

                @Override
                public String getAvatarFilePath() {
                    return Constant.thumbnailImageUrl(contactInfoBeanDB.getAvatar()).toString();
                }
            };
            iUserDataCallBack.onArrive(iUser);
        });
    }

    @Override
    public String getTimeString() {
        return TimeUtils.formatChatTimeRecent(st);
    }

    @Override
    public int getType() {
        initItemType();
        return itemType;
    }

    private int getRightType(int mtp) {

        switch (mtp) {
            case CONTENT_TYPE_TEXT:
            case CONTENT_ATE: {
                return MessageType.SEND_TEXT.ordinal();
            }
//            case CONTENT_TYPE_VIDEO:
            case CONTENT_TYPE_FILE: {
                return MessageType.SEND_FILE.ordinal();
            }
            case CONTENT_TYPE_IMG: {
                return MessageType.SEND_IMAGE.ordinal();
            }
            case CONTENT_TYPE_VIDEO: {
//                Object o = extras.get("name");
//                if (o != null && o instanceof String) {
//                    String fileScheme = FileUtil.getFileScheme((String) o);
//                    LogX.e(fileScheme);
//                    if (fileScheme.equals("mp4")) {
//                        return MessageType.SEND_VIDEO.ordinal();
//                    }
//
//                }
                return MessageType.SEND_FILE.ordinal();
            }
            case CONTENT_TYPE_VOICE: {
                return MessageType.SEND_VOICE.ordinal();
            }
            case CONTENT_MSG_EMOJITION: {
                return MessageType.SEND_EMOTION.ordinal();
            }
            case CONTENT_CALL_MSG: {
                return MessageType.SEND_CALL.ordinal();
            }
        }
        return 0;
    }

    /**
     * @param mtp
     * @return
     */
    private int getLeftType(int mtp) {
        switch (mtp) {
            case CONTENT_TYPE_TEXT:
            case CONTENT_ATE: {
                return MessageType.RECEIVE_TEXT.ordinal();
            }
//            case CONTENT_TYPE_VIDEO:
            case CONTENT_TYPE_FILE: {
                return MessageType.RECEIVE_FILE.ordinal();
            }
            case CONTENT_TYPE_IMG: {
                return MessageType.RECEIVE_IMAGE.ordinal();
            }
            case CONTENT_TYPE_VIDEO: {
//                Object o = extras.get("name");
//                if (o != null && o instanceof String) {
//                    String fileScheme = FileUtil.getFileScheme((String) o);
//                    LogX.e(fileScheme);
//                    if (fileScheme.equals("mp4")) {
//                        return MessageType.RECEIVE_VIDEO.ordinal();
//                    }
//
//                }
                return MessageType.RECEIVE_FILE.ordinal();
            }
            case CONTENT_TYPE_VOICE: {
                return MessageType.RECEIVE_VOICE.ordinal();
            }
            case CONTENT_MSG_EMOJITION: {
                return MessageType.RECEIVE_EMOTION.ordinal();
            }
            case CONTENT_CALL_MSG: {
                return MessageType.RECEIVE_CALL.ordinal();
            }
        }
        return 0;
    }

    @Override
    public MessageStatus getMessageStatus() {
        switch (state) {
            case STATE_SUCCEED: {
                return MessageStatus.SEND_SUCCEED;
            }
            case STATE_FAILURE: {
                return MessageStatus.SEND_FAILED;
            }
            case STATE_SEND: {
                return MessageStatus.SEND_GOING;
            }
            case STATE_DEL: {
//                return MessageStatus.SEND_DRAFT;
            }
        }

        return MessageStatus.SEND_SUCCEED;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public CharSequence getText() {
        return getMsg();
    }

    @Override
    public String getMediaFilePath() {
        if (!TextUtils.isEmpty(path)) {
            return path;
        }
        if (mtp == CONTENT_TYPE_VIDEO) {
            return Constant.fileUrl(TextUtils.isEmpty(url) ? "" : url).toString();

        }
        return Constant.thumbnailImageUrl(TextUtils.isEmpty(url) ? "" : url).toString();
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public float getProgress() {
        return progress;
    }

    @Override
    public int getUiType() {
        return uiType;
    }

    @Override
    public boolean isMsgOnRead() {
        return ord == 1;
    }

    @Override
    public HashMap<String, Object> getExtras() {
        return extras;
    }

    @Override
    public int getSendId() {
        return sid;
    }

    @Override
    public int getDialogueId() {
        return toid;
    }


    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public void setParam(String param) {
        this.param = param;
        initParam();
    }

    public void setOpt(MsgBean quoteMsg) {
        switch (mtp) {
//            case 9: {
//                opt = 1;
//                break;
//            }
            case 10: {
                opt = 2;
                break;
            }
            case 11: {
                opt = 3;
                break;
            }
            default: {
                if (quoteMsg != null) {

                    JSONObject quoteString = quoteMsg.getQuoteString();


                    opt = 4;
                    param = JsonUtils.newJson(param)
                            .put("reply", quoteString)
                            .toString();
                    extras = extras == null ? new HashMap<>(): extras;
                    extras.put("reply", quoteMsg);

                }
            }
        }
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public void setUiType(int uiType) {
        this.uiType = uiType;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public int getOrd() {
        return ord;
    }

    public void setMsg(CharSequence msg) {
        this.msg = msg;
    }

    public void setSt(long st) {
        this.st = st;
    }

    public JSONObject getQuoteString() {

        String text = msg.toString().replace("\r", "");
        text =text.replace("\n", "");
        text =text.replace(" ", "");
        text = text.replace("\t", "");
        return JsonUtils.newJson()
                .put("mtp", getMtp())
                .put("opt", getOpt())
                .put("mid", getMid())
                .put("sid", getSid())
                .put("toid", getToid())
                .put("msg", text)
                .put("param", getParam())
                .end();
    }
    public MsgBean getQuoteCopyMsg() {

        String text = msg.toString().replace("\r", "");
        text =text.replace("\n", "");
        text =text.replace(" ", "");
        text = text.replace("\t", "");

        MsgBean msgBean = new MsgBean(stp, mtp, mid, toid,text , param, sid, st, rd, ord, tid, opt, localTag);
        msgBean.setState(state);
        return msgBean;
    }

    @Override
    public int compareTo(@NonNull MsgBean o) {

        return ((Long) this.getSt()).compareTo(o.st);
    }

    public void setMtp(int mtp) {
        this.mtp = mtp;
    }

    public void setParseEmotion(boolean parseEmotion) {
        isParseEmotion = parseEmotion;
    }

    public int getOpt() {
        return opt;
    }

    public int getLocalTag() {
        return localTag;
    }

    public void setLocalTag(int localTag) {
        this.localTag = localTag;
    }
}
