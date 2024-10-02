package com.spring.onlinebookstore.repository;

import com.spring.onlinebookstore.dto.book.SearchBookRequestDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(SearchBookRequestDto searchBookRequestDto);
}
