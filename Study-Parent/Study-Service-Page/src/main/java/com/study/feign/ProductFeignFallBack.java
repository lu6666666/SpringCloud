package com.study.feign;

import com.study.pojo.Products;
import org.springframework.stereotype.Component;

/**
 * 熔断器触发之后的回调逻辑
 */
@Component
public class ProductFeignFallBack implements ProductFeign {
    @Override
    public Products findByIdProductFeign(Integer id) {
        return null;
    }

    @Override
    public String findServerPortFeign() {
        return "我是来兜底的！！";
    }
}
