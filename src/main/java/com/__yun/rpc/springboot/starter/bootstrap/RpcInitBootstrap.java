package com.__yun.rpc.springboot.starter.bootstrap;

import __yunRPC.core.RpcApplication;
import __yunRPC.core.service.Impl.NettyTcpServer;
import __yunRPC.core.service.TcpServer;
import com.__yun.rpc.springboot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/13/21:21
 * @Description:
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //不需要判断是否含有该注解：只有有注解EnableRpc才会导入该类，因此必定有该注解
//        if (!importingClassMetadata.hasAnnotation("EnableRpc")){
//            return;
//        }
        log.info("rpc初始化..");
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");
        RpcApplication.init();
        if (needServer) {
            TcpServer tcpServer = new NettyTcpServer();
            Integer serverPort = RpcApplication.getRpcConfig().getServerPort();
            //线程记得start
            new Thread(() -> tcpServer.doStart(serverPort)).start();
        } else {
            log.info("不启动server..");
        }
    }
}
