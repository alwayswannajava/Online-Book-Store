package com.spring.onlinebookstore.controller;

import static com.spring.onlinebookstore.Constants.CATEGORY_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.CATEGORY_NAME;
import static com.spring.onlinebookstore.Constants.CATEGORY_URL;
import static com.spring.onlinebookstore.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.CREATE_CATEGORY_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.CREATE_CATEGORY_REQUEST_DTO_NAME;
import static com.spring.onlinebookstore.Constants.UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.UPDATE_CATEGORY_REQUEST_DTO_NAME;
import static com.spring.onlinebookstore.controller.BookControllerTest.mockMvc;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onlinebookstore.dto.category.CategoryDto;
import com.spring.onlinebookstore.dto.category.CreateCategoryRequestDto;
import com.spring.onlinebookstore.dto.category.UpdateCategoryRequestDto;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    private CategoryDto categoryDto;
    private CreateCategoryRequestDto createCategoryRequestDto;
    private UpdateCategoryRequestDto updateCategoryRequestDto;

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
                    new ClassPathResource("database/scripts/category/add-category-test-data.sql")
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
                    new ClassPathResource("database/scripts/category/remove-category-test-data.sql")
            );
        }
    }

    @Test
    @DisplayName("Create category")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createCategory_CreateCategoryRequestDto_ReturnsCategoryDto() throws Exception {
        String requestJson = objectMapper.writeValueAsString(createCategoryRequestDto);
        MvcResult result = mockMvc.perform(
                        post(CATEGORY_URL)
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto expected = setCreateCategoryRequestDtoToDto();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "name", "description");
    }

    @Test
    @DisplayName("Get all categories")
    @WithMockUser(username = "user", roles = "USER")
    void getAllCategories_CategoriesExist_ReturnsListCategoryDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(CATEGORY_URL)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> expected = List.of(categoryDto);

        List<CategoryDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        EqualsBuilder.reflectionEquals(expected, actual, "name", "description");
    }

    @Test
    @DisplayName("Get category by id")
    @WithMockUser(username = "user", roles = "USER")
    void getCategoryById_CategoryExists_ReturnsCategoryDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(CATEGORY_URL + "/{id}", CORRECT_CATEGORY_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto expected = categoryDto;

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "name", "description");
    }

    @Test
    @DisplayName("Delete category by id")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteCategoryById_CategoryExists_ReturnsNoContentStatus() throws Exception {
        mockMvc.perform(
                        delete(CATEGORY_URL + "/{id}", CORRECT_CATEGORY_ID)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @DisplayName("Update category by id")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateCategoryById_CategoryExists_ReturnsCategoryDto() throws Exception {
        String requestJson = objectMapper.writeValueAsString(updateCategoryRequestDto);
        MvcResult result = mockMvc.perform(
                        put(CATEGORY_URL + "/{id}", CORRECT_CATEGORY_ID)
                                .content(requestJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto expected = setUpdateCategoryRequestDtoToDto();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryDto.class
        );

        EqualsBuilder.reflectionEquals(expected, actual, "name", "description");
    }

    private CategoryDto setUpdateCategoryRequestDtoToDto() {

        CategoryDto expected = new CategoryDto(
                CORRECT_CATEGORY_ID,
                updateCategoryRequestDto.name(),
                updateCategoryRequestDto.description()
        );

        return expected;
    }

    private CategoryDto setCreateCategoryRequestDtoToDto() {

        CategoryDto expected = new CategoryDto(
                CORRECT_CATEGORY_ID,
                createCategoryRequestDto.name(),
                createCategoryRequestDto.description()
        );

        return expected;
    }

    private void initDto() {
        categoryDto = new CategoryDto(
                CORRECT_CATEGORY_ID,
                CATEGORY_NAME,
                CATEGORY_DESCRIPTION
        );

        createCategoryRequestDto = new CreateCategoryRequestDto(
                CREATE_CATEGORY_REQUEST_DTO_NAME,
                CREATE_CATEGORY_REQUEST_DTO_DESCRIPTION
        );

        updateCategoryRequestDto = new UpdateCategoryRequestDto(
                UPDATE_CATEGORY_REQUEST_DTO_NAME,
                UPDATE_CATEGORY_REQUEST_DTO_DESCRIPTION
        );
    }
}
