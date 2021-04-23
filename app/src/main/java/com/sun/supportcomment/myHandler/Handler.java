package com.sun.supportcomment.myHandler;


public class Handler {

    private Looper mlooper;
    private MessageQueue mQueue;
    //
    public Handler() {
        mlooper = Looper.myLooper();//构造looper
        mQueue = mlooper.messageQueue;
    }

    public void sendMessage(Message message){
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    public void handlerMessage(Message message){

    }

    public void dispatchMessage(Message message){
        handlerMessage(message);
    }
}
