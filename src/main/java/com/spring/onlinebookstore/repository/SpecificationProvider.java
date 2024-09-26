package com.spring.onlinebookstore.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String AUTHOR_SPECIFICATION_NAME = "author";
    String ISBN_SPECIFICATION_NAME = "isbn";
    String TITLE_SPECIFICATION_NAME = "title";

    String getKey(String key);

    Specification<T> getSpecification(String[] params);
}
