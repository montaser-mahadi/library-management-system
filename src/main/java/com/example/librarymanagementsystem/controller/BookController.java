// BookController.java
package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BookDTO;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.BorrowerRepository;
import com.example.librarymanagementsystem.service.BookService;
import com.example.librarymanagementsystem.service.impl.BookServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    BookRepository bookRepository ;
    BorrowerRepository borrowerRepository;
    private final BookService bookService = new BookServiceImpl(bookRepository,new ModelMapper(),borrowerRepository);

//    //public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

    @PostMapping
    public ResponseEntity<BookDTO> registerBook(@RequestBody BookDTO bookDTO) {
        logger.info("Request to create book: {}", bookDTO);
        try {
            BookDTO savedBook = bookService.registerBook(bookDTO);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        logger.info("Request to get All books");
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookDetails(@PathVariable Long bookId) {
        logger.info("Request to get book with id: {}", bookId);
        BookDTO bookDTO = bookService.getBookById(bookId);
        return ResponseEntity.ok(bookDTO);
    }
}
