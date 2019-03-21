/*
 * Copyright (c) 2019. For Pheiot com
 */

package com.pheiot.phecloud.utils;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.LinkedBlockingQueue;

public class EventBusUtils {

    private static EventBus eventBus;
    private static LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue();
    private static Boolean isPost = false;

    static {
        if (eventBus == null) {
//            final int maxPoolSize = 50;
//            final long keepAliveTime = 3600L;
//            final int capacity = 100;
//            ExecutorService threadPool = new ThreadPoolExecutor(1, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(capacity));
//            eventBus = new AsyncEventBus(threadPool);
            eventBus = new EventBus();
        }
    }

    private static EventBus getInstance() {
        return eventBus;
    }

    /**
     * Register Server Event to bus.
     *
     * @param object index.
     */
    public static void register(Object object) {
        getInstance().register(object);
    }

    /**
     * Post Event with Server Event.
     *
     * @param object target.
     */
    public static synchronized void post(Object object) {
        try {
            linkedBlockingQueue.put(object);
            while (linkedBlockingQueue.size() != 0) {
                try {
                    postMsg();
                } catch (StackOverflowError error) {
                    error.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void postMsg() {
        try {
            if (!isPost) {
                isPost = true;
                if (linkedBlockingQueue.size() != 0) {
                    getInstance().post(linkedBlockingQueue.take());
                    isPost = false;
                } else {
                    isPost = false;
                }
            } else {
                try {
                    postMsg();
                } catch (StackOverflowError error) {
                    error.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
