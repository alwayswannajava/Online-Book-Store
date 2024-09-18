package com.spring.onlinebookstore.repository;

import com.spring.onlinebookstore.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
