package com.spring.onlinebookstore.mapper;

import com.spring.onlinebookstore.config.MapperConfig;
import com.spring.onlinebookstore.dto.book.BookDto;
import com.spring.onlinebookstore.dto.book.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.spring.onlinebookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    Book toModel(UpdateBookRequestDto requestDto, @MappingTarget Book book);
}
