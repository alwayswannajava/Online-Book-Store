package com.spring.onlinebookstore.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getKey(String key);

    Specification<T> getSpecification(String[] params);
}
