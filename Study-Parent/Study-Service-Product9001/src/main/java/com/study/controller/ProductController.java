package com.study.controller;

import com.study.pojo.Products;
import com.study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @GetMapping("/findByIdProduct/{id}")
    public Products findByIdProduct(@PathVariable Integer id){
        return productService.findByIdProductService(id);
    }
}
