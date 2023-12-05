package com.example.hello.exception;

public class MyDBException extends RuntimeException {
    public MyDBException(Throwable cause) {
        super(cause);
    }
}
