package com.__yun.rpc.springboot.starter;

import com.__yun.rpc.springboot.starter.annotation.EnableRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpc
public class YunRpcSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunRpcSpringBootStarterApplication.class, args);
    }

}
