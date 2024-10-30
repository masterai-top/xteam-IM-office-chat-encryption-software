package com.xmen.xteam.communication;

import com.xmen.xteam.communication.bean.BaseSocketBean;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public interface OnSucceedListener<T extends BaseSocketBean> {
    void onSucceed(T socketBean);
}
