package com.xmen.xteam.communication;

import com.xmen.xteam.communication.bean.BaseSocketBean;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述： 收到了结果 但是 可能是其他原因导致流程失败  比如 帐号被注册 手机号错误等等..
 */
public interface OnResultErrorListener {

    void onResultError(BaseSocketBean socketBean, int code, String errorStr);
}
