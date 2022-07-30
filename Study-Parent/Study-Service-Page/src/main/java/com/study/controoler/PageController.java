package com.study.controoler;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.study.feign.ProductFeign;
import com.study.pojo.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private ProductFeign productFeign;

    @GetMapping("/finByIdData/{id}")
    public Products finByIdData(@PathVariable Integer id) {
        /*// 获得Study-Service-Product在服务注册中心注册的服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances("Study-Service-Product");
        // 学习环境下所以获取商品服务列表中的第一个
        ServiceInstance instance = instances.get(0);

        // 获取自定义元数据
        Map<String, String> metadata = instance.getMetadata();

        // 获取商品微服务的主机地址
        String host = instance.getHost();
        // 获取商品微服务的端口号
        int port = instance.getPort();
        // 拼接路径
        String url = "http://"+host+":"+port + "/product/findByIdProduct/" + id;*/

        /**
         * 负载均衡策略
         */
        /*String url = "http://Study-Service-Product/product/findByIdProduct/" + id;
        Products products = restTemplate.getForObject(url, Products.class);*/

        // 使用Feign远程调用接口
        return productFeign.findByIdProductFeign(id);
    }

    /**
     * Ribbon负载均衡策略
     *
     * @return
     */
    @GetMapping("/getPort")
    public String getProductServerPort() {
        /*String url = "http://Study-Service-Product/server/query";
        return restTemplate.getForObject(url, String.class);*/

        // 使用Feign远程调用接口
        return productFeign.findServerPortFeign();
    }

    /**
     * 提供者模拟处理超时，调用方法添加Hystrix控制
     *  @HystrixCommand 进行熔断控制
     *  熔断成功显示错误信息（getProductServerPort2 timed-out and fallback failed.）
     */
    @HystrixCommand(
            // 线程池标识，要保持唯一，不唯一就共用了
            threadPoolKey = "getProductServerPort2",
            // 线程池细节属性配置(参数是数组) 每一个属性对应的都是一个HystrixProperty
            threadPoolProperties = {
                    // 线程数
                    @HystrixProperty(name = "coreSize", value = "1"),
                    // 等待队列长度
                    @HystrixProperty(name = "maxQueueSize", value = "20")
            },
            // commandProperties熔断的一些细节属性配置
            commandProperties = {
                    // 设置请求的超时时间，一旦请求超过此时间那么都按照超时处理
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            }
    )
    @GetMapping("/getPort2")
    public String getProductServerPort2() {
        /*String url = "http://Study-Service-Product/server/query";
        return restTemplate.getForObject(url, String.class);*/

        // 使用Feign远程调用接口
        return productFeign.findServerPortFeign();
    }


    /**
     * 服务降级（在服务熔断之后的兜底操作）
     */
    @HystrixCommand(
            // 线程池标识，要保持唯一，不唯一就共用了
            threadPoolKey = "getProductServerPort3",
            // 线程池细节属性配置(参数是数组) 每一个属性对应的都是一个HystrixProperty
            threadPoolProperties = {
                    // 线程数
                    @HystrixProperty(name = "coreSize", value = "2"),
                    // 等待队列长度
                    @HystrixProperty(name = "maxQueueSize", value = "20")
            },

            /**
             * commandProperties熔断的一些细节属性配置
             *  8秒钟内，请求次数达到2个，并且失败率在50%以上，就跳闸 跳闸后活动窗口设置为3s(可在配置文件中配置)
             */
            commandProperties = {
                    // 设置请求的超时时间，一旦请求超过此时间那么都按照超时处理
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    /*//统计窗口时间的设置
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "8000"),
                    //统计窗口内的最小请求数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "2"),
                    //统计窗口内错误请求阈值的设置 50%
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                    //自我修复的活动窗口时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "3000")*/
            },
            // 设置回退方法
            fallbackMethod = "myFallBack"
    )
    @GetMapping("/getPort3")
    public String getProductServerPort3() {
        // 使用Feign远程调用接口
        return productFeign.findServerPortFeign();
    }

    /**
     * 定义回退方法，返回预设默认值
     * 注意：该方法形参和返回值与原始方法保持一致
     */
    public String myFallBack(){
        return "我来兜底啦！";
    }
}
