package com.library.books.controller;

import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController // is a Component
@RequestMapping(path="/books", produces = MediaType.APPLICATION_JSON_VALUE) // works
@AllArgsConstructor
public class BookController {
    private IBookService iBookService;

    //       /books
    @GetMapping
    public List<BookDTO> getAllBooks(){return iBookService.getAllBooks();}


    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("XXX book is "+book);
        BookDTO bookDTO = iBookService.addBook(book);

        URI locationURI = uriComponentsBuilder
                .path("/books/"+bookDTO.getIsbn())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(bookDTO);
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAllBooks(){
        iBookService.deleteAllBooks();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT) // success, no message body
                .build();
    }
    @PutMapping
    public ResponseEntity<String> putNotSupported(){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }


    @GetMapping(path = "/book", params="author") // "params" value must match (@RequestParam identifier)
    public ResponseEntity<List<BookDTO>> getAllBooksByAuthor(@RequestParam String author){ // must be ?author=XX in Postman
        List<BookDTO> authorsBooks = iBookService.getAllBooksByAuthor(author);
        if(authorsBooks.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT) // success, no message body
                    .build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authorsBooks);
        }
    }

    //       /{isbn}
    @GetMapping("/{isbn}") // "isbn" on lines 63 and 64 must match
    public ResponseEntity<BookDTO> getBookDetailsPath(@PathVariable String sbn){
        BookDTO bookDto = iBookService.getBook(sbn);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookDto);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn){
        iBookService.deleteBook(isbn);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT) // success, no message body
                .build();
    }
    @PostMapping("/{isbn}")
    public ResponseEntity<String> postNotSupported(){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn, @RequestBody Book book){
        iBookService.updateBook(isbn, book);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT) // no message body
                .build();
    }
//    @OptionsMapping  - not available
//    @HeadMapping     - not available
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsCollectionOfBooks() {  //      /books
        return ResponseEntity
                .status(HttpStatus.OK) // 200 OK
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE) // varargs list
                .build();

    }
   @RequestMapping(value = "/{isbn}", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsIndividualBook() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .build();

    }
    @RequestMapping(value = "/book", method = RequestMethod.OPTIONS)
    public ResponseEntity<String> optionsIndividualRequestParams() {
        return ResponseEntity
                .status(HttpStatus.OK) // success
                .allow(HttpMethod.HEAD, HttpMethod.GET)
                .build();

    }
}
