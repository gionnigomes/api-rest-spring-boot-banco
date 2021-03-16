package com.acc.banco.service.exception;

public class DataIntegrityViolationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataIntegrityViolationException (String msg){
        super();
    }

    public DataIntegrityViolationException (String msg, Throwable cause){
        super(msg, cause);
    }

}
