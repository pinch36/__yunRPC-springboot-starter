package com.__yun.rpc.springboot.starter.annotation;

import com.__yun.rpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.__yun.rpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.__yun.rpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/13/21:06
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//启动类需要有入口被spring管理
@Import({RpcConsumerBootstrap.class, RpcProviderBootstrap.class, RpcInitBootstrap.class})
public @interface EnableRpc {
    boolean needServer() default true;
}
