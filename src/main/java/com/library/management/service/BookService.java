package com.library.management.service;


import com.library.management.model.Author;
import com.library.management.model.Book;
import com.library.management.repository.AuthorRepository;
import com.library.management.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Book not found with id: "+ id));
    }

    public Book addBook(Long authorId,Book book){
        Author author = authorRepository.findById(authorId)
                .orElseThrow(()->
                        new RuntimeException("Author not found with id: "+ authorId));
        book.setAuthor(author);
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id,Book updated){
        Book existing = getBookById(id);
        existing.setTitle(updated.getTitle());
        existing.setGenre(updated.getGenre());
        return bookRepository.save(existing);
    }

    public void deleteBook(Long id){
        if(!bookRepository.existsById(id)){
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public List<Book> getAvailableBooks(){
        return bookRepository.findByAvailableTrue();// get all the available records == true;
    }

    public List<Book> searchByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title);// based on the title stored in the db fetch the data;
    }

    public List<Book> searchByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCase(genre);// same like above based on genre
    }

    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorId(authorId);// get record based on the author;
    }
}
