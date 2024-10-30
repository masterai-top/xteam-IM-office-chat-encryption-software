package com.xmen.xteam.communication;

import android.os.SystemClock;

import java.util.Vector;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/3
 * 描    述：   生产 消费模式 控制器
 */
public abstract class Producer<T> implements Runnable {

    private Vector<T> mMsgList = new Vector<>(); //发送消息的队列
    private boolean isRun = true;
    private boolean isReceive = true;
    private int sleep = 0;

    public Producer(int sleep) {
        this.sleep = sleep;
    }

    public Producer() {
        this.sleep = 0;
    }

    public synchronized void stop() {
        isRun = false;
        this.notify();
    }

    @Override
    public void run() {
        while (isRun) {
            if (mMsgList.size() <= 0 ) {
                try {
                    synchronized (this) {
                        Producer.this.wait();//等待发送数据
                    }
                } catch (InterruptedException e) {
                    continue;
                }
            }
            if (sleep > 0) {
                SystemClock.sleep(sleep);
            }
            for (int i = 0; i < mMsgList.size(); i++) {
                T t = mMsgList.get(i);
                haveData(t);
            }
        }
    }

    public abstract void haveData(T remove);

    public void remove(T t) {
        mMsgList.remove(t);
    }

    public void removeAll() {
        mMsgList.removeAllElements();
    }

    public Vector<T> takeOutAll(){
//        Vector<T> msgList = mMsgList;
//        mMsgList = new Vector<>();
        return mMsgList;
    }


    public void stopReceive() {
        isReceive = false;
    }

    public void openReceive() {
        isReceive = true;
    }


    /**
     * 添加数据到发送队列
     */
    public synchronized void add(T item) {
        if (isRun && isReceive) {
            mMsgList.add(item);//将发送数据添加到发送队列
            Producer.this.notify();//取消等待
        }
    }
}
