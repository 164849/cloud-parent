package com.itck.coupon.config;

import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 单例模式 封装线程池
 * @Author: zed
 * @Date: 2022/5/5 11:37
 */
public class ThreadPoolSignle {
    //单例模式-IoDH实现
    public ThreadPoolExecutor poolExecutor;

    private ThreadPoolSignle() {
        //七大参数
        poolExecutor = new ThreadPoolExecutor(4, 20, 3, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20), new DefaultManagedAwareThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

    private static class PoolSingle {
        private static ThreadPoolSignle signle = new ThreadPoolSignle();
    }

    public static ThreadPoolSignle getInstance() {
        return PoolSingle.signle;
    }
}