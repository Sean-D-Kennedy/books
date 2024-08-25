package com.library.books.service;

import com.library.books.repository.entity.Book;
import com.library.books.service.dto.BookDTO;

import java.util.List;

public interface IBookService {
    // POST
    BookDTO addBook(Book book);

    // GET
    public List<BookDTO> getAllBooks();
    public BookDTO getBook(String isbn);
    public List<BookDTO> getAllBooksByAuthor(String authors);

    // DELETE
    boolean deleteBook(String isbn);
    boolean deleteAllBooks();

    // PUT
    public boolean updateBook(String bookISBN, Book book);
}
