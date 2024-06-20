package com.example.desafio.booksjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "lang")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lang;

    @Column(name = "lang")
    private String lang;

    @ManyToOne
    @JoinColumn(name = "book_id") // Chave estrangeira para Book
    private Book book;

    public Long getId_lang() {
        return id_lang;
    }

    public void setId_lang(Long id_lang) {
        this.id_lang = id_lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Languages() {
    }

    public Languages(String lang) {
        this.lang = lang;
    }
}
