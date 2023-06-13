package com.mjc.school.service;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.impl.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO write unit tests
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    private static final long INITIAL_NUMBER_OF_AUTHORS = 5;
    private static final long VALID_AUTHOR_ID = 3L;
    private static final long INVALID_AUTHOR_ID = 66L;
    private static final String VALID_AUTHOR_NAME = "Valid Name";
    private static final String INVALID_AUTHOR_NAME = "Albus Percival Wulfric Brian Dumbledore";

    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    private final List<Author> authorList = new ArrayList<>();

    @BeforeEach
    void init() {
        authorService = new AuthorService(authorRepository);

        authorList.add(new Author(1L, "Agatha Christie", LocalDateTime.now(), LocalDateTime.now()));
        authorList.add(new Author(2L, "Husna Ahmad", LocalDateTime.now(), LocalDateTime.now()));
        authorList.add(new Author(3L, "Svenja Adolphs", LocalDateTime.now(), LocalDateTime.now()));
        authorList.add(new Author(4L, "Dolly Alderton", LocalDateTime.now(), LocalDateTime.now()));
        authorList.add(new Author(5L, "Sally Abbott", LocalDateTime.now(), LocalDateTime.now()));
    }

    @Test
    @DisplayName("readAll() returns initial list of authors")
    void readAll() {
        Mockito.when(authorRepository.readAll())
                .thenReturn(authorList);
        List<AuthorResponseDto> list = authorService.readAll();
        assertEquals(INITIAL_NUMBER_OF_AUTHORS, list.size());
    }

    @Test
    @DisplayName("readById() returns correct author")
    void getAuthorByValidId() {
        Mockito.when(authorRepository.existById(VALID_AUTHOR_ID))
                .thenReturn(true);
        Mockito.when(authorRepository.readById(VALID_AUTHOR_ID))
                .thenReturn(Optional.ofNullable(
                        authorList.get(authorList.indexOf(new Author(VALID_AUTHOR_ID))))
                );
        AuthorResponseDto author = authorService.readById(VALID_AUTHOR_ID);
        assertEquals(VALID_AUTHOR_ID, author.getId());
    }

    @Test
    @DisplayName("getAuthorById() with invalid id fails")
    void getAuthorByInvalidId() {
        Mockito.when(authorRepository.existById(INVALID_AUTHOR_ID))
                .thenReturn(false);
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                authorService.readById(INVALID_AUTHOR_ID));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }


    @Test
    @DisplayName("createAuthor() returns new author")
    void createValidAuthorAndCheckResponse() {
        Mockito
                .when(
                        authorRepository.create(Mockito.any(Author.class)))
                .thenReturn(
                        new Author(6L, VALID_AUTHOR_NAME, LocalDateTime.now(),
                                LocalDateTime.now()));


        AuthorResponseDto response = authorService.create(
                new AuthorRequestDto(null, VALID_AUTHOR_NAME)
        );
        assertEquals(VALID_AUTHOR_NAME, response.getName());
        assertNotNull(response.getId());
        assertNotNull(response.getCreateDate());
        assertNotNull(response.getLastUpdateDate());
    }

    @Test
    @DisplayName("create() with invalid name fails")
    void createAuthorWithInvalidName() {
        ValidatorException thrown = assertThrows(ValidatorException.class, () -> authorService.create(
                new AuthorRequestDto(null, INVALID_AUTHOR_NAME)));
        assertTrue(thrown.getMessage().contains("Author name can not be"));
    }


    @Test
    @DisplayName("updateAuthor() returns updated author")
    void updateValidAuthorAndCheckResponse() {
        Mockito.when(authorRepository.existById(VALID_AUTHOR_ID))
                .thenReturn(true);
        Mockito
                .when(authorRepository.update(Mockito.any(Author.class)))
                .thenReturn(new Author(
                        VALID_AUTHOR_ID, VALID_AUTHOR_NAME, LocalDateTime.now(),
                        LocalDateTime.now()));

        AuthorResponseDto response = authorService.update(
                new AuthorRequestDto(VALID_AUTHOR_ID, VALID_AUTHOR_NAME)
        );
        assertEquals(VALID_AUTHOR_ID, response.getId());
        assertEquals(VALID_AUTHOR_NAME, response.getName());
    }

    @Test
    @DisplayName("updateAuthor() with invalid name fails")
    void updateAuthorWithInvalidTitle() {
        Mockito.when(authorRepository.existById(VALID_AUTHOR_ID))
                .thenReturn(true);
        ValidatorException thrown = assertThrows(ValidatorException.class, () ->
                authorService.update(
                        new AuthorRequestDto(VALID_AUTHOR_ID, INVALID_AUTHOR_NAME)));
        assertTrue(thrown.getMessage().contains("Author name can not be"));
    }

    @Test
    @DisplayName("removeAuthor() return true if id existed")
    void removeAuthorWithValidId() {
        Mockito.when(authorRepository.existById(VALID_AUTHOR_ID))
                .thenReturn(true);
        Mockito.when(authorRepository.deleteById(VALID_AUTHOR_ID))
                .thenReturn(true);
        assertTrue(authorService.deleteById(VALID_AUTHOR_ID));
    }

    @Test
    @DisplayName("removeAuthor() with invalid author id fails")
    void removeAuthorWithInvalidId() {
        Mockito.when(authorRepository.existById(INVALID_AUTHOR_ID))
                .thenReturn(false);
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                authorService.deleteById(INVALID_AUTHOR_ID));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }
}
