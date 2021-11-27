package com.springcloud.alibaba.product.service;

import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product findByPid(Integer pid) {
        return productRepository.findById(pid).orElse(null);
    }

    public void reduceInventory(Integer pid, int num) {
        Product product = this.findByPid(pid);

        // 发生异常
        int i = 1/0;

        product.setStock(product.getStock() - num);
        productRepository.save(product);
    }
}
