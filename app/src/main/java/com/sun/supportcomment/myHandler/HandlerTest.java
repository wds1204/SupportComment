package com.sun.supportcomment.myHandler;

public class HandlerTest {
    Handler handler;
    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                 handler = new Handler() {
                    @Override
                    public void handlerMessage(Message message) {
                        super.handlerMessage(message);
                        System.out.println("Test:" + Thread.currentThread().getName() + "线程接收到：" + message.object);
                    }
                };
                Looper.loop();
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.object = Thread.currentThread().getName() + "发送了消息";
                handler.sendMessage(message);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.object = Thread.currentThread().getName() + "发送了消息";
                handler.sendMessage(message);
            }
        }).start();
    }

    public static void main(String[] args) {
        HandlerTest test = new HandlerTest();

        test.test();
    }
}
