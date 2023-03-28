package com.springdemo.biblioteca.repositories;

import com.springdemo.biblioteca.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

    @Query("SELECT a FROM Author a WHERE a.name = :name")
    public Optional<Author> findByName(@Param("name") String name);

    @Query("SELECT a FROM Author a WHERE a.name = :name AND a.removed = false")
    public Optional<Author> findByNameRemove(@Param("name") String name);

    @Query("SELECT a FROM Author a WHERE a.name = :name")
    public List<Author> listByName(@Param("name") String name);

}
