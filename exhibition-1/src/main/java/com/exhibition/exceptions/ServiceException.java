package com.exhibition.exceptions;

import com.exhibition.enums.ExceptionEnums;

/**
 * Created by final on 17-7-22.
 */
public class ServiceException extends RuntimeException {
    /**
     * 封装自定义的异常信息
     */
    private ExceptionEnums exceptionEnums;

    public ServiceException(ExceptionEnums exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnums = exceptionEnum;
    }

    public ExceptionEnums getExEnums() {
        return exceptionEnums;
    }
}
