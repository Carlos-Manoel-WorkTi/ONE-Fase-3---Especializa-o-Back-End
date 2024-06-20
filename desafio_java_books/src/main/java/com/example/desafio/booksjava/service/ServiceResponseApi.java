package com.example.desafio.booksjava.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceResponseApi {
    private String name = "https://gutendex.com/books?search=";

    public void setName(String name) {
        this.name += name;
    }

    public String getName() {
        return name;
    }

    public <T> T getDate(String title, Class<T> classe) throws JsonParseException, IOException {
        ObjectMapper maper = new ObjectMapper();
        return maper.readValue(new URL(getName() + title), classe);
    }
}
