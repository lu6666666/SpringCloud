package com.study.service.impl;

import com.study.mapper.ProductMapper;
import com.study.pojo.Products;
import com.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Products findByIdProductService(Integer id) {
        return productMapper.selectById(id);
    }
}
