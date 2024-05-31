package com.challenge.literAlura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String title;
    @Enumerated(EnumType.STRING)
    private Languages language;
    private Boolean copyright;
    private String downloads;
    @ManyToOne
    private Author author;

    public Book () {}

    public Book (DataBook book) {
        String language = book.languages().get(0);
        this.title = book.title();
        this.language = Languages.fromString(language);
        this.copyright = book.copyright();
        this.downloads = book.downloads();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages languege) {
        this.language = languege;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
