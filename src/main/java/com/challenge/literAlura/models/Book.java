package com.challenge.literAlura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String languages;
    private Boolean copyright;
    private String downloads;
    @ManyToOne
    private Author author;

    public Book () {}

    public Book (DataBook book) {
        this.title = book.title();
        this.title = String.join("",book.languages());
        this.copyright = book.copyright();
        this.downloads = book.downloads();
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

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
