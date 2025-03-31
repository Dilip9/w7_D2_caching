package com.example.cache.manager.exceptions;

import lombok.Getter;

@Getter
public class UserException extends EntityExceptions{
    public UserException(ExceptionCode exceptionCode){
        super(exceptionCode);
    }
}
