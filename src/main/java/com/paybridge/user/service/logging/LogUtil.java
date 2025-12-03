package com.paybridge.user.service.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

public class LogUtil {

    private static final Logger log = LoggerFactory.getLogger("SERVICE_LOG");

    public static void info(String message, String endpoint, String method, int status, String userId) {
        MDC.put("span_id", UUID.randomUUID().toString());
        MDC.put("endpoint", endpoint);
        MDC.put("method", method);
        MDC.put("status", String.valueOf(status));

        if (userId != null)
            MDC.put("user_id", userId);

        log.info(message);

        MDC.remove("span_id");
        MDC.remove("endpoint");
        MDC.remove("method");
        MDC.remove("status");
        MDC.remove("user_id");
    }

    public static void error(String message, String endpoint, String method, int status, String userId) {
        MDC.put("span_id", UUID.randomUUID().toString());
        MDC.put("endpoint", endpoint);
        MDC.put("method", method);
        MDC.put("status", String.valueOf(status));

        if (userId != null)
            MDC.put("user_id", userId);

        log.error(message);

        MDC.remove("span_id");
        MDC.remove("endpoint");
        MDC.remove("method");
        MDC.remove("status");
        MDC.remove("user_id");
    }
}
