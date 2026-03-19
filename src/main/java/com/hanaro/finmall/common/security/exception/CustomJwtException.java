package com.hanaro.finmall.common.security.exception;

public class CustomJwtException extends RuntimeException {
    public CustomJwtException(String msg) {
        super("JwtErr:" + msg);
    }
}