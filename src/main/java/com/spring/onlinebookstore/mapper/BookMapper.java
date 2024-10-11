package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.book.BookDto;
import com.spring.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.spring.onlinebookstore.dto.book.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.spring.onlinebookstore.model.Book;
import java.util.ArrayList;
import java.util.HashSet;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    Book toModel(UpdateBookRequestDto requestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            bookDto.setCategoryIds(new ArrayList<>(book.getCategories()));
        }
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget Book book,
                                CreateBookRequestDto createBookRequestDto) {
        if (book.getCategories() != null) {
            book.setCategories(new HashSet<>(createBookRequestDto.categoryIds()));
        }
    }

    @AfterMapping
    default void setCategoryIds(@MappingTarget Book book,
                                UpdateBookRequestDto updateBookRequestDto) {
        if (book.getCategories() != null) {
            book.setCategories(new HashSet<>(updateBookRequestDto.categoryIds()));
        }
    }
}
