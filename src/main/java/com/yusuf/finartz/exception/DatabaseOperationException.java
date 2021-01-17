package com.yusuf.finartz.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus
public class DatabaseOperationException extends SQLException {


    private static final long serialVersionUID = 2506025965174857678L;

    public DatabaseOperationException(String message) {
        super(message);
    }
    public DatabaseOperationException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
