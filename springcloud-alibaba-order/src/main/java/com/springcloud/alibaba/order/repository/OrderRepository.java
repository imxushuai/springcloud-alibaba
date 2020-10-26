package com.springcloud.alibaba.order.repository;

import com.springcloud.alibaba.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
