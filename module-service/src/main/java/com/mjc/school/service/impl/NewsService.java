package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtendedRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ExtendedService;
import com.mjc.school.service.dto.ServiceAuthorResponseDto;
import com.mjc.school.service.dto.ServiceNewsRequestDto;
import com.mjc.school.service.dto.ServiceNewsResponseDto;
import com.mjc.school.service.dto.ServiceTagDto;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.validator.annotations.ValidateInput;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService implements BaseService<ServiceNewsRequestDto, ServiceNewsResponseDto, Long>, ExtendedService {

    private final NewsMapper newsMapper;

    private final AuthorMapper authorMapper = new AuthorMapper();

    private final TagMapper tagMapper = new TagMapper();

    private final BaseRepository<News, Long> newsRepository;

    private final BaseRepository<Author, Long> authorRepository;

    private final ExtendedRepository extendedRepository;

    public NewsService(@Qualifier("newsRepository")
                       BaseRepository<News, Long> newsRepository,
                       ExtendedRepository extendedRepository,
                       NewsMapper newsMapper,
                       @Qualifier("authorRepository")
                       BaseRepository<Author, Long> authorRepository) {
        this.newsMapper = newsMapper;
        this.newsRepository = newsRepository;
        this.extendedRepository = extendedRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<ServiceNewsResponseDto> readAll() {
        List<ServiceNewsResponseDto> newsDtoList = new ArrayList<>();
        for (News news : newsRepository.readAll()) {
            ServiceNewsResponseDto newsResponseDto = newsMapper.mapModelToResponseDto(news);
            newsDtoList.add(newsResponseDto);
        }
        return newsDtoList;
    }

    @Override
    @ValidateInput
    public ServiceNewsResponseDto readById(Long id) {
        News news = newsRepository.readById(id).get();
        return newsMapper.mapModelToResponseDto(news);
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
                        news.getAuthorId(),
                        news.getNewsTagsIds());
        News model = newsMapper.mapResponseDtoToModel(newNews);
        return newsMapper.mapModelToResponseDto(newsRepository.create(
                model
        ));
    }

    @Override
    @ValidateInput
    public ServiceNewsResponseDto update(ServiceNewsRequestDto news) {
        return newsMapper.mapModelToResponseDto(
                newsRepository.update(
                        newsMapper.mapRequestDtoToModel(news)
                ));
    }

    @Override
    @ValidateInput
    public boolean deleteById(Long id) {
        return newsRepository.deleteById(id);
    }

    @Override
    @ValidateInput
    public ServiceAuthorResponseDto readAuthorByNewsId(Long id) {
        Author authorModel = authorRepository.readById(
                newsRepository.readById(id).get().getAuthor().getId()).get();
        return authorMapper.mapModelToResponseDto(authorModel);
    }

    @Override
    @ValidateInput
    public List<ServiceTagDto> readTagsByNewsId(Long id) {
        List<ServiceTagDto> tagDtos = new ArrayList<>();
        for (Tag tag : extendedRepository.getTagsByNewsId(id)) {
            tagDtos.add(tagMapper.mapModelToServiceDto(tag));
        }
        return tagDtos;
    }

    @Override
    public List<ServiceNewsResponseDto> readNewsByParams(
            List<Long> tagsIds,
            String tagName,
            String authorName,
            String title,
            String content) {
        List<News> news = extendedRepository.readNewsByParams(tagsIds, tagName, authorName, title, content);
        List<ServiceNewsResponseDto> response = new ArrayList<>();
        for (News pieceOfNews : news) {
            response.add(newsMapper.mapModelToResponseDto(pieceOfNews));
        }
        return response;
    }
}
