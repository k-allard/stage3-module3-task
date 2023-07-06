package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.validator.annotations.ValidateInput;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService implements BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long> {

    private final NewsMapper mapper = new NewsMapper();

    private final BaseRepository<News, Long> newsRepository;

    public NewsService(@Qualifier("newsRepository")
                       BaseRepository<News, Long> newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<ServiceNewsResponseDto> readAll() {
        List<ServiceNewsResponseDto> newsDtoList = new ArrayList<>();
        for (News news : newsRepository.readAll()) {
            newsDtoList.add(mapper.mapModelToResponseDto(news));
        }
        return newsDtoList;
    }

    @Override
    @ValidateInput
    public ServiceNewsResponseDto readById(Long id) {
        News news = newsRepository.readById(id).get();
        return mapper.mapModelToResponseDto(news);
    }

    @Override
    @ValidateInput
    public ServiceNewsResponseDto create(ServiceNewsRequestDto news) {
        System.out.println("ServiceNewsRequestDto auth id is " + news.getAuthorId());
        ServiceNewsResponseDto newNews =
                new ServiceNewsResponseDto(
                        null,
                        news.getTitle(),
                        news.getContent(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        news.getAuthorId());
        News model = mapper.mapRequestDtoToModel(news);
        System.out.println("NewsModel auth id is " + model.getAuthor().getId());
        ServiceNewsResponseDto serviceNewsResponseDto = mapper.mapModelToResponseDto(newsRepository.create(
                model
        ));
        System.out.println("ServiceNewsResponseDto auth id is " + serviceNewsResponseDto.getAuthor_id());
        return serviceNewsResponseDto;
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
