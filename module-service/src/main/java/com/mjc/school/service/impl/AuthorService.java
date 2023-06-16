package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.annotations.ValidateInput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorService implements BaseService<AuthorRequestDto, AuthorResponseDto, Long> {

    private final AuthorMapper mapper = new AuthorMapper();

    private final BaseRepository<Author, Long> authorRepository;

    public AuthorService(BaseRepository<Author, Long> authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorResponseDto> readAll() {
        List<AuthorResponseDto> authorResponseDtoList = new ArrayList<>();
        for (Author author : authorRepository.readAll()) {
            authorResponseDtoList.add(mapper.mapModelToResponseDto(author));
        }
        return authorResponseDtoList;
    }

    @Override
    @ValidateInput
    public AuthorResponseDto readById(Long id) {
        Author author = authorRepository.readById(id).get();
        return mapper.mapModelToResponseDto(author);
    }

    @Override
    @ValidateInput
    public AuthorResponseDto create(AuthorRequestDto authorRequestDto) {
        AuthorResponseDto newAuthor =
                new AuthorResponseDto(
                        null,
                        authorRequestDto.getName(),
                        LocalDateTime.now(),
                        LocalDateTime.now());
        return mapper.mapModelToResponseDto(authorRepository.create(
                mapper.mapResponseDtoToModel(newAuthor)
        ));
    }

    @Override
    @ValidateInput
    public AuthorResponseDto update(AuthorRequestDto authorUpdateRequest) {
        return mapper.mapModelToResponseDto(
                authorRepository.update(
                        mapper.mapRequestDtoToModel(authorUpdateRequest)
                ));
    }

    @Override
    @ValidateInput
    public boolean deleteById(Long id) {
        return authorRepository.deleteById(id);
    }
}
