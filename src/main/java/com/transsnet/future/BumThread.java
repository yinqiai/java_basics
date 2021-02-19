package com.transsnet.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author yinqi
 * @date 2020/8/19
 */
public class BumThread extends Thread{
    @Override
    public void run() {
        /*try {
            Thread.sleep(1000*3);
            System.out.println("包子准备完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        // 等凉菜
        Callable ca1 = new Callable() {

            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000*6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "凉菜准备完毕";
            }
        };
        FutureTask<String> ft1 = new FutureTask<String>(ca1);
        Thread t1=new Thread(ft1);
       t1.start();

        // 等包子 -- 必须要等待返回的结果，所以要调用join方法
        Callable ca2 = new Callable() {

            @Override
            public Object call() throws Exception {
                try {
                    Thread.sleep(1000 * 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "包子准备完毕";
            }
        };
        FutureTask<String> ft2 = new FutureTask<String>(ca2);
        Thread t2=new Thread(ft2);
        t2.start();

        System.out.println(ft1.get());
        System.out.println(ft2.get());

        long end = System.currentTimeMillis();
        System.out.println("准备完毕时间：" + (end - start));

    }

    }
