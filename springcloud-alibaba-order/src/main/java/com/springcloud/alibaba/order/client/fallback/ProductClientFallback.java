package com.springcloud.alibaba.order.client.fallback;

import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.order.client.ProductClient;
import org.springframework.stereotype.Component;

/**
 * product service容错类
 */
@Component
public class ProductClientFallback implements ProductClient {
    @Override
    public Product findById(Integer pid) {
        // 具体的容错业务逻辑, 我这里直接返回空的商品对象
        return new Product();
    }

    @Override
    public void reduceInventory(Integer pid, int num) {

    }
}
