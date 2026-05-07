package com.example.identity.exception;

import com.example.identity.dto.API.ErrorType;

public class AppException extends RuntimeException {
    private ErrorType errorCode;

    public AppException(ErrorType errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorType getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorType errorCode) {
        this.errorCode = errorCode;
    }
}
