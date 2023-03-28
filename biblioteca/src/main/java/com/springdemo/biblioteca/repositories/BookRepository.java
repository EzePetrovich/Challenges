package com.springdemo.biblioteca.repositories;

import com.springdemo.biblioteca.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn AND b.removed = false")
    public Optional<Book> findByIdEnabled(@Param("isbn") Long isbn);

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    public Book searchByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.author.name = :name")
    public List<Book> searchByAuthor(@Param("name") String name);

    @Query("SELECT b FROM Book b WHERE b.editorial.name = :name")
    public List<Book> searchByEditorial(@Param("name") String name);

}
