package com.paybridge.user.service.logging;

import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import java.io.IOException;

public class TraceRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private static final String HEADER = "X-Trace-Id";

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        String traceId = MDC.get("trace_id");

        if (traceId != null) {
            request.getHeaders().add(HEADER, traceId);
        }

        return execution.execute(request, body);
    }
}