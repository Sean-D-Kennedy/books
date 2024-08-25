package com.library.books.service.dto;

import com.library.books.repository.entity.Book;

public class BookMapper {
    public static BookDTO mapToBookDTO(Book book, BookDTO bookDto) {
        bookDto.setBookTitle(book.getBookTitle());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setAuthors(book.getAuthors());
        bookDto.setPublisher(book.getPublisher());
        bookDto.setYearPublished(book.getYearPublished());
        bookDto.setPrice(book.getPrice());

        return bookDto;
    }
}
