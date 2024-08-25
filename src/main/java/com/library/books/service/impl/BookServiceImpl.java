package com.library.books.service.impl;

import com.library.books.controller.exception.BookISBNAlreadyExistsException;
import com.library.books.controller.exception.BookISBNMismatchException;
import com.library.books.controller.exception.BookISBNNotFoundException;
import com.library.books.repository.BookRepository;
import com.library.books.service.dto.BookMapper;
import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // is a Component
@AllArgsConstructor
public class BookServiceImpl implements IBookService {
    private BookRepository bookRepository; // will be injected

    public BookDTO addBook(Book book){
        if(bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            // book reg is already in db, do not add it again
            throw new BookISBNAlreadyExistsException("That book already exists in db! : "+book.getIsbn());
        }
        Book savedBook = bookRepository.save(book); // no need to code save(Book)!
        BookDTO bookDto = BookMapper.mapToBookDTO(savedBook, new BookDTO()); // so the primary key is not returned
        return bookDto;
    }
    public List<BookDTO> getAllBooks(){
        List<BookDTO> listOfBookDtos = new ArrayList<>();
        for(Book book: bookRepository.findAll()){
            BookDTO bookDto = BookMapper.mapToBookDTO(book, new BookDTO()); // so the primary key is not returned
            listOfBookDtos.add(bookDto);
        }
        return listOfBookDtos;
    }
    public BookDTO getBook(String isbn){
        Book book = bookRepository
                    .findByIsbn(isbn) // returns Optional<Book>
                    .orElseThrow(() -> new BookISBNNotFoundException("Book ISBN not found in db! : "+isbn));
        BookDTO bookDto = BookMapper.mapToBookDTO(book, new BookDTO()); // so the primary key is not returned
        return bookDto;
    }
    public List<BookDTO> getAllBooksByAuthor(String author){
        List<BookDTO> listOfBookDtos = new ArrayList<>();
//        for(Book book: bookRepository.findByAuthors(author)){
        for(Book book: bookRepository.findBooksByAuthorName(author)){
            BookDTO bookDto = BookMapper.mapToBookDTO(book, new BookDTO()); // so the primary key is not returned
            listOfBookDtos.add(bookDto);
        }
        return listOfBookDtos;
    }
    public boolean deleteBook(String isbn){
        Book book = bookRepository
                .findByIsbn(isbn) // returns Optional<Book>
                .orElseThrow(() -> new BookISBNNotFoundException("ISBN not found in db! : "+isbn));
        bookRepository.deleteByIsbn(isbn); // derived query
        return true;
    }
    public boolean deleteAllBooks(){
        bookRepository.deleteAll(); // already available
        return true;
    }
    public boolean updateBook(String isbnFromURI, Book book){
        if(!isbnFromURI.equals(book.getIsbn())) { // isbn numbers do not match, generate an exception
            throw new BookISBNMismatchException("ISBN numbers mismatch!. URI: "+isbnFromURI+", Entity Body: "+book.getIsbn());
        }
        // this is an update as the isbn is already in the database
        // Update - save() does insert as we know; it will do update if the primary key is present.
        // However, as I am letting the db generate ID's for the primary keys, this will not work.
        // Thus, we need to annotate the update method with @Transaction, @Query and @Modifying.
        // https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa
        bookRepository.updateBook(book.getBookTitle(), book.getAuthors(), book.getPublisher(),
                book.getYearPublished(), book.getPrice(), book.getIsbn());
        return true;
    }
}
