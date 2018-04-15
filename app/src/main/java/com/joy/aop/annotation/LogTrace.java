package com.joy.aop.annotation;

import com.joybar.library.common.log.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by joybar on 14/04/2018.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogTrace {
    @LogLevel.LogLevelType int level() default LogLevel.TYPE_VERBOSE;
    boolean spendTimeEnable() default true;

}
