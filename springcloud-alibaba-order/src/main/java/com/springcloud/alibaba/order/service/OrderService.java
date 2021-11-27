package com.springcloud.alibaba.order.service;

import com.alibaba.fastjson.JSON;
import com.springcloud.alibaba.common.entity.Order;
import com.springcloud.alibaba.common.entity.Product;
import com.springcloud.alibaba.order.client.ProductClient;
import com.springcloud.alibaba.order.repository.OrderRepository;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

//    @GlobalTransactional
    public Order createOrder(Integer pid) {
        log.info(">> 客户下单，这时候要调用商品微服务查询商品信息");
        // 通过Feign客户端调用
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
            this.save(order);

            // 减少库存
            productClient.reduceInventory(pid, order.getNumber());

            // 下单完成, 发送消息到用户微服务
            rocketMQTemplate.convertAndSend("order-topic", order);

            return order;
        }
        return null;
    }
}
