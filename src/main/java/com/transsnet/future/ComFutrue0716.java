package com.transsnet.future;

import java.util.concurrent.*;

public class ComFutrue0716 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       /* CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future.complete("World");

        try {
            System.out.println("11");
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
       /* ExecutorService es =  Executors.newFixedThreadPool(1);

       // CompletableFuture<String> future =  CompletableFuture.supplyAsync(()->"yinqi",es);
         CompletableFuture<String> future1 =  new CompletableFuture();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("run");
                future1.complete("任务完成，返回结果");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("WANCEHNG");
            }
        }).start();

        System.out.println(future1.get());*/



      /*  //3
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
                *//*try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*//*
                return "result2";
            }, executorService);
            //这个证明GET方法会阻塞主线程 但是每个CompletableFuture 都是并行执行的 默认completableFuture这套使用异步任务的操作都是创建成了守护线程（守护线程在其他非守护线程全部退出的情况下不继续执行）
            System.out.println(completableFuture.get());
            System.out.println(future.get());

            System.out.println("主线程跑起来");

            executorService.shutdown();*/

//4
       /* final CompletableFuture<String> futureOne = CompletableFuture.supplyAsync(() -> {
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
        *//*CompletableFuture future = CompletableFuture.allOf(futureOne, futureTwo);
        System.out.println(future.get());*//*
        CompletableFuture completableFuture = CompletableFuture.anyOf(futureOne, futureTwo);
        System.out.println(completableFuture.get());*/

       /* //5

        CompletableFuture<String> futureOne = CompletableFuture.supplyAsync(() -> {
            System.out.println("this is first task");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread());
            //int a = 1 / 0;
            return "first";
        });
           Thread.sleep(400);
        futureOne.whenCompleteAsync((s, e) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            System.out.println(Thread.currentThread());

            System.out.println("正确输结果" + s);
            System.out.println(e);

        });
        System.out.println("test");
        Thread.sleep(1000);*/

        //6
        CompletableFuture<String> futureOne = CompletableFuture.supplyAsync(() -> {
            return "first";});

        Thread.sleep(1000);
        CompletableFuture<Integer> future = futureOne.thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println("上一个任务返回结果：" + s);
            System.out.println(Thread.currentThread());
            return 1;
        }));
        Thread.sleep(100);
        System.out.println(future.get());



        }
}
