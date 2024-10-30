package com.xmen.xteam.communication;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/1/3
 * 描    述：  生产 消费模式 控制器
 */
public abstract class Producers<T> implements Runnable {

    private Vector<T> mList = new Vector<>(); //发送消息的队列
    private boolean isRun = true;
    private int sleep = 0;
    private int readCount = 5;
    private boolean isReceive = true;
    private Thread mThread;
    private String mThreadName;

    public Producers(int sleep) {
        this.sleep = sleep;
    }

    public Producers(int sleep, int readCount) {
        this.sleep = sleep;
        this.readCount = readCount;
    }

    public Producers() {
        this.sleep = 0;
    }

    public synchronized void stop() {
        isRun = false;
        this.notify();
    }

    @Override
    public void run() {
        while (isRun) {
            if (mList.size() <= 0) {
                try {
                    synchronized (this) {
                        Producers.this.wait();//等待发送数据
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }


            if (mList.size() <= 0) {
                continue;
            }


            ArrayList<T> tempList = fetchData(mList);
            haveData(tempList);

            if (sleep > 0) {
                SystemClock.sleep(sleep);
            }
        }
    }

    private ArrayList<T> fetchData(Vector<T> datas) {
        ArrayList<T> tempList = new ArrayList<>();
        for (int i = 0; i < readCount; i++) {
            if (datas.size() > 0) {
                //过滤重复语句
                T remove = datas.remove(0);
                if (!tempList.contains(remove)) {
                    tempList.add(remove);
                } else {
                    //移除重复语句 把语句放到最后执行
                    tempList.remove(remove);
                    tempList.add(remove);
                }
            } else {
                break;
            }
        }

        return tempList;
    }

    public abstract void haveData(ArrayList<T> remove);

    public void remove(T t) {
        mList.remove(t);
    }

    public void removeAll() {
        mList.removeAllElements();
    }

    public synchronized  void start(String threadName){
        mThreadName = threadName;
        mThread = new Thread(this);
        mThread.setName(mThreadName);
        mThread.start();
    }

    /**
     * 添加数据到发送队列
     */
    public synchronized void add(T item) {
        if (isRun) {
            if (!mThread.isAlive()) {
                mThread = new Thread(this);
                mThread.setName(mThreadName);
                mThread.start();
            }
            mList.add(item);//将发送数据添加到发送队列
            Producers.this.notify();//取消等待
        }
    }

    /**
     * 添加数据到发送队列
     */
    public synchronized void add(List<T> item) {
        if (isRun  && isReceive) {
            if (!mThread.isAlive()) {
                mThread = new Thread(this);
                mThread.setName(mThreadName);
                mThread.start();
            }
            mList.addAll(item);//将发送数据添加到发送队列
            Producers.this.notify();//取消等待
        }
    }


    public void stopReceive() {
        isReceive =false;
    }

    public void openReceive() {
        isReceive =true;
    }



}
