package com.springcloud.alibaba.order.config;

import com.alibaba.csp.sentinel.EntryType;

import java.lang.annotation.*;

/**
 * The annotation indicates a definition of Sentinel resource.
 *
 * @author Eric Zhao
 * @since 0.1.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SentinelResource {

    /**
     * 资源名称
     */
    String value() default "";

    /**
     * entry类型，标记流量的方向，取值IN/OUT，默认是OUT
     */
    EntryType entryType() default EntryType.OUT;

    /**
     * 资源类型
     * Sentinel提供的常量类：ResourceTypeConstants
     * COMMON = 0;
     * COMMON_WEB = 1;
     * COMMON_RPC = 2;
     * COMMON_API_GATEWAY = 3;
     * COMMON_DB_SQL = 4;
     */
    int resourceType() default 0;

    /**
     * 处理BlockException的函数名称,函数要求：
     * 1. 必须是 public
     * 2.返回类型 参数与原方法一致
     * 3. 默认需和原方法在同一个类中。若希望使用其他类的函数，可配置
     * blockHandlerClass ，并指定blockHandlerClass里面的方法。
     */
    String blockHandler() default "";

    /**
     * 存放blockHandler的类,对应的处理函数必须static修饰。
     */
    Class<?>[] blockHandlerClass() default {};

    /**
     * 用于在抛出异常的时候提供fallback处理逻辑。fallback函数可以针对所
     * 有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进
     * 行处理。函数要求：
     * 1. 返回类型与原方法一致
     * 2. 参数类型需要和原方法相匹配
     * 3. 默认需和原方法在同一个类中。若希望使用其他类的函数，可配置
     * fallbackClass，并指定fallbackClass里面的方法。
     */
    String fallback() default "";

    /**
     * 用于通用的 fallback 逻辑。默认fallback函数可以针对所有类型的异常进
     * 行处理。若同时配置了 fallback 和 defaultFallback，以fallback为准。函
     * 数要求：
     * 1. 返回类型与原方法一致
     * 2. 方法参数列表为空，或者有一个 Throwable 类型的参数。
     * 3. 默认需要和原方法在同一个类中。若希望使用其他类的函数，可配置
     * fallbackClass，并指定 fallbackClass 里面的方法。
     */
    String defaultFallback() default "";

    /**
     * 存放fallback的类。对应的处理函数必须static修饰。
     */
    Class<?>[] fallbackClass() default {};

    /**
     * 需要trace的异常
     */
    Class<? extends Throwable>[] exceptionsToTrace() default {Throwable.class};
    
    /**
     * 指定排除掉哪些异常。排除的异常不会计入异常统计，也不会进入
     * fallback逻辑，而是原样抛出。
     */
    Class<? extends Throwable>[] exceptionsToIgnore() default {};
}
