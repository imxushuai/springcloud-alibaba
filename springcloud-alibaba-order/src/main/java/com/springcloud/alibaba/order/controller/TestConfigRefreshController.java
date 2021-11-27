package com.springcloud.alibaba.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestConfigRefreshController {

    @Value("${app.customer}")
    private String CUSTOMER;

    @GetMapping("getCustomerConfig")
    public String getCustomerConfig() {
        return CUSTOMER;
    }
}
