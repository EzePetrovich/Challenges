package com.springdemo.biblioteca.services;

import com.springdemo.biblioteca.entities.Author;
import com.springdemo.biblioteca.exceptions.CRUDException;
import com.springdemo.biblioteca.repositories.AuthorRepository;
import com.springdemo.biblioteca.utilities.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements Tools {

    @Autowired
    private AuthorRepository ar;

    @Transactional
    public void createAuthor(String name) throws CRUDException {
        /* Create author */
        validate("nombre", name);
        Author author = new Author(name);
        author.setRemoved(Boolean.FALSE);
        author.setRemovedDate(null);
        /* Save author */
        Optional<Author> answerAuthor = ar.findByName(author.getName());
        if(answerAuthor.isPresent()) {
            author = answerAuthor.get();
            if(author.getRemoved()) {
                author.setRemoved(Boolean.FALSE);
                author.setRemovedDate(null);
            }
            else { throw new CRUDException("El autor ya existe."); }
        }
        ar.save(author);
    }

    public List<Author> listAuthors() { return ar.findAll(); }

    @Transactional
    public void modifyAuthor(String oldName, String newName) throws CRUDException {
        /* Find the author */
        validate("nombre", oldName);
        Optional<Author> answerAuthor = ar.findByName(oldName);
        /* If find then modify */
        if(answerAuthor.isPresent()) {
            Author author = answerAuthor.get();
            validate("nombre", newName);
            author.setName(newName);
            ar.save(author);
        }
        else { throw new CRUDException("El autor no existe."); }
    }

    @Transactional
    public void removeAuthor(String name) throws CRUDException {
        /* Find the author */
        validate("nombre", name);
        Optional<Author> answerAuthor = ar.findByNameRemove(name);
        /* If find then it's removed */
        if(answerAuthor.isPresent()) {
            Author author = answerAuthor.get();
            author.setRemoved(Boolean.TRUE);
            author.setRemovedDate(new Date());
        }
        else { throw new CRUDException("El autor no existe."); }
    }

}
