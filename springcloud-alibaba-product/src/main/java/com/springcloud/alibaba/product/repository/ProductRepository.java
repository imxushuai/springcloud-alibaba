package com.springcloud.alibaba.product.repository;

import com.springcloud.alibaba.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
