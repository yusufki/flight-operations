package com.yusuf.finartz.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -893821770617532991L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
