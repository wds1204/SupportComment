package com.sun.supportcomment.myHandler;

public class Message {

    public int what;
    public Object object;
    public Handler target;//target 消息触发handle回调

    private static final Object sPoolSync = new Object();
    private static Message sPool;
    Message next;
    int flags;
    private static int sPoolSize = 0;

    public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0; // clear in-use flag
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
