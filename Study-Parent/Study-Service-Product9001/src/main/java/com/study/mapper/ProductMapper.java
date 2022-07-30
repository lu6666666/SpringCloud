package com.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.pojo.Products;

/**
 * 现在使用的Mybatis-plus组件，该组件是Mybatis的加强版
 * 能够与SpringBoot进行非常友好的整合，对比Mybatis框架只有使用便捷的改变
 * 没有具体功能的改变
 * 具体使用：让具体的Mapper接口继承BaseMapper即可
 */
public interface ProductMapper extends BaseMapper<Products> {

}
