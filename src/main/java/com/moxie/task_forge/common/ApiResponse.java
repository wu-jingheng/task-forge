package com.moxie.task_forge.common;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private String status; // "success" or "error"

    private T data;

    private String message;

    private HttpStatus httpStatus;

    public ApiResponse() {
    }

    // Success API response constructor
    public ApiResponse(T data) {
        this.status = "success";
        this.data = data;
        this.message = null;
        this.httpStatus = HttpStatus.OK;
    }

    // Error API response constructor
    public ApiResponse(String message, HttpStatus httpStatus) {
        this.status = "error";
        this.data = null;
        this.message = message;
        this.httpStatus = httpStatus;
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
