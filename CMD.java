package com.xmen.xteam.communication;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/3
 * 描    述：
 */
public class CMD {

    public static final int LOGIN_1 = 1;// 客户端请求登录
    public static final int RESPONSE_LOGIN_2 = 2;// 服务器返回登录信息


    public static final int REGISTER_3 = 3;// 客户端请求注册
    public static final int RESPONSE_REGISTER_4 = 4;// 服务器返回注册信息

    public static final int REGISTER_VERIFY_5 = 5;// 客户端请求注册 有没有被注册过 校验 如果没有则发送短信
    public static final int REGISTER_RESPONSE_VERIFY_CODE_6 = 6;// 服务器返回注册用户身份验证码校验信息

    public static final int VERIFY_MOBILE_7 = 7;// 客户端请求发送验证码
    public static final int RESPONSE_VERIFY_MOBILE_8 = 8;// 服务器回应发送验证码信息


    public static final int RESET_PASSWORD_9 = 9;// 重置密码
    public static final int RESPONSE_RESET_PASSWORD_10 = 10;// 重置密码结果

    public static final int REQUEST_COUNTRY_CODE_11 = 11;// 请求国际区号接口

    public static final int REQUEST_COUNTRY_CODE_RESULT_12 = 12;// 返回国际区号结果；


    public static final int JUDGE_USER_IS_EXIST_13 = 13;// 客户端请求用户是否存在 登录服
    public static final int RESPONSE_JUDGE_USER_IS_EXIST_14 = 14;//

    public static final int ICON_VERIFY_15 = 15;//
    public static final int RESPONSE_ICON_VERIFY_15 = 16;//

    public static final int VERIFY_USER_PROTECT_19 = 19;//
    public static final int RESPONSE_VERIFY_USER_PROTECT_20 = 20;//


    public static final int ICON_VERIFY_SEND_CODE_17 = 17;//
    public static final int RESPONSE_ICON_VERIFY_SEND_CODE_18 = 18;//

    public static final int TEAMS_INFO_50 = 50;// 服务器回应同步团队昵称头像信息等
    public static final int RESPONSE_TEAMS_INFO_51 = 51;//

    public static final int REFRESH_TEAM_INFO_52 = 52;//   客户端请求同步团队里群组、用户昵称头像信息等
    public static final int RESPONSE_REFRESH_TEAM_INFO_53 = 53;//

    public static final int NEW_DIALOGUE_INFO_54 = 54;//  客户端请求同步最新会话消息（54）
    public static final int RESPONSE_NEW_DIALOGUE_INFO_55 = 55;//

    public static final int SEND_MSG_56 = 56;  //发送消息
    public static final int RESPONSE_SEND_MSG_57 = 57;

    public static final int TEAM_CREATE_58 = 58;// 客户端请求创建团队
    public static final int RESPONSE_TEAM_CREATE_59 = 59;//

    public static final int JUDGE_USER_IS_EXIST_60 = 60;// 客户端请求用户是否存在
    public static final int RESPONSE_JUDGE_USER_IS_EXIST_61 = 61;//


    public static final int TEAM_INVITATION_62 = 62;//  客户端请求把联系人加入团队
    public static final int RESPONSE_TEAM_INVITATION_63 = 63;//

    public static final int TEAM_JOIN_66 = 66;//  客户端请求把联系人加入团队
    public static final int RESPONSE_TEAM_JOIN_67 = 67;//

    public static final int TEAM_GET_INFO_64 = 64;// 获取团队信息
    public static final int RESPONSE_TEAM_GET_INFO_65 = 65;//

    public static final int TEAM_CHANGE_INFO_68 = 68;// 修改团队信息
    public static final int RESPONSE_TEAM_CHANGE_INFO_69 = 69;//

    public static final int TEAM_QUIT_70 = 70;// 退出团队
    public static final int RESPONSE_TEAM_QUIT_71 = 71;//

    public static final int INVITE_USER_REGISTER_72 = 72;//  邀请用户注册
    public static final int RESPONSE_INVITE_USER_REGISTER_73 = 73;//

    public static final int VERIFY_TOKEN_74 = 74;// 校验token
    public static final int RESPONSE_VERIFY_TOKEN_75 = 75;//

    public static final int REFRESH_CHAT_UNREAD_76 = 76;//  客户端请求更新已读消息
    public static final int RESPONSE_REFRESH_CHAT_UNREAD_77 = 77;//

    //旧接口
    public static final int GET_USER_INFO_78 = 78;//  客户端请求获取指定用户信息
    public static final int RESPONSE_GET_USER_INFO_79 = 79;//
    //新接口


    public static final int GET_USER_INFO_174 = 174;//  客户端请求获取指定用户信息
    public static final int RESPONSE_GET_USER_INFO_175 = 175;//

    public static final int GET_GROUP_INFO_82 = 82;//  客户端请求获取指定群信息
    public static final int RESPONSE_GET_GROUP_INFO_83 = 83;//

    public static final int CREATE_GROUP_84 = 84;//  客户端请求创建群
    public static final int RESPONSE_CREATE_GROUP_85 = 85;//

    public static final int GET_GROUP_MEMBER_86 = 86;// 客户端请求获取群成员
    public static final int RESPONSE_GET_GROUP_MEMBER_87 = 87;//

    public static final int GET_TEAM_MEMBER_88 = 88;// 客户端请求获取群成员
    public static final int RESPONSE_GET_TEAM_MEMBER_89 = 89;//

    public static final int ADD_GROUP_MEMBER_90 = 90;// 客户端请求添加群成员
    public static final int RESPONSE_ADD_GROUP_MEMBER_91 = 91;//

    public static final int QUIT_GROUP_92 = 92;// 客户端请求退出群
    public static final int RESPONSE_QUIT_GROUP_93 = 93;//

    public static final int CHANGE_GROUP_INFO_94 = 94;//  客户端请求修改群信息
    public static final int RESPONSE_CHANGE_GROUP_INFO_95 = 95;//

    public static final int DEL_GROUP_MEMBER_96 = 96;//   服务器回应删除群成员
    public static final int RESPONSE_DEL_GROUP_INFO_97 = 97;//

    public static final int ADD_GROUP_MANAGE_98 = 98;//   客户端请求添加群管理员（98）
    public static final int RESPONSE_ADD_GROUP_MANAGE_99 = 99;//

    public static final int CHANGE_USER_INFO_100 = 100;//   客户端请求修改个人信息
    public static final int RESPONSE_CHANGE_USER_INFO_101 = 101;//

    public static final int CHANGE_USER_INFO_176 = 176;//   客户端请求修改个人信息
    public static final int RESPONSE_CHANGE_USER_INFO_177 = 177;//

    public static final int GET_TEAM_GROUP_INFO_102 = 102;//   客户端请求团队里的群组信息及状态信息（102）
    public static final int RESPONSE_GET_TEAM_GROUP_INFO_103 = 103;//

    public static final int GET_TEAM_USER_INFO_104 = 104;//  户端请求团队里的用户信息及状态信息
    public static final int RESPONSE_GET_TEAM_USER_INFO_105 = 105;//

    public static final int GET_GROUP_USER_INFO_106 = 106;//  户端请求群里的用户信息及状态信息
    public static final int RESPONSE_GET_GROUP_USER_INFO_107 = 107;//

    public static final int SET_CONFIG_115 = 115;//  客户端请求用户设置全局配置（115）
    public static final int RESPONSE_SET_CONFIG_116 = 116;//

    public static final int SET_USER_CONFIG_117 = 117;//  客户端请求用户设置会话配置（117）
    public static final int RESPONSE_SET_USER_CONFIG_118 = 118;//

    public static final int REQUEST_YZM_SET_PHOTO_119 = 119;//修改手机号码，请求服务器获取验证码

    public static final int SET_PHOTO_NOMBER_121 = 121;//验证修改手机号的验证码

    public static final int REQUEST_COMMIT_FEEDBACK_123 = 123;//提交意见反馈；

    public static final int REQUEST_SHARE_MEDIA_PICTURE_127 = 127;//请求服务端获取共享媒体的图片信息；
    public static final int ANSWER_SHARE_MEDIA_PICTURE_128 = 128;//服务端回应请求结果解析；

    public static final int SEARCH_SHARE_FILES_129 = 129;//共享文件搜索
    public static final int RESULT_SEARCH_SHARE_FILES_130 = 130;//服务端回应共享文件搜索；

    public static final int KICK_MEMBER_131 = 131;//客户端请求删除团队成员（131）
    public static final int RESULT_KICK_MEMBER_132 = 132;//服务端回应删除团队成员（132）


    public static final int ADD_OR_CANCEL_ADMIN_136 = 136;//客户端请求删除团队成员（131）
    public static final int RESULT_ADD_OR_CANCEL_ADMIN_137 = 137;//服务端回应删除团队成员（132）

    public static final int HISTORY_CHAT_DATA_134 = 134;//客户端请求历史会话消息（134）
    public static final int RESULT_HISTORY_CHAT_DATA_135 = 135; //服务端返回历史会话消息（135）


    public static final int UPDATE_APP_142 = 142;//更新版本 142
    public static final int RESULT_UPDATE_APP_143 = 143; //服务端返回历史会话消息（135）

    public static final int GET_QRCODE_146 = 146; //客户端请求团队二维码（146）
    public static final int RESULT_GET_QRCODE_147 = 147; // 服务端回应请求团队二维码（147）

    public static final int QRCODE_REQUEST_TID_148 = 148; //客户端根据二维码请求团队ID（148）
    public static final int RESULT_QRCODE_REQUEST_TID_149 = 149; // 服务端回应请求团队ID（149）

    public static final int BATCH_DELETE_MSG_152 = 152; //客户端根据二维码请求团队ID（148）
    public static final int RESULT_BATCH_DELETE_MSG_153 = 153; // 服务端回应请求团队ID（149）


    public static final int GET_EMOTION_156 = 156; // 客户端请求自定义表情(156)
    public static final int RESULT_GET_EMOTION_157 = 157; // 服务器回应请求自定义表情(157)
    public static final int PAGE_GET_EMOTION_182 = 182; // 服务器回应请求自定义表情(157)

    public static final int ADD_EMOTION_158 = 158; //  客户端请求添加自定义表情(158)
    public static final int RESULT_ADD_EMOTION__159 = 159; //服务器回应添加自定义表情(159)

    public static final int DEL_EMOTION_160 = 160; //  客户端请求删除自定义表情(160)
    public static final int RESULT_DEL_EMOTION_161 = 161; //服务器回应删除自定义表情(161)


    public static final int S_TO_PUSH_OTHER_LOGIN_1002 = 1002;//在其他地方被登录
    public static final int S_TO_PUSH_MSG_LIST_ARRIVE_80 = 80;//  收到服务器
    public static final int S_TO_PUSH_MSG_ARRIVE_81 = 81;//服务器推送消息
    public static final int S_TO_PUSH_MSG_READ_INFO_108 = 108;//服务器主动推送更新已读会话消息（108）
    public static final int S_TO_PUSH_USER_INFO_109 = 109;//服务器主动推送用户基础信息更新（109）
    public static final int S_TO_PUSH_TEAM_INFO_110 = 110;//服务器主动推送团队基础信息更新（110）
    public static final int S_TO_PUSH_GROUP_INFO_111 = 111;//服务器主动推送群组基础信息更新（111）
    public static final int S_TO_PUSH_TEAM_MEMBER_STATE_112 = 112;//服务器主动推送团队成员状态信息更新（112）
    public static final int S_TO_PUSH_GROUP_MEMBER_STATE_113 = 113;// 服务器主动推送群成员状态信息更新（113）
    public static final int S_TO_PUSH_GROUP_MEMBER_STATE_114 = 114;// 服务器主动推送用户设置的配置信息（114）
    public static final int S_TO_PUSH_TEAM_OR_GROUP_STATE_CHANGE_133 = 133;// 团队或者成员状态改变
    public static final int S_TO_PUSH_ADD_EMOTION_162 = 162;// 服务器推送添加自定义表情(162)
    public static final int S_TO_PUSH_DEL_EMOTION_163 = 163;//  服务器推送删除自定义表情(163)
    public static final int S_TO_PUSH_PC_STATE_214 = 214;// 服务器主动推送登陆状态信息（214）

    public static final int S_TO_PUSH_TEAM_USERINFO_178 = 178;//  服务器推送用户团队个人信息更新（178）


    public static final int NEW_DIALOGUE_INFO_164 = 164;// 客户端请求会话列表（164）
    public static final int RESULT_NEW_DIALOGUE_INFO_165 = 165;// 服务端回应请求会话列表（165）
    public static final int PAGE_NEW_DIALOGUE_INFO_166 = 166;// 服务端推送会话列表（166）

    public static final int GET_CHAT_MSG_172 = 172;//  客户端请求获取指定用户信息
    public static final int RESPONSE_GET_CHAT_MSG_173 = 173;//    //新接口
    public static final int PAGE_CHAT_MSG_179 = 179;//    //新接口


    public static final int ADD_COLLECT_185 = 185;//  客户端请求添加收藏
    public static final int RESPONSE_ADD_COLLECT_186 = 186;//

    public static final int DEL_COLLECT_187 = 187;//  客户端请求删除收藏（187）
    public static final int RESPONSE_DEL_COLLECT_188 = 188;//    //新接口

    public static final int SYNCHRONIZE_COLLECT_189 = 189;//  同步收藏数据
    public static final int RESPONSE_SYNCHRONIZE_COLLECT_190 = 190;//

    public static final int GET_TEAM_USER_INFO_167 = 167;//  服务器推送删除自定义表情(167)
    public static final int RESULT_GET_TEAM_USER_INFO_168 = 168;//   服务器回应同步团队里群组、用户昵称头像信息等（168）
    public static final int PAGE_GET_TEAM_USER_INFO_169 = 169;//  服务器推送团队里群组、用户昵称头像信息等（169）


    public static final int REQUEST_COUNTRY_CODE_180 = 180;// 请求国际区号接口
    public static final int REQUEST_COUNTRY_CODE_RESULT_181 = 181;// 请求国际区号接口
    public static final int GET_USER_PHONE_EMAIL_183 = 183;//  客户端请求指定用户手机邮箱（183）
    public static final int RESULT_GET_USER_PHONE_EMAIL_184 = 184;//   服务器回应指定用户手机邮箱（184）


    public static final int DISSOLVE_GROUP_191 = 191;// 客户端请求退出群
    public static final int RESPONSE_DISSOLVE_GROUP_192 = 192;//
    public static final int S_TO_PUSH_DISSOLVE_GROUP_193 = 193;//


    public static final int DISSOLVE_TEAM_194 = 194;// 客户端请求退出群
    public static final int RESPONSE_DISSOLVE_TEAM_195 = 195;//
//    public static final int S_TO_PUSH_DISSOLVE_TEAM_196 = 196;//


    public static final int CHANGE_PSW_196 = 196;
    public static final int RESPONSE_CHANGE_PSW_197 = 197;


    public static final int SET_USER_PROTECT_198 = 198;//客户端请求设置账号保护（198）
    public static final int RESPONSE_SET_USER_PROTECT_199 = 199;//服务端回应请求设置账号保护（199）

    public static final int GET_USER_PROTECT_200 = 200;//客户端请求设置账号保护配置信息（200）
    public static final int RESPONSE_GET_USER_PROTECT_201 = 201; //服务端回应请求设置账号保护配置信息（201）


    public static final int GET_DOWNLOAD_FILE_TIMESTAMP_212 = 212;//客户端下载文件时请求验证码时间戳
    public static final int RESPONSE_GET_DOWNLOAD_FILE_TIMESTAMP_213 = 213; //服务器回应下载文件时请求验证码时间戳（213）

    public static final int GET_QUIT_PC_223 = 223;//客户端请求退出X.Team的WEB、PC端（223）
    public static final int RESPONSE_QUIT_PC_224 = 224; //服务器回应退出X.Team的WEB、PC端（224）
    public static final int CHANGE_GLOBAL_NOTIFICATION_225 = 225;//客户端请求修改全局手机通知配置（225）
    public static final int RESPONSE_CHANGE_GLOBAL_NOTIFICATION_226 = 226; //服务器回应修改全局手机通知配置（226）
    public static final int GET_PC_LOGIN_227 = 227;//客户端请求绑定登陆二维码（227）
    public static final int RESPONSE_PC_LOGIN_228 = 228; //服务器回应绑定登陆二维码（228）

    public static final int LOGOUT_229 = 229;//退出登录
    public static final int RESPONSE_LOGOUT_230 = 230; //服务器回应绑定登陆二维码（230）

    public static final int CREATE_ROBOT_231 = 231;//客户端请求分享邀请链接
    public static final int RESPONSE_CREATE_ROBOT_232 = 232; //232

    public static final int GET_ROBOT_TOKEN_233 = 233;// 客户端请求机器人小可爱的token（233）
    public static final int RESPONSE_CGET_ROBOT_TOKEN_234 = 234; //232

    public static final int ACTIVATE_ROBOT_235 = 235;//  客户端请求激活该群的小可爱（235）
    public static final int RESPONSE_ACTIVATE_ROBOT_236 = 236; //232

    public static final int SET_ONLINE_STATE_239 = 239;//客户端设置用户当前活动状态（237）
    public static final int RESPONSE_SET_ONLINE_STATE_240 = 240; //

    public static final int GET_ONLINE_STATE_241 = 241;//客户端请求用户当前活动状态（239）
    public static final int RESPONSE_GET_ONLINE_STATE_242 = 242; //服务器回应请求用户当前活动状态（240）
    public static final int PAGE_GET_ONLINE_STATE_243 = 243; //服务器推送用户当前活动状态（241）

    public static final int SET_USER_ONLINE_CONFIG_244 = 244;//客户端请求设置用户配置信息（242）
    public static final int RESPONSE_SET_USER_ONLINE_CONFIG_245 = 245; //

    public static final int GET_USER_ONLINE_CONFIG_246 = 246;//客户端请求用户配置信息（244）
    public static final int RESPONSE_GET_USER_ONLINE_CONFIG_247 = 247; //


    public static final int XLIST_TASK_249 = 249;//客户端请求添加、修改、删除xlist团队个人任务（
    public static final int RESPONSE_XLIST_TASK_250 = 250; //

    public static final int GET_XLIST_TASK_251 = 251;// 服务端回应请求添加、修改、删除xlist团队个人任务（250）
    public static final int RESPONSE_GET_XLIST_TASK_252 = 252; //客户端请求获取xlist团队个人任务列表（251）
    public static final int PAGE_GET_XLIST_TASK_253 = 253; //服务器回应请求获取xlist团队个人任务列表（252）

    public static final int ADD_OR_DEL_OSEERS_254 = 254;//客户端请求添加、删除xlist团队个人任务参与者（254）
    public static final int RESPONSE_ADD_OR_DEL_OSEERS_255 = 255; //

    public static final int GET_OSEERS_256 = 256;//客户端请求获取xlist团队个人任务参与者列表（256）
    public static final int RESPONSE_GET_OSEERS_257= 257; //

    public static final int DYNAMIC_ORDER_261 = 261;// 服务端回应请求动态口令设置、获取、刷新（262）
    public static final int RESPONSE_DYNAMIC_ORDER_262= 262; //


    public static final int CALL_REQUEST_263 = 263;//客户端请求团队用户语音视频（263）
    public static final int RESPONSE_CALL_REQUEST_264= 264; //
    public static final int S_TO_REQUEST_CALL_265= 265;//服务器推送客户端的团队用户语音视频请求（265）

    public static final int SIGNALLING_266 = 266;//客户端请求团队用户语音视频（263）
    public static final int RESPONSE_SIGNALLING_267 = 267; //
    public static final int S_TO_SIGNALLING_268= 268;//服务器推送客户端的团队用户语音视频请求（265）



    public static final int MULTI_CALL_273 = 273;//客户端请求团队用户语音视频会议（273）
    public static final int RESPONSE_MULTI_CALL_274 = 274; //
    public static final int S_TO_MULTI_CALL_275= 275;//服务器推送客户端的团队用户语音视频会议请求（265）


    public static final int MULTI_CALL_VOLUME_276 = 276;//客户端请求转发即时语音信号（276）
    public static final int RESPONSE_MULTI_CALL_VOLUME_277 = 277; //
    public static final int S_TO_MULTI_CALL_VOLUME_278= 278;//服务器推送客户端的团队用户语音视频会议请求（265）




    public static final int MULTI_CALL_UPSTATE_279 = 279;//客户端请求更新会议状态（279）
    public static final int RESPONSE_MULTI_CALL_UPSTATE_280 = 280; //


    public static final int GET_ALL_MULTI_CALL_USER_281 = 281;//客户端请求更新会议状态（279）
    public static final int RESPONSE_GET_ALL_MULTI_CALL_USER_282 = 282; //

    //文件服
    public static final int UPLOAD_FILE_REQUEST_1100 = 1100;// 客户端请求上传新文件(1100)
    public static final int RESPONSE_UPLOAD_FILE_REQUEST_1101 = 1101;//服务器回应上传新文件(1101)

    public static final int UPLOAD_FILE_DATA_1102 = 1102;// 客户端请求上传文件数据(1102) 客户端分块上传文件数据块给服务器
    public static final int RESPONSE_UPLOAD_FILE_DATA_1103 = 1103;//服务器回应上传新文件(1103)

    public static final int UPLOAD_FILE_CANCEL_1106 = 1106;// 客户端请求取消上传文件(1106)
    public static final int RESPONSE_UPLOAD_FILE_CANCEL_1107 = 1107;//服务器回应取消上传文件(1107)
}
