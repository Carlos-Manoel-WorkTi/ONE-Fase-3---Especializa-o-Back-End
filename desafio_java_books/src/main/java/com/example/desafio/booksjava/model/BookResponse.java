package com.example.desafio.booksjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {
    private List<Book> results;

    public List<Book> getResults() {
        return results;
    }
}
