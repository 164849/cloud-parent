package com.itck.jifen.common;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomerRequestOriginParser implements RequestOriginParser {

    public String parseOrigin(HttpServletRequest request) {
        //获取请求头的source
        return request.getHeader("source");
    }
}