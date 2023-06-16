package com.mjc.school.service.validator;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NewsRequestDtoValidatorTest {

    private static final long VALID_NEWS_ID = 3L;
    private static final long VALID_AUTHOR_ID = 2L;
    private static final String VALID_NEWS_TITLE = "VALID TITLE";
    private static final String VALID_NEWS_CONTENT = "Valid content.";
    private static final long INVALID_NEWS_ID = 99L;
    private static final long INVALID_AUTHOR_ID = 66L;
    private static final String INVALID_NEWS_TITLE = "Ohhh";
    private static final String INVALID_NEWS_CONTENT = "Paradoxically, another property commonly attributed to news " +
            "is sensationalism, the disproportionate focus on, and exaggeration of, emotive stories for public " +
            "consumption. This news is also not unrelated to gossip, the human practice of sharing information.";

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private NewsRequestDtoValidator validator;


    @Test
    @DisplayName("validateNewsId() with existent id - OK")
    void getNewsByValidId() {
        Mockito.when(newsRepository.existById(VALID_NEWS_ID))
                .thenReturn(true);
        assertDoesNotThrow(() ->
                validator.validateNewsId(VALID_NEWS_ID));
    }

    @Test
    @DisplayName("validateNewsId() with non-existent id fails")
    void getNewsByInvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                validator.validateNewsId(INVALID_NEWS_ID));
        assertTrue(thrown.getMessage().contains("News with id %d does not exist".formatted(INVALID_NEWS_ID)));
    }

    @Test
    @DisplayName("validateAuthorId() with existent id - OK")
    void createNewsWithValidAuthorId() {
        Mockito.when(authorRepository.existById(VALID_AUTHOR_ID))
                .thenReturn(true);
        assertDoesNotThrow(() ->
                validator.validateAuthorId(VALID_AUTHOR_ID));
    }

    @Test
    @DisplayName("validateAuthorId() with non-existent id fails")
    void createNewsWithInvalidAuthorId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                validator.validateAuthorId(INVALID_AUTHOR_ID));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }

    @Test
    @DisplayName("validateNewsDTORequest() with valid NewsRequestDto - OK")
    void createNewsWithValidDTO() {
        assertDoesNotThrow(() ->
                validator.validateNewsDTORequest(
                        new NewsRequestDto(null, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("validateNewsDTORequest() with invalid title fails")
    void createNewsWithInvalidTitle() {
        ValidatorException thrown = assertThrows(ValidatorException.class, () ->
                validator.validateNewsDTORequest(
                        new NewsRequestDto(null, INVALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
        assertTrue(thrown.getMessage().contains("News title can not be"));
    }

    @Test
    @DisplayName("validateNewsDTORequest() with invalid content fails")
    void createNewsWithInvalidContent() {
        ValidatorException thrown = assertThrows(ValidatorException.class, () ->
                validator.validateNewsDTORequest(
                        new NewsRequestDto(
                                null, VALID_NEWS_TITLE, INVALID_NEWS_CONTENT, VALID_AUTHOR_ID)
                ));
        assertTrue(thrown.getMessage().contains("News content can not be"));
    }
}
