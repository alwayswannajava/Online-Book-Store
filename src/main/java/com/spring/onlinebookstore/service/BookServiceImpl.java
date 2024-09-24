package com.spring.onlinebookstore.service;

import com.spring.onlinebookstore.dto.BookDto;
import com.spring.onlinebookstore.dto.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.SearchBookRequestDto;
import com.spring.onlinebookstore.dto.UpdateBookRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.BookMapper;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.BookRepository;
import com.spring.onlinebookstore.repository.BookSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        bookRepository.deleteById(book.getId());
    }

    @Override
    public BookDto update(long id, UpdateBookRequestDto updateBookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        Book updatedBook = bookMapper.toModel(updateBookRequestDto, book);
        bookRepository.save(updatedBook);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> search(SearchBookRequestDto searchBookRequestDto) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(searchBookRequestDto);
        return bookRepository.findAll(bookSpecification).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
