package com.spring.onlinebookstore.service.book;

import static com.spring.onlinebookstore.Constants.BOOK_AUTHOR;
import static com.spring.onlinebookstore.Constants.BOOK_CATEGORIES;
import static com.spring.onlinebookstore.Constants.BOOK_COVER_IMAGE;
import static com.spring.onlinebookstore.Constants.BOOK_DESCRIPTION;
import static com.spring.onlinebookstore.Constants.BOOK_ISBN;
import static com.spring.onlinebookstore.Constants.BOOK_PRICE;
import static com.spring.onlinebookstore.Constants.BOOK_TITLE;
import static com.spring.onlinebookstore.Constants.CORRECT_BOOK_ID;
import static com.spring.onlinebookstore.Constants.CORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.INCORRECT_BOOK_ID;
import static com.spring.onlinebookstore.Constants.INCORRECT_CATEGORY_ID;
import static com.spring.onlinebookstore.Constants.SEARCH_BOOK_REQUEST_DTO_AUTHOR;
import static com.spring.onlinebookstore.Constants.SEARCH_BOOK_REQUEST_DTO_TITLE;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_PRICE;
import static com.spring.onlinebookstore.Constants.UPDATE_BOOK_REQUEST_DTO_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import java.util.HashSet;
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
        createBookRequestDto = new CreateBookRequestDto(
                BOOK_TITLE,
                BOOK_AUTHOR,
                BOOK_ISBN,
                BOOK_PRICE,
                BOOK_DESCRIPTION,
                BOOK_COVER_IMAGE,
                BOOK_CATEGORIES
        );

        updateBookRequestDto = new UpdateBookRequestDto(
                UPDATE_BOOK_REQUEST_DTO_TITLE,
                BOOK_AUTHOR,
                BOOK_ISBN,
                UPDATE_BOOK_REQUEST_DTO_PRICE,
                BOOK_DESCRIPTION,
                BOOK_COVER_IMAGE,
                BOOK_CATEGORIES
        );

        book = new Book();
        book.setId(CORRECT_BOOK_ID);
        book.setTitle(BOOK_TITLE);
        book.setAuthor(BOOK_AUTHOR);
        book.setIsbn(BOOK_ISBN);
        book.setPrice(BOOK_PRICE);
        book.setDescription(BOOK_DESCRIPTION);
        book.setCoverImage(BOOK_COVER_IMAGE);
        book.setCategories(new HashSet<>(BOOK_CATEGORIES));

        bookDto = new BookDto();
        bookDto.setId(CORRECT_BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);
        bookDto.setAuthor(BOOK_AUTHOR);
        bookDto.setIsbn(BOOK_ISBN);
        bookDto.setPrice(BOOK_PRICE);
        bookDto.setDescription(BOOK_DESCRIPTION);
        bookDto.setCoverImage(BOOK_COVER_IMAGE);

        pageRequest = PageRequest.of(0, 10);
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
        verify(bookRepository, times(1)).save(book);
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
        verify(bookRepository, times(1)).findAll(pageRequest);
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
        verify(bookRepository, times(1)).findById(CORRECT_BOOK_ID);
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    @DisplayName("Test findById() non-existent book")
    void findById_BookNotExists_ShouldThrowEntityNotFoundException() {
        when(bookRepository.findById(INCORRECT_BOOK_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookService.findById(INCORRECT_BOOK_ID));
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
        verify(bookRepository, times(1)).findById(CORRECT_BOOK_ID);
    }

    @Test
    @DisplayName("Test update() non-existent book")
    void update_NonExistentBook_ShouldThrowEntityNotFoundException() {
        when(bookRepository.findById(INCORRECT_BOOK_ID)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                bookService.update(INCORRECT_BOOK_ID, updateBookRequestDto));
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
        verify(bookSpecificationBuilder, times(1)).build(searchParams);
        verify(bookRepository, times(1)).findAll(bookSpecification, pageRequest);
        verify(bookMapper, times(1)).toDto(book);
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
        verify(bookRepository, times(1))
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
        verify(bookRepository, times(1))
                .findAllByCategoriesId(INCORRECT_CATEGORY_ID, pageRequest);
        verifyNoMoreInteractions(bookRepository);
    }
}
