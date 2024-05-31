package com.challenge.literAlura.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    String name;
    Integer birth;
    Integer death;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author () {}

    public Author (DataAuthor author) {
        this.name = author.name();
        this.birth = author.birth();
        this.death = author.death();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(Book book) {
        book.setAuthor(this);
        this.books.add(book);
    }

    public Integer getDeath() {
        return death;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }

    public Integer getBirth() {
        return birth;
    }

    public void setBirth(Integer birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
