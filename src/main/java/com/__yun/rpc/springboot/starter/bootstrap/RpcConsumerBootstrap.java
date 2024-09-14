package com.__yun.rpc.springboot.starter.bootstrap;

import __yunRPC.core.factory.ServiceProxyFactory;
import __yunRPC.core.proxy.TcpServiceProxy;
import com.__yun.rpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/13/21:20
 * @Description:
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference == null){
                continue;
            }
            Class<?> interfaceClass = rpcReference.interfaceClass();
            if (interfaceClass == void.class){
                interfaceClass = field.getType();
            }
            field.setAccessible(true);
            Object tcpServiceProxy = ServiceProxyFactory.getTcpProxy(interfaceClass);
            try {
                field.set(bean,tcpServiceProxy);
                log.info("代理成功注入：{}",tcpServiceProxy);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("为字段注入代理对象失败..",e);
            }
        }



        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
