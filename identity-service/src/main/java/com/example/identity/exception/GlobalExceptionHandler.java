package com.example.identity.exception;

import com.example.identity.dto.API.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public ErrorType handlingAppException(AppException exception) {
        return exception.getErrorCode();
    }
}
