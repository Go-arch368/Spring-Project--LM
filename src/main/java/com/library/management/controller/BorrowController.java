package com.library.management.controller;


import com.library.management.model.BorrowRecord;
import com.library.management.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/{bookId}")
    public BorrowRecord borrowRecord(@PathVariable Long bookId , Principal principal){
        return borrowService.borrowBook(bookId,principal.getName());
    }

    @PutMapping("/return/{bookId}")
    public BorrowRecord returnBook(@PathVariable Long bookId){
        return borrowService.returnBook(bookId);
    }

    @GetMapping("/my-books")
    public List<BorrowRecord> getMyBooks(Principal principal){
        return borrowService.getMyBorrowedBooks(principal.getName());
    }

}
