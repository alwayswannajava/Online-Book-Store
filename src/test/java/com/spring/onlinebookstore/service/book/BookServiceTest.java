package com.spring.onlinebookstore.service.book;

import static com.spring.onlinebookstore.util.Constants.BOOK_AUTHOR;
import static com.spring.onlinebookstore.util.Constants.BOOK_COVER_IMAGE;
import static com.spring.onlinebookstore.util.Constants.BOOK_DESCRIPTION;
import static com.spring.onlinebookstore.util.Constants.BOOK_ISBN;
import static com.spring.onlinebookstore.util.Constants.BOOK_PRICE;
import static com.spring.onlinebookstore.util.Constants.BOOK_TITLE;
import static com.spring.onlinebookstore.util.Constants.CORRECT_BOOK_ID;
import static com.spring.onlinebookstore.util.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.util.Constants.ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_BOOK_MESSAGE;
import static com.spring.onlinebookstore.util.Constants.INCORRECT_BOOK_ID;
import static com.spring.onlinebookstore.util.Constants.INCORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.util.Constants.SEARCH_BOOK_REQUEST_DTO_AUTHOR;
import static com.spring.onlinebookstore.util.Constants.SEARCH_BOOK_REQUEST_DTO_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.spring.onlinebookstore.dto.book.BookDto;
import com.spring.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.spring.onlinebookstore.dto.book.CreateBookRequestDto;
import com.spring.onlinebookstore.dto.book.SearchBookRequestDto;
import com.spring.onlinebookstore.dto.book.UpdateBookRequestDto;
import com.spring.onlinebookstore.exception.EntityNotFoundException;
import com.spring.onlinebookstore.mapper.BookMapper;
import com.spring.onlinebookstore.model.Book;
import com.spring.onlinebookstore.repository.book.BookRepository;
import com.spring.onlinebookstore.repository.book.BookSpecificationBuilder;
import com.spring.onlinebookstore.util.TestUtil;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    private CreateBookRequestDto createBookRequestDto;
    private UpdateBookRequestDto updateBookRequestDto;
    private Book book;
    private BookDto bookDto;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        createBookRequestDto = TestUtil.createBookRequestDtoWithBlankCategory();

        updateBookRequestDto = TestUtil.createUpdateBookRequestDtoWithBlankCategory();

        book = TestUtil.createBook();

        bookDto = TestUtil.createBookDtoWithoutCategory();

        pageRequest = TestUtil.createPageRequest();
    }

    @Test
    @DisplayName("Test save() method")
    void save_ValidCreateBookRequestDto_ReturnsBookDto() {
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);

        BookDto expected = bookDto;
        BookDto actual = bookService.save(createBookRequestDto);

        assertEquals(expected, actual);
        verify(bookRepository).save(book);
        verifyNoMoreInteractions(bookRepository, bookMapper);
    }

    @Test
    @DisplayName("Test findAll() method")
    void findAll_BookExists_ReturnsListBookDto() {
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        when(bookRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(book)));

        List<BookDto> expected = List.of(bookDto);
        List<BookDto> actual = bookService.findAll(pageRequest);

        assertEquals(expected, actual);
        verify(bookRepository).findAll(pageRequest);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Test findById() method")
    void findById_BookExists_ReturnsBookDto() {
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        when(bookRepository.findById(CORRECT_BOOK_ID)).thenReturn(Optional.ofNullable(book));

        BookDto expected = bookDto;
        BookDto actual = bookService.findById(CORRECT_BOOK_ID);

        assertEquals(expected, actual);
        verify(bookRepository).findById(CORRECT_BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Test findById() non-existent book")
    void findById_BookNotExists_ShouldThrowEntityNotFoundException() {
        when(bookRepository.findById(INCORRECT_BOOK_ID)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class,
                        () -> bookService.findById(INCORRECT_BOOK_ID));

        String actualMessage = entityNotFoundException.getMessage();

        assertEquals(ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_BOOK_MESSAGE, actualMessage);
    }

    @Test
    @DisplayName("Test update() method")
    void update_ValidUpdateBookRequestDto_ReturnsBookDto() {
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        when(bookRepository.findById(CORRECT_BOOK_ID)).thenReturn(Optional.ofNullable(book));

        BookDto expected = bookDto;
        expected.setTitle(updateBookRequestDto.title());
        expected.setPrice(updateBookRequestDto.price());

        BookDto actual = bookService.update(CORRECT_BOOK_ID, updateBookRequestDto);

        assertEquals(expected, actual);
        verify(bookRepository).findById(CORRECT_BOOK_ID);
    }

    @Test
    @DisplayName("Test update() non-existent book")
    void update_NonExistentBook_ShouldThrowEntityNotFoundException() {
        when(bookRepository.findById(INCORRECT_BOOK_ID)).thenReturn(Optional.empty());
        EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class, () ->
                        bookService.update(INCORRECT_BOOK_ID, updateBookRequestDto));

        String actualMessage = entityNotFoundException.getMessage();

        assertEquals(ENTITY_NOT_FOUND_EXCEPTION_EXPECTED_BOOK_MESSAGE, actualMessage);
    }

    @Test
    @DisplayName("Test search method")
    void search_ValidSearchBookRequestDto_ReturnsListBookDto() {
        SearchBookRequestDto searchParams = new SearchBookRequestDto(
                new String[]{SEARCH_BOOK_REQUEST_DTO_TITLE},
                new String[]{SEARCH_BOOK_REQUEST_DTO_AUTHOR},
                null
        );
        Specification<Book> bookSpecification = mock(Specification.class);

        when(bookSpecificationBuilder.build(searchParams)).thenReturn(bookSpecification);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        when(bookRepository.findAll(bookSpecification, pageRequest))
                .thenReturn(new PageImpl<>(List.of(book)));

        List<BookDto> expected = List.of(bookDto);
        List<BookDto> actual = bookService.search(searchParams, pageRequest);

        assertEquals(expected, actual);
        verify(bookSpecificationBuilder).build(searchParams);
        verify(bookRepository).findAll(bookSpecification, pageRequest);
        verify(bookMapper).toDto(book);
        verifyNoMoreInteractions(bookRepository, bookSpecificationBuilder, bookMapper);
    }

    @Test
    @DisplayName("Test findAllByCategoriesId() method")
    void findAllByCategoriesId_CategoryExists_ReturnsPageBookDtoWithoutIds() {
        BookDtoWithoutCategoryIds bookDtoWithoutCategoryIds = new BookDtoWithoutCategoryIds(
                CORRECT_BOOK_ID,
                BOOK_TITLE,
                BOOK_AUTHOR,
                BOOK_ISBN,
                BOOK_PRICE,
                BOOK_DESCRIPTION,
                BOOK_COVER_IMAGE
        );

        when(bookRepository.findAllByCategoriesId(CORRECT_CATEGORY_ID, pageRequest))
                .thenReturn(new PageImpl<>(List.of(bookDtoWithoutCategoryIds)));

        List<BookDtoWithoutCategoryIds> expected = List.of(bookDtoWithoutCategoryIds);
        Page<BookDtoWithoutCategoryIds> actual = bookRepository
                .findAllByCategoriesId(CORRECT_CATEGORY_ID, pageRequest);

        assertEquals(expected, actual.getContent());
        assertEquals(expected.size(), actual.getTotalElements());
        verify(bookRepository)
                .findAllByCategoriesId(CORRECT_CATEGORY_ID, pageRequest);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Test findAllByCategoriesId() by non-existent category id")
    void findAllByCategoriesId_CategoryNotExists_ReturnsPageBookDtoWithoutIds() {
        when(bookRepository.findAllByCategoriesId(INCORRECT_CATEGORY_ID, pageRequest))
                .thenReturn(new PageImpl<>(List.of()));

        Page<BookDtoWithoutCategoryIds> actual = bookRepository
                .findAllByCategoriesId(INCORRECT_CATEGORY_ID, pageRequest);

        assertEquals(0, actual.getTotalElements());
        verify(bookRepository)
                .findAllByCategoriesId(INCORRECT_CATEGORY_ID, pageRequest);
        verifyNoMoreInteractions(bookRepository);
    }
}
