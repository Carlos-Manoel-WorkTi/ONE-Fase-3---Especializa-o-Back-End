package com.example.desafio.booksjava.repository;

import com.example.desafio.booksjava.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b ")
    List<Book> allBooks();

    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.birth_year <= :year AND (a.death_year IS NULL OR a.death_year >= :year)")
    List<Book> findBooksByAuthorsAliveInYear(int year);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.languages l WHERE l.lang = :language")
    List<Book> findBooksByLanguage(String language);
}
