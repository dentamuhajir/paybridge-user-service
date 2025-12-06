package com.paybridge.user.service.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long start = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;

        String traceId = req.getHeader("X-Trace-Id");
        if (traceId == null) traceId = UUID.randomUUID().toString();
        String spanId = UUID.randomUUID().toString();

        MDC.put("trace_id", traceId);
        MDC.put("span_id", spanId);
        MDC.put("method", req.getMethod());
        MDC.put("endpoint", req.getRequestURI());

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;
        int status = ((HttpServletResponse) response).getStatus();

        MDC.put("status", String.valueOf(status));
        MDC.put("duration_ms", String.valueOf(duration));
    }
}
