package com.sun.supportcomment.myHandler;


public class Looper {
    final MessageQueue messageQueue;
    private static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();

    public Looper() {
        messageQueue = new MessageQueue();//消息队列
    }

    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        threadLocal.set(new Looper());
        System.out.println("looper初始化");
    }

    public static Looper myLooper() {
        return threadLocal.get();
    }
    //无限循环取消息
    public static void loop() {
        Looper me = myLooper();
        Message msg;
        for (; ; ) {
            msg = me.messageQueue.next();
            if (msg == null || msg.target == null){
                System.out.println("Looper：空消息");
                continue;
            }
            System.out.println("Looper：looper轮询到了消息，发送消息");
            msg.target.dispatchMessage(msg);
        }
    }
}
