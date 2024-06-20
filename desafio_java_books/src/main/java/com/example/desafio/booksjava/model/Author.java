package com.example.desafio.booksjava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


@Entity
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_author;

    @JsonAlias("name")
    private String author;

    @JsonAlias("birth_year")
    private int birth_year;

    @JsonAlias("death_year")
    private int death_year;

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }


    public String getAuthor() {
        return author;
    }

    public Author() {

    }
    public Author(String author, int birth_year, int death_year) {
        this.author = author;
        this.birth_year = birth_year;
        this.death_year = death_year;
    }

    @ManyToOne
    @JoinColumn(name = "book_id") // Chave estrangeira para Book
    private Book book;


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId_author() {
        return id_author;
    }

    public void setId_author(Long id_author) {
        this.id_author = id_author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
