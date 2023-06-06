package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.NewsRepositoryImpl;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.validator.NewsDTORequestValidator;
import com.mjc.school.service.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsService implements BaseService<NewsRequestDto, NewsResponseDto, Long> {

    private final NewsDTORequestValidator newsValidator = new NewsDTORequestValidator();

    private final BaseRepository<NewsModel, Long> newsRepository = new NewsRepositoryImpl();

    private final NewsMapper mapper = new NewsMapper();

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
        NewsModel newsModel = newsRepository.readById(id).get();
        return mapper.mapModelToResponseDto(newsModel);
    }

    @Override
    public NewsResponseDto create(NewsRequestDto news) {

        newsValidator.validateNewsDTORequest(news);

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

        newsValidator.validateNewsDTORequest(news);

        return mapper.mapModelToResponseDto(
                newsRepository.update(
                        mapper.mapRequestDtoToModel(news)
                ));
    }

    @Override
    public boolean deleteById(Long id) {
        return newsRepository.deleteById(id);
    }
}
