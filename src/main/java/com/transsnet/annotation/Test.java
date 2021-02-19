package com.transsnet.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinqi
 * @date 2019/12/23
 */
public class Test {
    @Hello(value = "hello")
    public static void main(String[] args) throws NoSuchMethodException {
        // 方式1.从源码中得知，设置这个值，可以把生成的代理类，输出出来。
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 方式2. vm参数 -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
        //yinqi

       /* Class cls= Test.class;
        Method m = cls.getMethod("main",String[].class);
        Hello h=m.getAnnotation(Hello.class);*/

       //"dsds".hashCode();
       // System.out.println("toString".charAt(2));



    }
}
