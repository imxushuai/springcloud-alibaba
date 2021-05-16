package com.springcloud.alibaba.predicates;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.AfterRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.BeforeRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Age自定义断言类
 *
 * 自定义断言类命名规范：配置项 + RoutePredicateFactory
 */
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("minAge", "maxAge");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (exchange) -> {
            // 获取请求参数age
            String ageString = exchange.getRequest().getQueryParams().getFirst("age");
            if (StringUtils.isBlank(ageString)) {
                return false;
            }
            // 判断age
            int age = Integer.parseInt(ageString);
            return age > config.minAge && age < config.maxAge;
        };
    }

    @Data
    static class Config {
        private int minAge;
        private int maxAge;
    }
}
