package com.transsnet.thread_communicate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yinqi
 * @date 2021/2/4
 */
public class TestSyncNotify {

    public static void main(String[] args) {
        // 定义一个锁对象
        Object lock = new Object();
        List<String> list = new ArrayList<>();
        // 实现线程A
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                for (int i = 1; i <= 10; i++) {
                    list.add("abc");
                    System.out.println("线程A向list中添加一个元素，此时list中的元素个数为：" + list.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (list.size() == 5)
                        lock.notify();// 唤醒B线程
                }
            }
        });
        // 实现线程B
        Thread threadB = new Thread(() -> {
            while (true) {
                System.out.println("dsdsdsdsds");
                synchronized (lock) {

                        if (list.size() != 5) {
                            try {
                                System.out.println("dsdsdsdsds111111111111");
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("线程B收到通知，开始执行自己的业务...");
                }
            }
        });
        //　需要先启动线程B
        threadB.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 再启动线程A
        threadA.start();
    }

}
