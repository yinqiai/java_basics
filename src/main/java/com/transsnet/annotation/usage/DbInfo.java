package com.transsnet.annotation.usage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DbInfo {
    String url() default "jdbc:mysql://localhost:3306/test";
    String username() default "root";
    String pw() default "123456";


}
