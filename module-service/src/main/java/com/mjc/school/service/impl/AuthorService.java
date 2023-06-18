package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.ServiceAuthorRequestDto;
import com.mjc.school.service.dto.ServiceAuthorResponseDto;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.annotations.ValidateInput;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorService implements BaseService<ServiceAuthorRequestDto, ServiceAuthorResponseDto, Long> {

    private final AuthorMapper mapper = new AuthorMapper();

    private final BaseRepository<Author, Long> authorRepository;

    private final BaseRepository<NewsModel, Long> newsRepository;


    public AuthorService(BaseRepository<Author, Long> authorRepository,
                         BaseRepository<NewsModel, Long> newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<ServiceAuthorResponseDto> readAll() {
        List<ServiceAuthorResponseDto> serviceAuthorResponseDtoList = new ArrayList<>();
        for (Author author : authorRepository.readAll()) {
            serviceAuthorResponseDtoList.add(mapper.mapModelToResponseDto(author));
        }
        return serviceAuthorResponseDtoList;
    }

    @Override
    @ValidateInput
    public ServiceAuthorResponseDto readById(Long id) {
        Author author = authorRepository.readById(id).get();
        return mapper.mapModelToResponseDto(author);
    }

    @Override
    @ValidateInput
    public ServiceAuthorResponseDto create(ServiceAuthorRequestDto serviceAuthorRequestDto) {
        ServiceAuthorResponseDto newAuthor =
                new ServiceAuthorResponseDto(
                        null,
                        serviceAuthorRequestDto.getName(),
                        LocalDateTime.now(),
                        LocalDateTime.now());
        return mapper.mapModelToResponseDto(authorRepository.create(
                mapper.mapResponseDtoToModel(newAuthor)
        ));
    }

    @Override
    @ValidateInput
    public ServiceAuthorResponseDto update(ServiceAuthorRequestDto authorUpdateRequest) {
        return mapper.mapModelToResponseDto(
                authorRepository.update(
                        mapper.mapRequestDtoToModel(authorUpdateRequest)
                ));
    }

    @Override
    @ValidateInput
    public boolean deleteById(Long id) {
        newsRepository.readAll().removeIf(news -> Objects.equals(news.getAuthorId(), id));
        return authorRepository.deleteById(id);
    }
}
