package com.spring.onlinebookstore.service;

import com.spring.onlinebookstore.dto.BookDto;
import com.spring.onlinebookstore.dto.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.UpdateBookRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.BookMapper;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

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
        update(book, updateBookRequestDto);
        return bookMapper.toDto(book);
    }

    private void update(Book book, UpdateBookRequestDto updateBookRequestDto) {
        if (updateBookRequestDto.title() != null) {
            book.setTitle(updateBookRequestDto.title());
        }
        if (updateBookRequestDto.author() != null) {
            book.setAuthor(updateBookRequestDto.author());
        }
        if (updateBookRequestDto.isbn() != null
                && !book.getIsbn().equals(updateBookRequestDto.isbn())) {
            book.setIsbn(updateBookRequestDto.isbn());
        }
        if (updateBookRequestDto.price() != null) {
            book.setPrice(updateBookRequestDto.price());
        }
        if (updateBookRequestDto.description() != null) {
            book.setDescription(updateBookRequestDto.description());
        }
        if (updateBookRequestDto.coverImage() != null) {
            book.setCoverImage(updateBookRequestDto.coverImage());
        }
        bookRepository.save(book);
    }
}
