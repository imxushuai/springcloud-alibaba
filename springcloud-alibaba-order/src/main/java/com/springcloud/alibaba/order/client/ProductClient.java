package com.springcloud.alibaba.order.client;

import com.springcloud.alibaba.common.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-product")
public interface ProductClient {

    @GetMapping("product/{pid}")
    Product findById(@PathVariable Integer pid);

}
