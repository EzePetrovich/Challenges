package com.springdemo.biblioteca.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Book implements Comparable<Book> {

    @Id
    private Long isbn;

    private String title;

    /* Number of exemplars */
    private Integer exemplars;

    /* Book entry date */
    @Temporal(TemporalType.DATE)
    private Date entry;

    /* If the book it's removed then is true */
    private Boolean removed;

    /* Book drop date */
    @Temporal(TemporalType.DATE)
    private Date removedDate;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Editorial editorial;


    public Book() {}

    public Book(Long isbn, String title, Integer exemplars) {
        this.isbn = isbn;
        this.title = title;
        this.exemplars = exemplars;
    }

    @Override
    public int compareTo(Book b) {
        int value = this.isbn.compareTo(b.getIsbn());
        value += this.title.compareTo(b.getTitle());
        value += this.exemplars.compareTo(b.getExemplars());
        value += this.author.getId().compareTo(b.getAuthor().getId());
        value += this.editorial.getId().compareTo(b.getEditorial().getId());
        return value;
    }

}
