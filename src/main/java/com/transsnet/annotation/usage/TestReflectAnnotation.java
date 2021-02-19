package com.transsnet.annotation.usage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflectAnnotation {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = JdbcUtil.class;
        Method method = clazz.getMethod("getConnection", String.class, String.class, String.class);
        DbInfo di  = method.getAnnotation(DbInfo.class);
        //
        method.invoke(null,di.url(),di.username(),di.pw());
    }
}