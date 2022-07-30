package com.study.feign;

import com.study.pojo.Products;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Study-Service-Product",fallback = ProductFeignFallBack.class)
public interface ProductFeign {

    /**
     * 通过id获取商品信息
     */
    @GetMapping("/product/findByIdProduct/{id}")
    Products findByIdProductFeign(@PathVariable Integer id);

    /**
     * 获取端口号
     */
    @GetMapping("/server/query")
    String findServerPortFeign();
}
