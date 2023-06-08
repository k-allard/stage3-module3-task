package com.mjc.school.service;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.impl.NewsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewsServiceTest {
    private static final long INITIAL_NUMBER_OF_NEWS = 30;
    private static final long VALID_NEWS_ID = 3L;
    private static final long INVALID_NEWS_ID = 99L;
    private static final long VALID_AUTHOR_ID = 6L;
    private static final long INVALID_AUTHOR_ID = 66L;
    private static final String VALID_NEWS_TITLE = "VALID TITLE";
    private static final String INVALID_NEWS_TITLE = "Ohhh";
    private static final String VALID_NEWS_CONTENT = "Valid content.";
    private static final String INVALID_NEWS_CONTENT = "Paradoxically, another property commonly attributed to news " +
            "is sensationalism, the disproportionate focus on, and exaggeration of, emotive stories for public " +
            "consumption. This news is also not unrelated to gossip, the human practice of sharing information.";

    private final BaseService<NewsRequestDto, NewsResponseDto, Long> newsService = new NewsService();

    @Test
    @DisplayName("getAllNews() returns initial list of news")
    void getAllNews() {
        List<NewsResponseDto> list = newsService.readAll();
        assertEquals(INITIAL_NUMBER_OF_NEWS, list.size());
    }

    @Test
    @DisplayName("getNewsById() returns correct news")
    void getNewsByValidId() {
        NewsResponseDto news = newsService.readById(VALID_NEWS_ID);
        assertEquals(VALID_NEWS_ID, news.getId());
    }

    @Test
    @DisplayName("getNewsById() with invalid id fails")
    void getNewsByInvalidId() {
        assertThrows(NotFoundException.class, () ->
                newsService.readById(INVALID_NEWS_ID));
    }


    @Test
    @DisplayName("createNews() returns new news")
    void createValidNewsAndCheckResponse() {
        NewsResponseDto response = newsService.create(
                new NewsRequestDto(null, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
        );
        assertEquals(VALID_NEWS_TITLE, response.getTitle());
        assertEquals(VALID_NEWS_CONTENT, response.getContent());
        assertEquals(VALID_AUTHOR_ID, response.getAuthorId());
        assertNotNull(response.getId());
        assertNotNull(response.getCreateDate());
        assertNotNull(response.getLastUpdateDate());
    }

    @Test
    @DisplayName("createNews() saves new news")
    void createValidNewsAndCheckThatItWasWrittenToRepo() {
        NewsResponseDto responseOfCreate = newsService.create(
                new NewsRequestDto(null, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
        );
        NewsResponseDto responseByGet = newsService.readById(responseOfCreate.getId());
        assertEquals(VALID_NEWS_TITLE, responseByGet.getTitle());
        assertEquals(VALID_NEWS_CONTENT, responseByGet.getContent());
        assertEquals(VALID_AUTHOR_ID, responseOfCreate.getAuthorId());
    }

    @Test
    @DisplayName("createNews() with invalid title fails")
    void createNewsWithInvalidTitle() {
        assertThrows(ValidatorException.class, () -> newsService.create(
                new NewsRequestDto(null, INVALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("createNews() with invalid content fails")
    void createNewsWithInvalidContent() {
        assertThrows(ValidatorException.class, () -> newsService.create(
                new NewsRequestDto(null, VALID_NEWS_TITLE, INVALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("createNews() with invalid authorId fails")
    void createNewsWithInvalidAuthorId() {
        assertThrows(NotFoundException.class, () -> newsService.create(
                new NewsRequestDto(null, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, INVALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("updateNews() returns updated news")
    void updateValidNewsAndCheckResponse() {
        NewsResponseDto response = newsService.update(
                new NewsRequestDto(VALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
        );
        assertEquals(VALID_NEWS_ID, response.getId());
        assertEquals(VALID_NEWS_TITLE, response.getTitle());
        assertEquals(VALID_NEWS_CONTENT, response.getContent());
        assertEquals(VALID_AUTHOR_ID, response.getAuthorId());
    }

    @Test
    @DisplayName("updateNews() saves updates")
    void updateValidNewsAndCheckThatItWasWrittenToRepo() {
        NewsResponseDto responseOfUpdate = newsService.update(
                new NewsRequestDto(VALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
        );
        NewsResponseDto responseByGet = newsService.readById(responseOfUpdate.getId());
        assertEquals(VALID_NEWS_ID, responseByGet.getId());
        assertEquals(VALID_NEWS_TITLE, responseByGet.getTitle());
        assertEquals(VALID_NEWS_CONTENT, responseByGet.getContent());
        assertEquals(VALID_AUTHOR_ID, responseByGet.getAuthorId());
    }

    @Test
    @DisplayName("updateNews() with invalid id fails")
    void updateNewsWithInvalidId() {
        assertThrows(NotFoundException.class, () -> newsService.update(
                new NewsRequestDto(INVALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("updateNews() with invalid title fails")
    void updateNewsWithInvalidTitle() {
        assertThrows(ValidatorException.class, () -> newsService.update(
                new NewsRequestDto(VALID_NEWS_ID, INVALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("updateNews() with invalid content fails")
    void updateNewsWithInvalidContent() {
        assertThrows(ValidatorException.class, () -> newsService.update(
                new NewsRequestDto(VALID_NEWS_ID, VALID_NEWS_TITLE, INVALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("updateNews() with invalid authorId fails")
    void updateNewsWithInvalidAuthorId() {
        assertThrows(NotFoundException.class, () -> newsService.update(
                new NewsRequestDto(VALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, INVALID_AUTHOR_ID)));
    }

    @Test
    @DisplayName("removeNews() deletes news")
    void removeNewsWithValidId() {
        newsService.readById(VALID_NEWS_ID);
        assertTrue(newsService.deleteById(VALID_NEWS_ID));
        assertThrows(NotFoundException.class, () -> newsService.readById(VALID_NEWS_ID));
    }

    @Test
    @DisplayName("removeNews() with invalid news id fails")
    void removeNewsWithInvalidId() {
        assertThrows(NotFoundException.class, () -> newsService.deleteById(INVALID_NEWS_ID));
    }
}
