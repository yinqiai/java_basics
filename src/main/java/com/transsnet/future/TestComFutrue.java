package com.transsnet.future;

import java.util.concurrent.*;

/**
 * @author yinqi
 * @date 2020/10/23
 */
public class TestComFutrue {

    public static void testSupplyAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("executorService 是否为守护线程 :" + Thread.currentThread().isDaemon());
                return null;
            }
        });
        final CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("this is lambda supplyAsync");
            System.out.println("supplyAsync 是否为守护线程 " + Thread.currentThread().isDaemon());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("this lambda is executed by forkJoinPool");
            return "result1";
        });
        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("this is task with executor");
            System.out.println("supplyAsync 使用executorService 时是否为守护线程 : " + Thread.currentThread().isDaemon());
            return "result2";
        }, executorService);
        System.out.println(completableFuture.get());
        System.out.println(future.get());
        executorService.shutdown();
    }

    public static void testrunAsync() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("executorService 是否为守护线程 :" + Thread.currentThread().isDaemon());
                return null;
            }
        });
        final CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("this is lambda runAsync");
            System.out.println("runAsync 是否为守护线程 " + Thread.currentThread().isDaemon());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("this is runAsync method ");
        });
        final CompletableFuture future = CompletableFuture.runAsync(() -> {
            System.out.println("this is two runAsync method ");
            System.out.println("runAsync 使用executorService 时是否为守护线程 : " + Thread.currentThread().isDaemon());
        }, executorService);
        System.out.println(completableFuture.get());
        System.out.println(future.get());
        executorService.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //testSupplyAsync();
        //testrunAsync();

        //allofanyofcompletable();


        instanceMethod();

        //Thread.sleep(20000);

    }

    public static void allofanyofcompletable() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> futureOne = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("futureOne InterruptedException");
            }
            return "futureOneResult";
        });
        final CompletableFuture<String> futureTwo = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                System.out.println("futureTwo InterruptedException");
            }
            return "futureTwoResult";
        });
    /*    CompletableFuture future = CompletableFuture.allOf(futureOne, futureTwo);
        System.out.println(future.get());*/
      CompletableFuture completableFuture = CompletableFuture.anyOf(futureOne, futureTwo);
      System.out.println(completableFuture.get());

    }

    public static void instanceMethod() throws InterruptedException, ExecutionException {
           CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{ try {
               Thread.sleep(1000);
               System.out.println("one task");
           } catch (InterruptedException e) {
               System.out.println("instanceMethod InterruptedException");
           }
               return "instanceResult";});

       // return completableFuture.getNow("getNow");
        //阻塞主线程
        completableFuture.get();
        System.out.println("main thread");
       // System.out.println(completableFuture.getNow("getNow"));



    }



}
