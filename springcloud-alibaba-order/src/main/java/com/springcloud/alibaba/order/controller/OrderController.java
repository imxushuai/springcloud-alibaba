package com.springcloud.alibaba.order.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.alibaba.common.entity.Order;
import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.order.client.ProductClient;
import com.springcloud.alibaba.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("order")
    public List<Order> orderList() {
        return orderService.findAll();
    }

    //准备买1件商品
    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) {
        return orderService.createOrder(pid);
    }

}
