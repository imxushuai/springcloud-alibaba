package com.springcloud.alibaba.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义局部过滤器
 *
 * 过滤器命名规则：配置项 + GatewayFilterFactory
 */
@Slf4j
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public LogGatewayFilterFactory() {
        super(LogGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog", "cacheLog");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.consoleLog) {
                log.info("开启ConsoleLog....");
            }
            if (config.cacheLog) {
                log.info("开启CacheLog....");
            }

            return chain.filter(exchange);
        };
    }


    @Data
    public static class Config {
        private boolean consoleLog;
        private boolean cacheLog;
    }
}
