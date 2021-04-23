package com.sun.supportcomment.myHandler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

    Message[] mItems;
    int mPutIndex;
    private int mCount;
    private int mTakeIndex;

    Lock mlock;
    Condition getCondition;
    Condition addCondition;

    public MessageQueue() {
        mItems = new Message[50];
        mlock = new ReentrantLock();
        getCondition = mlock.newCondition();
        addCondition = mlock.newCondition();
    }

    /**
     * 取消息出列
     * @return
     */
    Message next(){
        Message msg = null;
        try {
            mlock.lock();
            while (mCount <= 0){
                System.out.println("MessageQueue：队列空了，读锁阻塞");
                getCondition.await();
            }
            msg = mItems[mTakeIndex];
            mItems[mTakeIndex] = null;
            mTakeIndex = (++mTakeIndex >= mItems.length)?0:mTakeIndex;
            mCount--;

            addCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mlock.unlock();
        }

        return msg;

    }

    /**
     * 添加消息入列
     * @param message
     */
    public void enqueueMessage(Message message){

        try {
            mlock.lock();
            while (mCount >= mItems.length){
                System.out.println("MessageQueue:队列空了，写锁阻塞");
                addCondition.await();
            }
            mItems[mPutIndex] = message;
            mPutIndex = (++ mPutIndex >= mItems.length)?0:mPutIndex;
            mCount ++;
            getCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mlock.unlock();
        }

    }
}
