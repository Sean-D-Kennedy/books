package com.library.books.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND) // does not override status set by ResponseEntity
public class BookISBNNotFoundException extends RuntimeException{
    public BookISBNNotFoundException(String message){
        super(message);
    }
}
