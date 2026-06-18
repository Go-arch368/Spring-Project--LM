package com.library.management.controller;


import com.library.management.model.Book;
import com.library.management.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @GetMapping("/available")
    public List<Book> getAvailable(){
        return bookService.getAvailableBooks();
    }

    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title){
       return bookService.searchByTitle(title);
    }

    @GetMapping("/search/genre")
    public List<Book> searchByGenre(@RequestParam String genre){
        return bookService.searchByGenre(genre);
    }

    @GetMapping("/author/{authorId}")
    public List<Book> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.getBooksByAuthor(authorId);
    }

    @PostMapping("/author/{authorId}")
    public Book addBook(@PathVariable Long authorId,@RequestBody Book book){
        return bookService.addBook(authorId,book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id,
                           @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "Book deleted successfully!";
    }

}
