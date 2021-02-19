package com.transsnet.completable;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.util.stream.Collectors.toList;

/**
 * @author yinqi
 * @date 2020/11/9
 */
public class ClientTest {

    List<Shop> shops;
    List<Shop> discountShops;
    @Before
    public void before() {
        shops = Arrays.asList(new Shop("淘宝"),
                new Shop("天猫"),
                new Shop("京东"),
                new Shop("亚马逊"),
                new Shop("当当"),
        new Shop("天猫"),
                new Shop("京东"),new Shop("京东"),new Shop("京东"),new Shop("京东"),
                new Shop("京东"),
                new Shop("亚马逊"),
                new Shop("当当"),
                new Shop("天猫"),
                new Shop("京东"),new Shop("京东"),new Shop("京东"),new Shop("京东"),   new Shop("京东"),
                new Shop("亚马逊"),
                new Shop("当当"),
                new Shop("天猫"),
                new Shop("京东"),new Shop("京东"),new Shop("京东"),new Shop("京东"),
                new Shop("京东"),
                new Shop("亚马逊"),
                new Shop("当当"),
                new Shop("天猫"),
                new Shop("京东"),new Shop("京东"),new Shop("京东"),new Shop("京东"));


        discountShops=Arrays.asList(new Shop("淘宝"),
                new Shop("天猫"),
                new Shop("京东"),
                new Shop("天猫"),
                new Shop("京东"));
    }

    /**
     * 采用顺序查询所有商店的方式实现的 findPrices 方法
     * @param product
     * @return
     */
    public List<String> findPrice(String product) {
        List<String> list = shops.stream()
                .map(shop ->
                        String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)))

                .collect(toList());

        return list;
    }

    /**
     * 使用并行流对请求进行并行操作
     * @param product
     * @return
     */
    public List<String> findPrice2(String product) {
        List<String> list = shops.parallelStream()
                .map(shop ->
                        String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)))

                .collect(toList());

        return list;
    }


    /**
     * 使用 CompletableFuture 发起异步请求
     * @param product
     * @return
     */
    public List<String> findPrice3(String product) {
      //多流水线模式
      List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)))
                )
                .collect(toList());
        List<String> list = futures.stream()
                //.map(CompletableFuture::join) 等同如下
                .map(completableFuture->completableFuture.join())
                .collect(toList());
       /* //单流水线模式 还是串行执行
        List<String> list = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)))
                )
                .map(completableFuture->completableFuture.join())
                .collect(toList());*/

        return list;
    }

    /**
     * 使用定制的 Executor 配置 CompletableFuture
     *
     * @param product
     * @return
     */
    public List<String> findPrice4(String product) {

        //为“最优价格查询器”应用定制的执行器 Execotor
        Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        //使用守护线程,使用这种方式不会组织程序的关停
                        thread.setDaemon(true);
                        return thread;
                    }
                }
        );

        //将执行器Execotor 作为第二个参数传递给 supplyAsync 工厂方法
        List<CompletableFuture<String>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f RMB",
                                shop.getName(),
                                shop.getPrice(product)), executor)
                )
                .collect(toList());
        List<String> list = futures.stream()
                .map(CompletableFuture::join)
                .collect(toList());


        return list;
    }

    /**
     * 得到折扣商店信息(已经被解析过)
     */
    public List<String> findPrice5(String product){
        List<String> list = discountShops.parallelStream()
                .map(discountShop -> discountShop.getPriceContainDiscountCode(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());

        return list;
    }

    /**
     * 使用 CompletableFuture 实现 findPrices 方法
     */
    public List<String> findPrice6(String product) {
        //为“最优价格查询器”应用定制的执行器 Execotor
        Executor executor = Executors.newFixedThreadPool(Math.min(discountShops.size(), 100),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        //使用守护线程,使用这种方式不会阻止程序的关停
                        thread.setDaemon(true);
                        return thread;
                    }
                }
        );

        List<CompletableFuture<String>> futureList = discountShops.stream()
                .map(discountShop -> CompletableFuture.supplyAsync(
                        //异步方式取得商店中产品价格
                        () -> discountShop.getPriceContainDiscountCode(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(
                                //使用另一个异步任务访问折扣服务
                                () -> Discount.applyDiscount(quote), executor
                        )
                ))
                .collect(toList());

        //等待流中所有future执行完毕,并提取各自的返回值.
        List<String> list = futureList.stream()
                //join想但与future中的get方法,只是不会抛出异常
                .map(CompletableFuture::join)
                .collect(toList());

        return list;
    }

    /**
     *采用顺序查询所有商店的方式实现的 findPrices 方法,查询每个商店里的 iphone666s
     */
    @Test
    public void test() {
        long start = System.nanoTime();
        System.out.println(Runtime.getRuntime().availableProcessors() );

        List<String> list = findPrice6("iphone666s");

        System.out.println(list);
        System.out.println("Done in "+(System.nanoTime()-start)/1_000_000+" ms");
    }




}
