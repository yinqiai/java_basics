package com.transsnet.completable;

/**
 * @author yinqi
 * @date 2020/11/9
 */

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 每个商店都提供的对外访问的API
 * @author itguang
 * @create 2017-11-22 11:05
 **/
public class Shop {

    /**
     * 商店名称
     */
    private  String name;
    private Random random = new Random();


    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * (阻塞式)通过名称查询价格
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * (阻塞式)通过名称查询价格
     * @param product
     * @return 返回  Shop-Name:price:DiscountCode 的格式字符串
     */
    public String getPriceContainDiscountCode(String product) {

        double price = calculatePrice(product);
        //随机得到一个折扣码
        Discount.Code code = Discount.Code.values()[
                random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s",name,price,code);
    }




    /**
     * 计算价格
     * @param product
     * @return
     */
    private double calculatePrice(String product){
        delay();

       // int i=  1/0;//故意抛出 java.lang.ArithmeticException: / by zero 异常

        //数字*字符=数字
        //return 10*product.charAt(0);
        return random.nextDouble()*product.charAt(0)*product.charAt(1);

    }


    /**
     * 模拟耗时操作,阻塞1秒
     */
    private void delay(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * (非阻塞式)异步获取价格
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(()->{
            double price = calculatePrice(product);
            //需要长时间计算的任务结束并返回结果时,设置Future返回值
            future.complete(price);
        }).start();

        //无需等待还没结束的计算,直接返回Future对象
        return future;
    }

    /**
     * 使用静态工厂supplyAsync(非阻塞式)异步获取价格
     * @param product
     * @return
     */
    public Future<Double> getPriceAsyncImprove(String product){
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> calculatePrice(product));

        //无需等待还没结束的计算,直接返回Future对象
        return future;
    }

}
