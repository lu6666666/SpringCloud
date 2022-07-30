package com.study.filter;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义全局过滤器，会对所有路由生效
 *      GlobalFilter：全局过滤器，应用到所有路由
 *      GateWayFilter：单个过滤器，应用到单个路由
 *      Ordered：定义过滤的顺序
 * 通常情况下进行网关自定义过滤器时，需要实现两个接口
 */
@Component
public class BlackListFilter implements GlobalFilter,Ordered {

    /**
     * 加载地址黑名单列表(正常使用方式)
     *  MYSQL缓存到 -> Redis -> 加载到内存中
     */
    private static List<String> blackList = new ArrayList<>();

    /**
     * 静态代码块
     *  先于构造快执行
     *  只执行一次
     */
    static {
        // 将本机地址加入到黑名单
        blackList.add("127.0.0.1");
    }

    /**
     * GlobalFilter过滤器的核心逻辑
     * @param exchange 封装了request和response上下文
     * @param chain 网关过滤器链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 获取来访者相应地址
        String clientIP = request.getRemoteAddress().getHostString();
        // 判断是否存在黑名单中 （contains方法判断是否包含指定对象）
        if(blackList.contains(clientIP)){
            // 存在黑名单中拒绝访问

            // 设置没有授权的状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 返回错误信息
            String message = "request be denied";

            DataBuffer wrap = response.bufferFactory().wrap(message.getBytes());

            return response.writeWith(Mono.just(wrap));
        }
        return chain.filter(exchange);
    }

    /**
     * Ordered定义过滤的顺序，getOrder()返回值的大小决定了过滤器执行的优先级，越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
