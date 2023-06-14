package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.validator.NewsDTORequestValidator;
import com.mjc.school.service.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionsCodes.NEWS_ID_DOES_NOT_EXIST;

public class NewsService implements BaseService<NewsRequestDto, NewsResponseDto, Long> {

    //TODO think of declarative validation outside of business logic, e.g. via custom annotations.
    //  use Aspects.
    private final NewsDTORequestValidator newsValidator = new NewsDTORequestValidator();

    private final NewsMapper mapper = new NewsMapper();

    private final BaseRepository<NewsModel, Long> newsRepository;


    public NewsService(@Qualifier("newsRepository") BaseRepository<NewsModel, Long> newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<NewsResponseDto> readAll() {
        List<NewsResponseDto> newsDtoList = new ArrayList<>();
        for (NewsModel newsModel : newsRepository.readAll()) {
            newsDtoList.add(mapper.mapModelToResponseDto(newsModel));
        }
        return newsDtoList;
    }

    @Override
    public NewsResponseDto readById(Long id) {
        newsValidator.validateNewsId(id);
        if (!newsRepository.existById(id)) {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
        NewsModel newsModel = newsRepository.readById(id).get();
        return mapper.mapModelToResponseDto(newsModel);
    }

    @Override
    public NewsResponseDto create(NewsRequestDto news) {

        newsValidator.validateNewsDTORequest(news);
        newsValidator.validateAuthorId(news.getAuthorId());

        NewsResponseDto newNews =
                new NewsResponseDto(
                        null,
                        news.getTitle(),
                        news.getContent(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        news.getAuthorId());
        return mapper.mapModelToResponseDto(newsRepository.create(
                mapper.mapResponseDtoToModel(newNews)
        ));
    }

    @Override
    public NewsResponseDto update(NewsRequestDto news) {
        newsValidator.validateNewsId(news.getId());
        newsValidator.validateAuthorId(news.getAuthorId());
        if (!newsRepository.existById(news.getId())) {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), news.getId()));
        }

        newsValidator.validateNewsDTORequest(news);

        return mapper.mapModelToResponseDto(
                newsRepository.update(
                        mapper.mapRequestDtoToModel(news)
                ));
    }

    @Override
    public boolean deleteById(Long id) {
        newsValidator.validateNewsId(id);
        if (!newsRepository.existById(id)) {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
        return newsRepository.deleteById(id);
    }
}
