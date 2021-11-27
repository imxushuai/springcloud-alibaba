package com.springcloud.alibaba.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthGlobalFilterFactory implements GlobalFilter, Ordered {
    /**
     * 自定义的过滤器业务逻辑
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (StringUtils.isBlank(token)) {// 鉴权失败
            log.error("非法用户....");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }*/
        // 鉴权成功, 执行一系列业务....
        return chain.filter(exchange);
    }

    /**
     * 过滤器的优先级, 值越小优先级越高
     *
     * @return orderValue
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
