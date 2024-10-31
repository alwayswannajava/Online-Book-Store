package com.spring.onlinebookstore.controller;

import static com.spring.onlinebookstore.Constants.BOOK_AUTHOR;
import static com.spring.onlinebookstore.Constants.BOOK_COVER_IMAGE;
import static com.spring.onlinebookstore.Constants.BOOK_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.BOOK_ISBN;
import static com.spring.onlinebookstore.Constants.BOOK_PRICE;
import static com.spring.onlinebookstore.Constants.BOOK_TITLE;
import static com.spring.onlinebookstore.Constants.BOOK_URL;
import static com.spring.onlinebookstore.Constants.CORRECT_BOOK_ID;
import static com.spring.onlinebookstore.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_AUTHOR;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_COVER_IMAGE;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_ISBN;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_PRICE;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_TITLE;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onlinebookstore.dto.book.BookDto;
import com.spring.onlinebookstore.dto.book.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.book.SearchBookRequestDto;
import com.spring.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.spring.onlinebookstore.model.Category;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private BookDto bookDto;
    private CreateBookRequestDto createBookRequestDto;
    private UpdateBookRequestDto updateBookRequestDto;

    @BeforeEach
    void setUp(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/scripts/book/add-book-test-data.sql")
            );
        }
        initDto();
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/scripts/book/remove-book-test-data.sql")
            );
        }
    }

    @Test
    @DisplayName("Create book")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createBook_CreateBookRequestDto_ReturnsBookDto() throws Exception {
        String requestJson = objectMapper.writeValueAsString(createBookRequestDto);
        MvcResult result = mockMvc.perform(
                post(BOOK_URL)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto expected = new BookDto();
        setCreateBookRequestDtoToDto(createBookRequestDto, expected);

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id", "title",
                "author", "price", "isbn", "categoryIds");
    }

    @Test
    @DisplayName("Get all books")
    @WithMockUser(username = "user", roles = "USER")
    void getAllBooks_BookExist_ReturnsListBookDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(BOOK_URL)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> expected = List.of(bookDto);

        List<BookDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id", "title",
                "author", "price", "isbn", "categoryIds");
    }

    @Test
    @DisplayName("Get book by id")
    @WithMockUser(username = "user", roles = "USER")
    void getBookById_BookExists_ReturnsBookDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(BOOK_URL + "/{id}", CORRECT_BOOK_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto expected = bookDto;

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id", "title",
                "author", "price", "isbn", "categoryIds");
    }

    @Test
    @DisplayName("Delete book by id")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteBookById_BookExists_ReturnsNoContentStatus() throws Exception {
        mockMvc.perform(
                        delete(BOOK_URL + "/{id}", CORRECT_BOOK_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Update book by id")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateBookById_BookExists_ReturnsBookDto() throws Exception {
        String requestJson = objectMapper.writeValueAsString(updateBookRequestDto);
        MvcResult result = mockMvc.perform(
                        put(BOOK_URL + "/{id}", CORRECT_BOOK_ID)
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto expected = new BookDto();
        setUpdateBookRequestDtoToDto(updateBookRequestDto, expected);

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );
        EqualsBuilder.reflectionEquals(expected, actual, "id", "title",
                "author", "price", "isbn", "categoryIds");
    }

    @Test
    @DisplayName("Search books by params")
    @WithMockUser(username = "user", roles = "USER")
    void search_BookExists_ReturnsListBookDto() throws Exception {
        SearchBookRequestDto searchParams = new SearchBookRequestDto(
                new String[]{"title"},
                null,
                null
        );

        String requestJson = objectMapper.writeValueAsString(searchParams);
        MvcResult result = mockMvc.perform(
                        get(BOOK_URL)
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<BookDto> expected = List.of(bookDto);

        List<BookDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        EqualsBuilder.reflectionEquals(expected, actual, "id", "title",
                "author", "price", "isbn", "categoryIds");
    }

    private void initDto() {
        createBookRequestDto = new CreateBookRequestDto(
        BOOK_TITLE,
        BOOK_AUTHOR,
        BOOK_ISBN,
        BOOK_PRICE,
        BOOK_DESCRIPTION,
        BOOK_COVER_IMAGE,
        List.of(new Category(CORRECT_CATEGORY_ID)));

        bookDto = new BookDto();
        bookDto.setId(CORRECT_BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);
        bookDto.setAuthor(BOOK_AUTHOR);
        bookDto.setIsbn("978-161-729-045-10");
        bookDto.setPrice(BigDecimal.valueOf(799));
        bookDto.setCategoryIds(List.of(new Category(CORRECT_BOOK_ID)));

        updateBookRequestDto = new UpdateBookRequestDto(
                UPDATE_BOOK_REQUEST_DTO_TITLE,
                UPDATE_BOOK_REQUEST_DTO_AUTHOR,
                UPDATE_BOOK_REQUEST_DTO_ISBN,
                UPDATE_BOOK_REQUEST_DTO_PRICE,
                UPDATE_BOOK_REQUEST_DTO_DESCRIPTION,
                UPDATE_BOOK_REQUEST_DTO_COVER_IMAGE,
                List.of(new Category(CORRECT_CATEGORY_ID)));
    }

    private void setCreateBookRequestDtoToDto(CreateBookRequestDto createBookRequestDto,
                                              BookDto bookDto) {
        bookDto.setId(2L);
        bookDto.setTitle(createBookRequestDto.title());
        bookDto.setAuthor(createBookRequestDto.author());
        bookDto.setIsbn(createBookRequestDto.isbn());
        bookDto.setPrice(createBookRequestDto.price());
        bookDto.setCategoryIds(createBookRequestDto.categoryIds());
        bookDto.setDescription(createBookRequestDto.description());
        bookDto.setCoverImage(createBookRequestDto.coverImage());
    }

    private void setUpdateBookRequestDtoToDto(UpdateBookRequestDto updateBookRequestDtoToDto,
                                              BookDto bookDto) {
        bookDto.setTitle(updateBookRequestDtoToDto.title());
        bookDto.setAuthor(updateBookRequestDtoToDto.author());
        bookDto.setIsbn(updateBookRequestDtoToDto.isbn());
        bookDto.setPrice(updateBookRequestDtoToDto.price());
        bookDto.setCategoryIds(updateBookRequestDtoToDto.categoryIds());
        bookDto.setDescription(updateBookRequestDtoToDto.description());
        bookDto.setCoverImage(updateBookRequestDtoToDto.coverImage());
    }
}
