package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validator.annotations.ValidateInput;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsService implements BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> {

    private final NewsMapper mapper = new NewsMapper();

    private final BaseRepository<NewsModel, Long> newsRepository;

    public NewsService(@Qualifier("newsRepository") BaseRepository<NewsModel, Long> newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<ServiceNewsResponseDto> readAll() {
        List<ServiceNewsResponseDto> newsDtoList = new ArrayList<>();
        for (NewsModel newsModel : newsRepository.readAll()) {
            newsDtoList.add(mapper.mapModelToResponseDto(newsModel));
        }
        return newsDtoList;
    }

    @Override
    @ValidateInput
    public ServiceNewsResponseDto readById(Long id) {
        NewsModel newsModel = newsRepository.readById(id).get();
        return mapper.mapModelToResponseDto(newsModel);
    }

    @Override
    @ValidateInput
    public ServiceNewsResponseDto create(ServiceNewsRequestDto news) {
        ServiceNewsResponseDto newNews =
                new ServiceNewsResponseDto(
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
    @ValidateInput
    public ServiceNewsResponseDto update(ServiceNewsRequestDto news) {
        return mapper.mapModelToResponseDto(
                newsRepository.update(
                        mapper.mapRequestDtoToModel(news)
                ));
    }

    @Override
    @ValidateInput
    public boolean deleteById(Long id) {
        return newsRepository.deleteById(id);
    }
}
