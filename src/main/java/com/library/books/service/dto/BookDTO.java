package com.library.books.service.dto;

import lombok.Data;

// @Data : Generates getters/setters, a constructor, toString, hashCode and equals.
@Data
public class BookDTO {
    private String bookTitle, authors, publisher, isbn;
    private int yearPublished, price;
}
