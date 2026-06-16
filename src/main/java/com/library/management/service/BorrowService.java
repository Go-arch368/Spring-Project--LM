package com.library.management.service;

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
                        new RuntimeException("Book not found with id: " + bookId));

        if(!book.isAvailable()){
            throw new RuntimeException("Book is already borrowed");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new RuntimeException("User not found!"));

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
                       new RuntimeException("No active borrow record found!"));

       Book book = record.getBook();
       book.setAvailable(true);
       bookRepository.save(book);

       record.setReturnDate(LocalDate.now());
       return borrowRepository.save(record);
    }

    public List<BorrowRecord> getMyBorrowedBooks(String username){
         User user = userRepository.findByUsername(username)
                 .orElseThrow(()-> new RuntimeException("Username not found in the db"));

         return borrowRepository.findByBookId(user.getId());
    }


}
