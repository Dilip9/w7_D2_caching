package com.example.cache.manager.controller.advice;

import com.example.cache.manager.exceptions.EntityExceptions;
import com.example.cache.manager.models.response.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler(value = {EntityExceptions.class})
    public ResponseEntity<ErrorMessage> catchBooksException(EntityExceptions exceptions){
        return new ResponseEntity<>(ErrorMessage.builder()
                .errorCode(exceptions.getExceptionCode()
                        .getErrorCode())
                .errorMessage(exceptions.getExceptionCode().getErrorMessage()).build(),
                exceptions.getExceptionCode().getStatus());
    }

}
