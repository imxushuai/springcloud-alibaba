package com.springcloud.alibaba.order.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.alibaba.common.entity.Order;
import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.order.client.ProductClient;
import com.springcloud.alibaba.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("test")
public class TestOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;


    //准备买1件商品
    @GetMapping("/order/prod/{pid}")
    public Order order(@PathVariable("pid") Integer pid) throws InterruptedException {
        log.info(">> 客户下单，这时候要调用商品微服务查询商品信息");
        //通过Feign客户端调用
        Product product = productClient.findById(pid);

        if (product != null) {
            log.info(">> 商品信息,查询结果: {}", JSON.toJSONString(product));
            Order order = new Order();
            order.setUid(1);
            order.setUsername("测试用户");
            order.setPid(product.getPid());
            order.setPname(product.getPname());
            order.setPprice(product.getPprice());
            order.setNumber(1);
//            orderService.save(order);
            Thread.sleep(2000);

            return order;
        }

        throw new RuntimeException("购买失败!");
    }

    @GetMapping("message")
    public String getMessage() {
        return "测试高并发场景";
    }

}
