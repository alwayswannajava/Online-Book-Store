package com.spring.onlinebookstore.repository.book;

import com.spring.onlinebookstore.exception.SpecificationProviderException;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.SpecificationProvider;
import com.spring.onlinebookstore.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(b -> b.getKey(key).equals(key))
                .findFirst()
                .orElseThrow(() ->
                        new SpecificationProviderException(
                                "Can't find correct specification provider "
                                        + "for key: " + key));
    }
}
