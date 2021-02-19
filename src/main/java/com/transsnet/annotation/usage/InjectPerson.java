package com.transsnet.annotation.usage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yinqi
 * @date 2019/12/25
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectPerson {
    String name();
    int age();
}
