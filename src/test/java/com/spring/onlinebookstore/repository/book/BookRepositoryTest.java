package com.spring.onlinebookstore.repository.book;

import static com.spring.onlinebookstore.Constants.CORRECT_BOOK_ID;
import static com.spring.onlinebookstore.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.INCORRECT_CATEGORY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spring.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.model.Category;
import com.spring.onlinebookstore.repository.category.CategoryRepository;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("Find all books by category id")
    @Sql(scripts = {"classpath:database/scripts/book/add-book-test-data.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/scripts/book/remove-book-test-data.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoriesId_PositiveId_ReturnsPage() {
        Book book = bookRepository.findById(CORRECT_BOOK_ID).get();
        Category category = categoryRepository.findById(CORRECT_CATEGORY_ID).get();
        book.setCategories(Set.of(category));
        Page<BookDtoWithoutCategoryIds> actual = bookRepository
                .findAllByCategoriesId(CORRECT_CATEGORY_ID, pageRequest);
        assertEquals(1, actual.getTotalElements());
    }

    @Test
    @DisplayName("Find all books by non-existent category id")
    void findAllByCategoriesId_NonExistentId_ReturnsEmptyPage() {
        Page<BookDtoWithoutCategoryIds> actual = bookRepository
                .findAllByCategoriesId(INCORRECT_CATEGORY_ID,
                        pageRequest);
        assertEquals(0, actual.getTotalElements());
    }
}
