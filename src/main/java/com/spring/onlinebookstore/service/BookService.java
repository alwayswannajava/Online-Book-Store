package com.spring.onlinebookstore.service;

import com.spring.onlinebookstore.dto.BookDto;
import com.spring.onlinebookstore.dto.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.UpdateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(long id);

    void deleteById(long id);

    BookDto update(long id, UpdateBookRequestDto updateBookRequestDto);
}
