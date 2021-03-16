package com.acc.banco.controller.exception;

import com.acc.banco.service.exception.BalanceException;
import com.acc.banco.service.exception.DataIntegrityViolationException;
import com.acc.banco.service.exception.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StanderError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
        StanderError err = new StanderError(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StanderError> contraint (DataIntegrityViolationException e, HttpServletRequest request){
        StanderError err = new StanderError(HttpStatus.BAD_REQUEST.value(), e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StanderError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Error de validação");

        for (FieldError x : e.getBindingResult().getFieldErrors()){
            err.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    
    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<StanderError> contraint (BalanceException e, HttpServletRequest request){
        StanderError err = new StanderError(HttpStatus.BAD_REQUEST.value(), "Error Saldo Insuficiente");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
