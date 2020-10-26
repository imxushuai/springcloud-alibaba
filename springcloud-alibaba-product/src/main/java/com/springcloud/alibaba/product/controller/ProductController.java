package com.springcloud.alibaba.product.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("product/{pid}")
    public Product product(@PathVariable Integer pid) {
        Product product = productService.findByPid(pid);
        log.info(">> 查询到商品: {}", JSON.toJSONString(product));
        return product;
    }


}
