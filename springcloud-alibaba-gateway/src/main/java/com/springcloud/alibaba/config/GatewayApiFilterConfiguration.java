package com.springcloud.alibaba.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Configuration
public class GatewayApiFilterConfiguration {

    private final List<ViewResolver> viewResolvers;

    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayApiFilterConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 初始化限流过滤器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)// 最高优先级
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    /**
     * 配置限流的异常处理器
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)// 最高优先级
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 初始化限流规则
     */
    @PostConstruct
    public void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        rules.add(new GatewayFlowRule("product_api1").setCount(1).setIntervalSec(1));
        rules.add(new GatewayFlowRule("product_api2").setCount(1).setIntervalSec(1));
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 定义需要API
     */
    @PostConstruct
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();

        // 新增断言项1
        HashSet<ApiPredicateItem> apiPredicateItems1 = new HashSet<>();
        ApiPathPredicateItem apiPathPredicateItem1 = new ApiPathPredicateItem()
                .setPattern("/product-serv/product/api1/**")
                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX);
        apiPredicateItems1.add(apiPathPredicateItem1);
        ApiDefinition api1 = new ApiDefinition("product_api1").setPredicateItems(apiPredicateItems1);

        // 新增断言项2
        HashSet<ApiPredicateItem> apiPredicateItems2 = new HashSet<>();
        ApiPathPredicateItem apiPathPredicateItem2 = new ApiPathPredicateItem()
                .setPattern("/product-serv/product/api2/**")
                .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX);
        apiPredicateItems1.add(apiPathPredicateItem2);
        ApiDefinition api2 = new ApiDefinition("product_api2")
                .setPredicateItems(apiPredicateItems2);

        // 添加到API定义列表中
        definitions.add(api1);
        definitions.add(api2);

        // 将API列表加载到gateway中
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }


    /**
     * 自定义限流异常页面
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 0);
            map.put("message", "接口被限流了");
            return ServerResponse.status(HttpStatus.OK).
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    body(BodyInserters.fromObject(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
