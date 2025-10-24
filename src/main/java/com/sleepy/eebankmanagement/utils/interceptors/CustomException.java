package com.sleepy.eebankmanagement.utils.interceptors;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}