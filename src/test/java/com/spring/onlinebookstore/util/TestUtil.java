package com.spring.onlinebookstore.util;

import static com.spring.onlinebookstore.util.Constants.BOOK_AUTHOR;
import static com.spring.onlinebookstore.util.Constants.BOOK_CATEGORIES;
import static com.spring.onlinebookstore.util.Constants.BOOK_COVER_IMAGE;
import static com.spring.onlinebookstore.util.Constants.BOOK_DESCRIPTION;
import static com.spring.onlinebookstore.util.Constants.BOOK_ISBN;
import static com.spring.onlinebookstore.util.Constants.BOOK_PRICE;
import static com.spring.onlinebookstore.util.Constants.BOOK_TITLE;
import static com.spring.onlinebookstore.util.Constants.CATEGORY_DESCRIPTION;
import static com.spring.onlinebookstore.util.Constants.CATEGORY_NAME;
import static com.spring.onlinebookstore.util.Constants.CORRECT_BOOK_ID;
import static com.spring.onlinebookstore.util.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.util.Constants.CREATE_CATEGORY_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.util.Constants.CREATE_CATEGORY_REQUEST_DTO_NAME;
import static com.spring.onlinebookstore.util.Constants.UPDATE_BOOK_REQUEST_DTO_AUTHOR;
import static com.spring.onlinebookstore.util.Constants.UPDATE_BOOK_REQUEST_DTO_COVER_IMAGE;
import static com.spring.onlinebookstore.util.Constants.UPDATE_BOOK_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.util.Constants.UPDATE_BOOK_REQUEST_DTO_ISBN;
import static com.spring.onlinebookstore.util.Constants.UPDATE_BOOK_REQUEST_DTO_PRICE;
import static com.spring.onlinebookstore.util.Constants.UPDATE_BOOK_REQUEST_DTO_TITLE;
import static com.spring.onlinebookstore.util.Constants.UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.util.Constants.UPDATE_CATEGORY_REQUEST_DTO_NAME;

import com.spring.onlinebookstore.dto.book.BookDto;
import com.spring.onlinebookstore.dto.book.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.spring.onlinebookstore.dto.category.CategoryDto;
import com.spring.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.spring.onlinebookstore.dto.category.UpdateCategoryRequestDto;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.model.Category;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public final class TestUtil {
    private TestUtil() {

    }

    public static CreateBookRequestDto createBookRequestDto() {
        return new CreateBookRequestDto(
                BOOK_TITLE,
                BOOK_AUTHOR,
                BOOK_ISBN,
                BOOK_PRICE,
                BOOK_DESCRIPTION,
                BOOK_COVER_IMAGE,
                List.of(new Category(CORRECT_CATEGORY_ID)));
    }

    public static CreateBookRequestDto createBookRequestDtoWithBlankCategory() {
        return new CreateBookRequestDto(
                BOOK_TITLE,
                BOOK_AUTHOR,
                BOOK_ISBN,
                BOOK_PRICE,
                BOOK_DESCRIPTION,
                BOOK_COVER_IMAGE,
                BOOK_CATEGORIES
        );
    }

    public static BookDto createBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(CORRECT_BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);
        bookDto.setAuthor(BOOK_AUTHOR);
        bookDto.setIsbn("978-161-729-045-10");
        bookDto.setPrice(BigDecimal.valueOf(799));
        bookDto.setCategoryIds(List.of(new Category(CORRECT_BOOK_ID)));
        return bookDto;
    }

    public static BookDto createBookDtoWithoutCategory() {
        BookDto bookDto = new BookDto();
        bookDto.setId(CORRECT_BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);
        bookDto.setAuthor(BOOK_AUTHOR);
        bookDto.setIsbn(BOOK_ISBN);
        bookDto.setPrice(BOOK_PRICE);
        bookDto.setDescription(BOOK_DESCRIPTION);
        bookDto.setCoverImage(BOOK_COVER_IMAGE);
        return bookDto;
    }

    public static UpdateBookRequestDto createUpdateBookRequestDto() {
        return new UpdateBookRequestDto(
                UPDATE_BOOK_REQUEST_DTO_TITLE,
                UPDATE_BOOK_REQUEST_DTO_AUTHOR,
                UPDATE_BOOK_REQUEST_DTO_ISBN,
                UPDATE_BOOK_REQUEST_DTO_PRICE,
                UPDATE_BOOK_REQUEST_DTO_DESCRIPTION,
                UPDATE_BOOK_REQUEST_DTO_COVER_IMAGE,
                List.of(new Category(CORRECT_CATEGORY_ID)));
    }

    public static UpdateBookRequestDto createUpdateBookRequestDtoWithBlankCategory() {
        return new UpdateBookRequestDto(
                UPDATE_BOOK_REQUEST_DTO_TITLE,
                BOOK_AUTHOR,
                BOOK_ISBN,
                UPDATE_BOOK_REQUEST_DTO_PRICE,
                BOOK_DESCRIPTION,
                BOOK_COVER_IMAGE,
                BOOK_CATEGORIES
        );
    }

    public static CategoryDto createCategoryDto() {
        return new CategoryDto(
                CORRECT_CATEGORY_ID,
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );
    }

    public static CreateCategoryRequestDto createCategoryRequestDto() {
        return new CreateCategoryRequestDto(
                CREATE_CATEGORY_REQUEST_DTO_NAME,
                CREATE_CATEGORY_REQUEST_DTO_DESCRIPTION
        );
    }

    public static CreateCategoryRequestDto createCategoryRequestDtoWithCategoryParams() {
        return new CreateCategoryRequestDto(
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );
    }

    public static UpdateCategoryRequestDto createUpdateCategoryRequestDto() {
        return new UpdateCategoryRequestDto(
                UPDATE_CATEGORY_REQUEST_DTO_NAME,
                UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION
        );
    }

    public static CategoryDto createUpdateCategoryDto() {
        return new CategoryDto(
                CORRECT_CATEGORY_ID,
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );
    }

    public static Category createCategory() {
        Category category = new Category();
        category.setId(CORRECT_CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        return category;
    }

    public static Book createBook() {
        Book book = new Book();
        book.setId(CORRECT_BOOK_ID);
        book.setTitle(BOOK_TITLE);
        book.setAuthor(BOOK_AUTHOR);
        book.setIsbn(BOOK_ISBN);
        book.setPrice(BOOK_PRICE);
        book.setDescription(BOOK_DESCRIPTION);
        book.setCoverImage(BOOK_COVER_IMAGE);
        book.setCategories(new HashSet<>(BOOK_CATEGORIES));
        return book;
    }

    public static PageRequest createPageRequest() {
        return PageRequest.of(0, 10);
    }
}
