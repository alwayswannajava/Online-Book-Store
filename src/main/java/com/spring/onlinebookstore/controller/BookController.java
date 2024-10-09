package com.spring.onlinebookstore.controller;

import com.spring.onlinebookstore.dto.book.BookDto;
import com.spring.onlinebookstore.dto.book.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.book.SearchBookRequestDto;
import com.spring.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.spring.onlinebookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Online book store", description = "Endpoints for managing books")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get all books", description = "Get all books")
    @PreAuthorize("hasRole('USER')")
    public List<BookDto> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Get book by ID", description = "Get book by ID")
    @PreAuthorize("hasRole('USER')")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @Tag(name = "post", description = "POST methods of Book APIs")
    @Operation(summary = "Create a new book", description = "Create a new book")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto addBook(@RequestBody @Valid CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "delete", description = "DELETE methods of Book APIs")
    @Operation(summary = "Delete book", description = "Delete book")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Tag(name = "update", description = "UPDATE methods of Book APIs")
    @Operation(summary = "Update book", description = "Update book")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDto updateBook(@PathVariable Long id,
                              @RequestBody
                              @Valid UpdateBookRequestDto updateBookRequestDto) {
        return bookService.update(id, updateBookRequestDto);
    }

    @GetMapping("/search")
    @Tag(name = "get", description = "GET methods of Book APIs")
    @Operation(summary = "Search books", description = "Search book")
    @PreAuthorize("hasRole('USER')")
    public List<BookDto> search(SearchBookRequestDto searchBookRequestDto, Pageable pageable) {
        return bookService.search(searchBookRequestDto, pageable);
    }
}
