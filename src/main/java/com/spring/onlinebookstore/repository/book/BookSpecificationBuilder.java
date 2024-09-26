package com.spring.onlinebookstore.repository.book;

import com.spring.onlinebookstore.dto.SearchBookRequestDto;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.SpecificationBuilder;
import com.spring.onlinebookstore.repository.SpecificationProvider;
import com.spring.onlinebookstore.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(SearchBookRequestDto searchBookRequestDto) {
        Specification<Book> specification = Specification.where(null);
        if (searchBookRequestDto.author() != null && searchBookRequestDto.author().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationProvider.AUTHOR_SPECIFICATION_NAME)
                    .getSpecification(searchBookRequestDto.author()));
        }
        if (searchBookRequestDto.title() != null && searchBookRequestDto.title().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationProvider.TITLE_SPECIFICATION_NAME)
                    .getSpecification(searchBookRequestDto.title()));
        }
        if (searchBookRequestDto.isbn() != null && searchBookRequestDto.isbn().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(SpecificationProvider.ISBN_SPECIFICATION_NAME)
                    .getSpecification(searchBookRequestDto.isbn()));
        }
        return specification;
    }
}
