package com.xmen.xteam.communication;

import android.util.SparseArray;

import com.xmen.xteam.communication.bean.BaseSocketBean;

import java.util.List;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/2
 * 描    述：
 */
public interface OnSubpackageTransition {


    Object onTransition(SparseArray<List<BaseSocketBean>> page);
}
