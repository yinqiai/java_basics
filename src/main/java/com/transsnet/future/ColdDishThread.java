package com.transsnet.future;

/**
 * @author yinqi
 * @date 2020/8/19
 */
public class ColdDishThread extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("凉菜准备完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        // 等凉菜 -- 必须要等待返回的结果，所以要调用join方法
        Thread t1 = new ColdDishThread();
        t1.start();
        t1.join();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Thread t2 = new BumThread();
        t2.start();
        t2.join();

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间："+(end-start));

    }

}
