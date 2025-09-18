// exception/BusinessException.java
package com.gzh.tilas.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
