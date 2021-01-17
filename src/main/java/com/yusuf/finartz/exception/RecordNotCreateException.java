package com.yusuf.finartz.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class RecordNotCreateException extends RuntimeException {


    private static final long serialVersionUID = 1158980192307286346L;

    public RecordNotCreateException(String message) {
        super(message);
    }
    public RecordNotCreateException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
