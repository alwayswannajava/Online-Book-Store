package com.spring.onlinebookstore.repository.book.specification;

import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String ISBN_SPECIFICATION_NAME = "isbn";

    @Override
    public String getKey(String key) {
        return ISBN_SPECIFICATION_NAME;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get(ISBN_SPECIFICATION_NAME)
                .in(Arrays.stream(params).toArray());
    }
}
