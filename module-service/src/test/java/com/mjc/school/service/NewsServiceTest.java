package com.mjc.school.service;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.congiguration.AppConfig;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.mapper.NewsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    private static final long INITIAL_NUMBER_OF_NEWS = 5;
    private static final long VALID_NEWS_ID = 3L;
    private static final long INVALID_NEWS_ID = 99L;
    private static final long VALID_AUTHOR_ID = 2L;
    private static final long INVALID_AUTHOR_ID = 66L;
    private static final String VALID_NEWS_TITLE = "VALID TITLE";
    private static final String INVALID_NEWS_TITLE = "Ohhh";
    private static final String VALID_NEWS_CONTENT = "Valid content.";
    private static final String INVALID_NEWS_CONTENT = "Paradoxically, another property commonly attributed to news " +
            "is sensationalism, the disproportionate focus on, and exaggeration of, emotive stories for public " +
            "consumption. This news is also not unrelated to gossip, the human practice of sharing information.";

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsMapper newsMapper;

    @InjectMocks
    private NewsService newsService;

    private final List<NewsModel> newsList = new ArrayList<>();

    @BeforeEach
    void init() {
        newsList.add(new NewsModel(1L, "FINANCIAL INSTITUTIONS", "One man's drastic 400 pound weight loss spurred", LocalDateTime.now(), LocalDateTime.now(), 1L));
        newsList.add(new NewsModel(2L, "COMMERCE AND TRADE", "An inspiring couple recreating their wedding", LocalDateTime.now(), LocalDateTime.now(), 2L));
        newsList.add(new NewsModel(3L, "UNIFORM COMMERCIAL CODE", "The worst flood in India in 100 years brings Twitter", LocalDateTime.now(), LocalDateTime.now(), 3L));
        newsList.add(new NewsModel(4L, "CONSERVATION", "A Nigerian boy solves a 30-year math equation", LocalDateTime.now(), LocalDateTime.now(), 4L));
        newsList.add(new NewsModel(5L, "CORPORATIONS", "My doctor ordered a $6,000 treatment machine", LocalDateTime.now(), LocalDateTime.now(), 5L));
    }

    @Test
    @DisplayName("getAllNews() returns initial list of news")
    void getAllNews() {
        Mockito.when(newsRepository.readAll())
                .thenReturn(newsList);
        List<NewsResponseDto> list = newsService.readAll();
        assertEquals(INITIAL_NUMBER_OF_NEWS, list.size());
    }

    @Test
    @DisplayName("getNewsById() returns correct news")
    void getNewsByValidId() {
        Mockito.when(newsRepository.existById(VALID_NEWS_ID))
                .thenReturn(true);
        Mockito.when(newsRepository.readById(VALID_NEWS_ID))
                .thenReturn(Optional.ofNullable(
                        newsList.get(newsList.indexOf(new NewsModel(VALID_NEWS_ID))))
                );
        NewsResponseDto news = newsService.readById(VALID_NEWS_ID);
        assertEquals(VALID_NEWS_ID, news.getId());
    }

    @Test
    @DisplayName("getNewsById() with invalid id fails")
    void getNewsByInvalidId() {
        Mockito.when(newsRepository.existById(INVALID_NEWS_ID))
                .thenReturn(false);
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                newsService.readById(INVALID_NEWS_ID));
        assertTrue(thrown.getMessage().contains("News with id %d does not exist".formatted(INVALID_NEWS_ID)));
    }


    @Test
    @DisplayName("createNews() returns new news")
    void createValidNewsAndCheckResponse() {
        Mockito
                .when(
                        newsRepository.create(any(NewsModel.class)))
                .thenReturn(
                        new NewsModel(6L, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, LocalDateTime.now(),
                                LocalDateTime.now(), VALID_AUTHOR_ID));


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
    @DisplayName("createNews() with invalid title fails")
    void createNewsWithInvalidTitle() {
        ValidatorException thrown = assertThrows(ValidatorException.class, () -> newsService.create(
                new NewsRequestDto(null, INVALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
        assertTrue(thrown.getMessage().contains("News title can not be"));
    }

    @Test
    @DisplayName("createNews() with invalid content fails")
    void createNewsWithInvalidContent() {
        ValidatorException thrown = assertThrows(ValidatorException.class, () -> newsService.create(
                new NewsRequestDto(null, VALID_NEWS_TITLE, INVALID_NEWS_CONTENT, VALID_AUTHOR_ID)));
        assertTrue(thrown.getMessage().contains("News content can not be"));
    }

    @Test
    @DisplayName("createNews() with invalid authorId fails")
    void createNewsWithInvalidAuthorId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> newsService.create(
                new NewsRequestDto(null, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, INVALID_AUTHOR_ID)));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }

    @Test
    @DisplayName("updateNews() returns updated news")
    void updateValidNewsAndCheckResponse() {
        Mockito.when(newsRepository.existById(VALID_NEWS_ID))
                .thenReturn(true);
        Mockito
                .when(newsRepository.update(any(NewsModel.class)))
                .thenReturn(new NewsModel(
                        VALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, LocalDateTime.now(),
                        LocalDateTime.now(), VALID_AUTHOR_ID));

        NewsResponseDto response = newsService.update(
                new NewsRequestDto(VALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
        );
        assertEquals(VALID_NEWS_ID, response.getId());
        assertEquals(VALID_NEWS_TITLE, response.getTitle());
        assertEquals(VALID_NEWS_CONTENT, response.getContent());
        assertEquals(VALID_AUTHOR_ID, response.getAuthorId());
    }


    @Test
    @DisplayName("updateNews() with invalid id fails")
    void updateNewsWithInvalidId() {
        Mockito.when(newsRepository.existById(INVALID_NEWS_ID))
                .thenReturn(false);
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                newsService.update(
                        new NewsRequestDto(
                                INVALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
                ));
        assertTrue(thrown.getMessage().contains("News with id %d does not exist".formatted(INVALID_NEWS_ID)));
    }

    @Test
    @DisplayName("updateNews() with invalid title fails")
    void updateNewsWithInvalidTitle() {
        Mockito.when(newsRepository.existById(VALID_NEWS_ID))
                .thenReturn(true);
        ValidatorException thrown = assertThrows(ValidatorException.class, () ->
                newsService.update(
                        new NewsRequestDto(
                                VALID_NEWS_ID, INVALID_NEWS_TITLE, VALID_NEWS_CONTENT, VALID_AUTHOR_ID)
                ));
        assertTrue(thrown.getMessage().contains("News title can not be"));
    }

    @Test
    @DisplayName("updateNews() with invalid content fails")
    void updateNewsWithInvalidContent() {
        Mockito.when(newsRepository.existById(VALID_NEWS_ID))
                .thenReturn(true);
        ValidatorException thrown = assertThrows(ValidatorException.class, () ->
                newsService.update(
                        new NewsRequestDto(
                                VALID_NEWS_ID, VALID_NEWS_TITLE, INVALID_NEWS_CONTENT, VALID_AUTHOR_ID)
                ));
        assertTrue(thrown.getMessage().contains("News content can not be"));
    }

    @Test
    @DisplayName("updateNews() with invalid authorId fails")
    void updateNewsWithInvalidAuthorId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                newsService.update(
                        new NewsRequestDto(
                                VALID_NEWS_ID, VALID_NEWS_TITLE, VALID_NEWS_CONTENT, INVALID_AUTHOR_ID)
                ));
        assertTrue(thrown.getMessage().contains("Author Id does not exist"));
    }

    @Test
    @DisplayName("removeNews() return true if id existed")
    void removeNewsWithValidId() {
        Mockito.when(newsRepository.existById(VALID_NEWS_ID))
                .thenReturn(true);
        Mockito.when(newsRepository.deleteById(VALID_NEWS_ID))
                .thenReturn(true);
        assertTrue(newsService.deleteById(VALID_NEWS_ID));
    }

    @Test
    @DisplayName("removeNews() with invalid news id fails")
    void removeNewsWithInvalidId() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () ->
                newsService.deleteById(INVALID_NEWS_ID));
        assertTrue(thrown.getMessage().contains("News with id %d does not exist".formatted(INVALID_NEWS_ID)));
    }
}
