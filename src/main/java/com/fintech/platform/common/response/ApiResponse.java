package com.fintech.platform.common.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;
}
