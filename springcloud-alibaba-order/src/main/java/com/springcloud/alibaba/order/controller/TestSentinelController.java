package com.springcloud.alibaba.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("sentinel")
public class TestSentinelController {

    @GetMapping("message1")
    public String message1() {
        return "Message 1.";
    }

    @GetMapping("message2")
    public String message2() {
        return "Message 2.";
    }

    @GetMapping("message3")
    @SentinelResource("message3")// 必须添加该注解, 否则热点规则不会生效
    public String message3(String name, Integer age) {
        return "Message 3. name = " + name + ",age = " + age;
    }

}
