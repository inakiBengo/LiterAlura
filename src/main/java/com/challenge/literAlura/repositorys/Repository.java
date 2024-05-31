package com.challenge.literAlura.repositorys;

import com.challenge.literAlura.models.Author;
import com.challenge.literAlura.models.Book;
import com.challenge.literAlura.models.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Repository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a WHERE a.name = %:name%")
    List<Author> getByAuthorName(String name);
    @Query("SELECT a FROM Author a WHERE :year BETWEEN a.birth AND a.death")
    List<Author> getByAuthorBirth(Integer year);
    @Query("SELECT b FROM Author a JOIN a.books b WHERE b.language ILIKE :language")
    List<Book> getBooksByLanguage(Languages language);
}
