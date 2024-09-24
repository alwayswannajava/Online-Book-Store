package com.spring.onlinebookstore.controller;

import com.spring.onlinebookstore.dto.BookDto;
import com.spring.onlinebookstore.dto.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.UpdateBookRequestDto;
import com.spring.onlinebookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto addBook(@RequestBody CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable long id,
                              @RequestBody UpdateBookRequestDto updateBookRequestDto) {
        return bookService.update(id, updateBookRequestDto);
    }
}
