package com.fintech.platform.common.response;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiResponse<T> {
    private int status;
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean success, String message, T data, int status) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.status = status;
    }
    // -- Static factory helpers for convenience --
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, 200);
    }

    public static <T> ApiResponse<T> success(String message, T data, int status) {
        return new ApiResponse<>(true, message, data, status);
    }

    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(false, message, null, status);
    }
}



