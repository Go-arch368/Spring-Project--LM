package com.library.management.service;
import com.library.management.model.Author;
import com.library.management.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Author not found with id "+id));
    }

    public Author addAuthor(Author author){
        if(authorRepository.existsByName(author.getName())){
            throw new RuntimeException("Author already exists!");
        }
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id , Author updated){
        Author existing = getAuthorById(id);

            existing.setName(updated.getName());
            existing.setNationality(updated.getNationality());
        return authorRepository.save(existing);
    }

    public void deleteAuthor(Long id){
        if(!authorRepository.existsById(id)){
            throw new RuntimeException("Author not found with id : "+id);
        }
        authorRepository.deleteById(id);
    }

    public List<Author> searchUsers(String name){
        return authorRepository.findByNameContainingIgnoreCase(name);// in search bar we search a name with includes that text gives all the mathcing records:
    }



}
