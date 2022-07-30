package com.study.service;

import com.study.pojo.Products;

public interface ProductService {
    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    Products findByIdProductService(Integer id);
}
