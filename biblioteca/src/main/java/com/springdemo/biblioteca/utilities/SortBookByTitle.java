package com.springdemo.biblioteca.utilities;

import com.springdemo.biblioteca.entities.Book;
import java.util.Comparator;

public class SortBookByTitle implements Comparator<Book> {
    
    public int compare(Book b1, Book b2) { return b1.getTitle().compareTo(b2.getTitle()); }
}
