package com.spring.onlinebookstore.repository.book;

import com.spring.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.spring.onlinebookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {

    Page<BookDtoWithoutCategoryIds> findAllByCategoriesId(Long id, Pageable pageable);
}
