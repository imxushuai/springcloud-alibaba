package com.springcloud.alibaba.order.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义限流异常返回
 */
public class ExceptionHandlerConfig implements UrlBlockHandler {
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        JSONObject result = new JSONObject();
        result.put("message", "当前服务器忙, 请稍后再试!");

        response.getWriter().write(result.toJSONString());
    }
}
