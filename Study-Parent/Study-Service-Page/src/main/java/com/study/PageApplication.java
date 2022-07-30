package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 注解简化写法
 * @SpringCloudApplication = @SpringBootApplication+@EnableDiscoveryClient+@EnableCircuitBreaker
 */
@SpringBootApplication
// 也是将当前项目表示为注册中心的客户端 向注册中心进行注册 可以在所有的服务注册中心环境下使用
@EnableDiscoveryClient
// 开启熔断器
// @EnableCircuitBreaker
// 开启Feign(Feign会自动引入@EnableCircuitBreaker(熔断))
@EnableFeignClients
public class PageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PageApplication.class,args);
    }

    @Bean
    // Ribbon负载均衡
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
