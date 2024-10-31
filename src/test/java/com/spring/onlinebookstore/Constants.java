package com.spring.onlinebookstore;

import com.spring.onlinebookstore.model.Category;
import java.math.BigDecimal;
import java.util.List;

public final class Constants {
    public static final Long CORRECT_BOOK_ID = 1L;
    public static final Long INCORRECT_BOOK_ID = 1000L;
    public static final Long CORRECT_CATEGORY_ID = 1L;
    public static final Long INCORRECT_CATEGORY_ID = 1000L;

    public static final String BOOK_TITLE = "Sample book 1";
    public static final String BOOK_AUTHOR = "J.K Rowling";
    public static final String BOOK_ISBN = "978-161-729-045-9";
    public static final BigDecimal BOOK_PRICE = BigDecimal.valueOf(1399.99);
    public static final String BOOK_DESCRIPTION = "J.K Rowling's book about Harry Potter";
    public static final String BOOK_COVER_IMAGE = "coverImage.jpg";
    public static final List<Category> BOOK_CATEGORIES = List.of(new Category());

    public static final String UPDATE_BOOK_REQUEST_DTO_TITLE = "Sample book 2";
    public static final String UPDATE_BOOK_REQUEST_DTO_AUTHOR = "J.K Rowling";
    public static final String UPDATE_BOOK_REQUEST_DTO_ISBN = "978-161-729-045-9";
    public static final String UPDATE_BOOK_REQUEST_DTO_DESCRIPTION = "J.K Rowling's book "
            + "about Harry Potter";
    public static final String UPDATE_BOOK_REQUEST_DTO_COVER_IMAGE = "coverImageUpdated.jpg";
    public static final BigDecimal UPDATE_BOOK_REQUEST_DTO_PRICE = BigDecimal.valueOf(1699.99);

    public static final String SEARCH_BOOK_REQUEST_DTO_TITLE = "title";
    public static final String SEARCH_BOOK_REQUEST_DTO_AUTHOR = "author";

    public static final String CATEGORY_NAME = "Novel";
    public static final String CATEGORY_DESCRIPTION = "Novel category";

    public static final String CREATE_CATEGORY_REQUEST_DTO_NAME = "Horror";
    public static final String CREATE_CATEGORY_REQUEST_DTO_DESCRIPTION = "Horror category";

    public static final String UPDATE_CATEGORY_REQUEST_DTO_NAME = "Fantastic";
    public static final String UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION = "Fantastic category";

    public static final String BOOK_URL = "/books";
    public static final String CATEGORY_URL = "/categories";

    private Constants() {

    }
}
