package com.__yun.rpc.springboot.starter.bootstrap;

import __yunRPC.common.service.UserService;
import __yunRPC.core.RpcApplication;
import __yunRPC.core.config.RegistryConfig;
import __yunRPC.core.config.RpcConfig;
import __yunRPC.core.constant.RpcConstant;
import __yunRPC.core.factory.RegistryFactory;
import __yunRPC.core.model.service.ServiceMetaInfo;
import __yunRPC.core.registry.LocalRegistry;
import __yunRPC.core.registry.Registry;
import com.__yun.rpc.springboot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/13/21:20
 * @Description:
 */
@Slf4j
public class RpcProviderBootstrap implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        if (rpcService == null){
            return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
        }
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Class<?> interfaceClass = rpcService.interfaceClass();
        if (interfaceClass == void.class){
            interfaceClass = beanClass.getInterfaces()[0];
        }
        String serviceVersion = rpcService.serviceVersion();
        String serviceName = interfaceClass.getName();
        LocalRegistry.registry(serviceName, beanClass);
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(serviceVersion);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        try {
            log.info("注册服务：{}",serviceMetaInfo);
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
