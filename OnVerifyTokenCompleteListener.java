package com.xmen.xteam.communication;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/3/14
 * 描    述：
 */
public interface OnVerifyTokenCompleteListener {
    void intercept(NewIMSocket.VerifyTokenIntercept verifyTokenIntercept);
}
