package com.moxie.task_forge.common;

public class ApiResponse<T> {
    private String status; // "success" or "error"

    private T data;

    private String message;

    private Integer code;

    public ApiResponse() {
    }

    // Success API response constructor
    public ApiResponse(T data) {
        this.status = "success";
        this.data = data;
        this.message = null;
        this.code = 200;
    }

    // Error API response constructor
    public ApiResponse(String message, Integer code) {
        this.status = "error";
        this.data = null;
        this.message = message;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
