package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.impl.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorServiceTest {
    private static final long INITIAL_NUMBER_OF_AUTHORS = 30;
    private static final long VALID_AUTHOR_ID = 6L;
    private static final long INVALID_AUTHOR_ID = 66L;
    private static final String VALID_AUTHOR_NAME = "Valid Name";
    private static final String INVALID_AUTHOR_NAME = "Albus Percival Wulfric Brian Dumbledore";

    private final BaseService<AuthorRequestDto, AuthorResponseDto, Long> authorService = new AuthorService();

    @Test
    @DisplayName("readAll() returns initial list of authors")
    void readAll() {
        List<AuthorResponseDto> list = authorService.readAll();
        assertEquals(INITIAL_NUMBER_OF_AUTHORS, list.size());
    }

    @Test
    @DisplayName("readById() returns correct author")
    void getAuthorByValidId() {
        AuthorResponseDto author = authorService.readById(VALID_AUTHOR_ID);
        assertEquals(VALID_AUTHOR_ID, author.getId());
    }

    @Test
    @DisplayName("getAuthorById() with invalid id fails")
    void getAuthorByInvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                authorService.readById(INVALID_AUTHOR_ID));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }


    @Test
    @DisplayName("createAuthor() returns new author")
    void createValidAuthorAndCheckResponse() {
        AuthorResponseDto response = authorService.create(
                new AuthorRequestDto(null, VALID_AUTHOR_NAME)
        );
        assertEquals(VALID_AUTHOR_NAME, response.getName());
        assertNotNull(response.getId());
        assertNotNull(response.getCreateDate());
        assertNotNull(response.getLastUpdateDate());
    }

    @Test
    @DisplayName("createAuthor() saves new author")
    void createValidAuthorAndCheckThatItWasWrittenToRepo() {
        AuthorResponseDto responseOfCreate = authorService.create(
                new AuthorRequestDto(null, VALID_AUTHOR_NAME)
        );
        AuthorResponseDto responseByGet = authorService.readById(responseOfCreate.getId());
        assertEquals(VALID_AUTHOR_NAME, responseByGet.getName());
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
        AuthorResponseDto response = authorService.update(
                new AuthorRequestDto(VALID_AUTHOR_ID, VALID_AUTHOR_NAME)
        );
        assertEquals(VALID_AUTHOR_ID, response.getId());
        assertEquals(VALID_AUTHOR_NAME, response.getName());
    }

    @Test
    @DisplayName("updateAuthor() saves updates")
    void updateValidAuthorAndCheckThatItWasWrittenToRepo() {
        AuthorResponseDto responseOfUpdate = authorService.update(
                new AuthorRequestDto(VALID_AUTHOR_ID, VALID_AUTHOR_NAME)
        );
        AuthorResponseDto responseByGet = authorService.readById(responseOfUpdate.getId());
        assertEquals(VALID_AUTHOR_ID, responseByGet.getId());
        assertEquals(VALID_AUTHOR_NAME, responseByGet.getName());
    }

    @Test
    @DisplayName("updateAuthor() with invalid id fails")
    void updateAuthorWithInvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                authorService.update(
                        new AuthorRequestDto(INVALID_AUTHOR_ID, VALID_AUTHOR_NAME)));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }

    @Test
    @DisplayName("updateAuthor() with invalid name fails")
    void updateAuthorWithInvalidTitle() {
        ValidatorException thrown = assertThrows(ValidatorException.class, () ->
                authorService.update(
                        new AuthorRequestDto(VALID_AUTHOR_ID, INVALID_AUTHOR_NAME)));
        assertTrue(thrown.getMessage().contains("Author name can not be"));
    }

    @Test
    @DisplayName("removeAuthor() deletes author")
    void removeAuthorWithValidId() {
        authorService.readById(VALID_AUTHOR_ID);
        assertTrue(authorService.deleteById(VALID_AUTHOR_ID));
        assertThrows(NotFoundException.class, () ->
                authorService.readById(VALID_AUTHOR_ID));
    }

    @Test
    @DisplayName("removeAuthor() with invalid author id fails")
    void removeAuthorWithInvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> authorService.deleteById(INVALID_AUTHOR_ID));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }
}
