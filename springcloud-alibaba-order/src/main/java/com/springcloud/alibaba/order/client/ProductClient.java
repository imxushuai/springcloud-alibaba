package com.springcloud.alibaba.order.client;

import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.order.client.fallback.ProductClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product", fallback = ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("product/{pid}")
    Product findById(@PathVariable Integer pid);

}
