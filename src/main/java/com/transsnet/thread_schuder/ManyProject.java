package com.transsnet.thread_schuder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author yinqi
 * @date 2021/2/5
 */
public class ManyProject {

    public static void main(String[] args) {
        //创建方法1的对象
        Mythread_thread firstThread=new Mythread_thread();
        firstThread.start();//开启线程
        System.out.println("********************************");

        /*//创建方法2的对象
        Mythread_run secondThread=new Mythread_run();
        //创建Thread类对象，把方法2类对象当做Thread类的构造方法的参数传递进去
        Thread second=new Thread(secondThread);
        second.start();//Thread对象开启新的线程
        System.out.println("********************************");*/

      /*  //main线程方法
        for (int i = 0; i < 10; i++) {
            System.out.println("main线程报数："+i);
        }*/
    }

}
/*创建新的线程：方法1==>继承Thread类*/
class Mythread_thread extends Thread{//1、继承Thread
    @Override
    public void run() {  //2、实现run方法
        // for (int i = 0; i < 10; i++) {//3、自定义执行语句
        //System.out.println("继承Thread类 --线程报数：");
        Timer timer = new Timer();
        //表示在3秒之后开始执行，并且每2秒执行一次
        timer.schedule(new MyTask2(),3000,20000);
        // }
    }
}

/*创建新的线程：方法2==>实现Runnable接口*/
class Mythread_run implements Runnable{//1、自定义类实现Runnable接口
    @Override
    public void run() {//2、重写run方法
        Timer timer = new Timer();
        //表示在3秒之后开始执行，并且每2秒执行一次
        timer.schedule(new MyTask1(),3000,2000);

    }
}

/**
 * 类描述：这个类代表一个定时任务
 * @author xiezd
 * 自定义定时任务，继承TimerTask
 *
 */
class MyTask1 extends TimerTask {

    //在run方法中的语句就是定时任务执行时运行的语句。
    public void run() {
        System.out.println("Hello!! 现在是："+new Date().toLocaleString());
    }
}
class MyTask2 extends TimerTask{

    //在run方法中的语句就是定时任务执行时运行的语句。
    public void run() {
        System.out.println(" 现在是："+new Date().toLocaleString());
    }

}
