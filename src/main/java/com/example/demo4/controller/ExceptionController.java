package com.example.demo4.controller;

import com.example.demo4.exception.CustomException;
import com.example.demo4.response.ObjectResponse;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ObjectResponse emailNotValidatedException(MethodArgumentNotValidException ex, WebRequest request) {
        System.out.println(ex.getBindingResult().getAllErrors().get(0));
        return new ObjectResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                null);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ObjectResponse handler(RuntimeException ex, WebRequest request) {
        return new ObjectResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null);}

    @ExceptionHandler(value = {CustomException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ObjectResponse handler(CustomException ex, WebRequest request) {
        return new ObjectResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null);}

}