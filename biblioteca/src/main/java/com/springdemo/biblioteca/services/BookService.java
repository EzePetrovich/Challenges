package com.springdemo.biblioteca.services;

import com.springdemo.biblioteca.entities.Author;
import com.springdemo.biblioteca.entities.Editorial;
import com.springdemo.biblioteca.entities.Book;
import com.springdemo.biblioteca.exceptions.CRUDException;
import com.springdemo.biblioteca.repositories.AuthorRepository;
import com.springdemo.biblioteca.repositories.EditorialRepository;
import com.springdemo.biblioteca.repositories.BookRepository;
import com.springdemo.biblioteca.utilities.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookService implements Tools {

    @Autowired
    private BookRepository br;
    @Autowired
    private AuthorRepository ar;
    @Autowired
    private EditorialRepository er;

    @Transactional
    public void createBook(Long isbn, String title, Integer exemplars, String nameAuthor, String nameEditorial) throws CRUDException {
        /* Create author and editorial for book */
        Optional<Author> answerAuthor = ar.findByName(nameAuthor);
        if(!answerAuthor.isPresent())
            throw new CRUDException("No se puede crear el libro porque el autor no existe.");
        Optional<Editorial> answerEditorial = er.findByName(nameEditorial);
        if(!answerEditorial.isPresent())
            throw new CRUDException("No se puede crear el libro porque la editorial no existe.");
        /* If author and editorial exists, create and set book */
        validate("ISBN", isbn);
        validate("titulo", title);
        validate("ejemplares", exemplars);
        Book book = new Book(isbn, title, exemplars);
        book.setEntry(new Date());
        book.setRemovedDate(null);
        book.setRemoved(Boolean.FALSE);
        Author author = answerAuthor.get();
        book.setAuthor(author);
        Editorial editorial = answerEditorial.get();
        book.setEditorial(editorial);
        /* Save book */
        Optional<Book> answerBook = br.findById(book.getIsbn());
        if(answerBook.isPresent()) {
            Book bookOfAnswer = answerBook.get();
            if(book.compareTo(bookOfAnswer) == 0) {
                if(bookOfAnswer.getRemoved()) {
                    bookOfAnswer.setRemoved(Boolean.FALSE);
                    bookOfAnswer.setRemovedDate(null);
                    br.save(bookOfAnswer);
                }
                else { throw new CRUDException("El libro ya existe."); }
            }
            else { throw new CRUDException("El ISBN ya existe."); }
        }
        else { br.save(book); }
    }

    public List<Book> listBooks() { return br.findAll(); }

    public Boolean existsBook(Long isbn) {
        Optional<Book> answerBook = br.findByIdEnabled(isbn);
        return answerBook.isPresent();
    }

    @Transactional
    public void modifyBook(Long isbnToModify, Long isbn, String title, Integer exemplars, String nameAuthor, String nameEditorial) throws CRUDException {
        /* Find the book */
        Optional<Book> answerBook = br.findByIdEnabled(isbnToModify);
        Book book = answerBook.get();
        /* If find then modify */
        /* ISBN */
        if(isbn != null) { book.setIsbn(isbn); }
        /* Title */
        if(title != null && !title.isEmpty()) { book.setTitle(title); }
        /* Exemplars */
        if(exemplars != null) { book.setExemplars(exemplars); }
        /* Author */
        if(nameAuthor != null && !nameAuthor.isEmpty()) {
            Optional<Author> answerAuthor = ar.findByName(nameAuthor);
            if(answerAuthor.isPresent()) { book.setAuthor(answerAuthor.get()); }
            else { throw new CRUDException("El autor no existe."); }
        }
        /* Editorial */
        if(nameEditorial != null && !nameEditorial.isEmpty()) {
            Optional<Editorial> answerEditorial = er.findByName(nameEditorial);
            if(answerEditorial.isPresent()) { book.setEditorial(answerEditorial.get()); }
            else { throw new CRUDException("La editorial no existe."); }
        }
        /* Save modified book */
        br.save(book);
    }

    @Transactional
    public void removeBook(Long isbn) throws CRUDException {
        /* Find the book */
        validate("ISBN", isbn);
        Optional<Book> answerBook = br.findById(isbn);
        /* If the book exists then it's logically removed */
        if(answerBook.isPresent()) {
            Book book = answerBook.get();
            if(!book.getRemoved()) {
                book.setRemoved(Boolean.TRUE);
                book.setRemovedDate(new Date());
                br.save(book);
            }
            else { throw new CRUDException("El libro no existe."); }
        }
        else { throw new CRUDException("El libro no existe."); }
    }

    public HashMap<String, Object> createMap(String[] keys, List<Object> values) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(int i = 0; i < keys.length; ++i) { map.put(keys[i], values.get(i)); }
        return map;
    }

}
