package com.library.management.service;

import com.library.management.exception.LibraryException;
import com.library.management.model.Book;
import com.library.management.model.BorrowRecord;
import com.library.management.model.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowRepository;
import com.library.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public BorrowRecord borrowBook(long bookId , String username){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->
                        new LibraryException("Book not found with id: " + bookId , 404));

        if(!book.getAvailable()){
            throw new LibraryException("Book is already borrowed" , 400);
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new LibraryException("User not found!" , 404));

        book.setAvailable(false);

        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUser(user);
        record.setBorrowDate(LocalDate.now());
        record.setReturnDate(null);

        return borrowRepository.save(record);

    }

    // this is for return a book

    public BorrowRecord returnBook(Long bookId){
       BorrowRecord record = borrowRepository.findByBookIdAndReturnDateIsNull(bookId)
               .orElseThrow(()->
                       new LibraryException("No active borrow record found!",404));

       Book book = record.getBook();
       book.setAvailable(true);
       bookRepository.save(book);

       record.setReturnDate(LocalDate.now());
       return borrowRepository.save(record);
    }

    public List<BorrowRecord> getMyBorrowedBooks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new LibraryException("Username not found in the db", 404));

        return borrowRepository.findByUserId(user.getId()); // ✅ fixed!
    }


}
