package com.library.management.controller;


import com.library.management.model.Author;
import com.library.management.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @GetMapping("/search")
    public List<Author> searchUsers(@RequestParam String name){
        return authorService.searchUsers(name);
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author){
        return authorService.addAuthor(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id , @RequestBody Author author){
        return authorService.updateAuthor(id,author);
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return "Author deleted successfully";
    }

}
