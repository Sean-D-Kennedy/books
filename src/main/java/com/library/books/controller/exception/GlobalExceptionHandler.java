package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookISBNAlreadyExistsException.class)
    public ResponseEntity<DetailedErrorResponse> handleISBNAlreadyExists(BookISBNAlreadyExistsException exception,
                                                                          WebRequest webRequest){
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookISBNNotFoundException.class)
    public ResponseEntity<DetailedErrorResponse> handleISBNNotFound(BookISBNNotFoundException exception,
                                                                     WebRequest webRequest){
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookISBNMismatchException.class)
    public ResponseEntity<DetailedErrorResponse> handleISBNMismatchOnPut(BookISBNMismatchException exception,
                                                                               WebRequest webRequest){
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
