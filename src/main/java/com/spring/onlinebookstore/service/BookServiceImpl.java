package com.spring.onlinebookstore.service;

import com.spring.onlinebookstore.dto.BookDto;
import com.spring.onlinebookstore.dto.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.SearchBookRequestDto;
import com.spring.onlinebookstore.dto.UpdateBookRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.BookMapper;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.book.BookRepository;
import com.spring.onlinebookstore.repository.book.BookSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(Long id, UpdateBookRequestDto updateBookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        Book updatedBook = bookMapper.toModel(updateBookRequestDto, book);
        bookRepository.save(updatedBook);
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> search(SearchBookRequestDto params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(params);

        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
