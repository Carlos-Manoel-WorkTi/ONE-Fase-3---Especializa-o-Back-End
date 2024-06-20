package com.example.desafio.booksjava;

import com.example.desafio.booksjava.principal.Principal;
import com.example.desafio.booksjava.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JavaBooks implements  CommandLineRunner{
    @Autowired
    private BookRepository  bookRepository;


    public static void main(String[] args) {
        SpringApplication.run(JavaBooks.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(bookRepository);
        principal.init();
    }
}
