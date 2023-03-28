package com.springdemo.biblioteca.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@ToString
public class Author implements Comparable<Author> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private Boolean removed;

    @Temporal(TemporalType.DATE)
    private Date removedDate;

    public Author() {}

    public Author(String name) { this.name = name; }

    @Override
    public int compareTo(Author a) { return this.name.compareTo(a.getName()); }

}
