package com.morris.logging.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Configuration
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String transactionId = UUID.randomUUID().toString();
        MDC.put("TransactionId",transactionId);

        chain.doFilter(req,response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
