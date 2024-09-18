package com.spring.onlinebookstore;

import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Book title #1");
            book.setAuthor("Author #1");
            book.setIsbn("ISBN #1");
            book.setPrice(BigDecimal.valueOf(650));
            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }

}
