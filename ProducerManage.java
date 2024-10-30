package com.xmen.xteam.communication;

import java.util.Vector;

/**
 * 创 建 者:  lwh
 * 创建时间:  2018/7/21
 * 描    述： 生产 消费模式  用于清空所有 生存
 */
public class ProducerManage {

    private Vector<Object> mMsgList = new Vector<>(); //发送消息的队列


    private static ProducerManage instance;

    private ProducerManage() {
    }

    public static ProducerManage getI() {
        if (instance == null) {
            instance = new ProducerManage();
        }
        return instance;
    }

    public void clear() {
        instance = null;
    }

    public void addProducer(Producers producers) {
        mMsgList.add(producers);
    }

    public void addProducer(Producer producers) {
        mMsgList.add(producers);
    }


    public void quit(){
        for (Object o : mMsgList) {
            if (o instanceof Producer) {
                ((Producer) o).removeAll();
            }else if(o instanceof Producers){
                ((Producers) o).removeAll();
            }
        }

    }

    public void stopReceive() {
        for (Object o : mMsgList) {
            if (o instanceof Producer) {
                ((Producer) o).stopReceive();
            }else if(o instanceof Producers){
                ((Producers) o).stopReceive();
            }
        }
    }
    public void openReceive() {
        for (Object o : mMsgList) {
            if (o instanceof Producer) {
                ((Producer) o).openReceive();
            }else if(o instanceof Producers){
                ((Producers) o).openReceive();
            }
        }
    }
}
