package com.spring.onlinebookstore.repository.book.specification;

import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {

    @Override
    public String getKey(String key) {
        return TITLE_SPECIFICATION_NAME;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get(TITLE_SPECIFICATION_NAME)
                .in(Arrays.stream(params).toArray());
    }
}
