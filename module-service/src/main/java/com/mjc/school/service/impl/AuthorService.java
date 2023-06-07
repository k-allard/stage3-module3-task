package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.AuthorDtoValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorService implements BaseService<AuthorRequestDto, AuthorResponseDto, Long> {

    private final AuthorDtoValidator authorValidator = new AuthorDtoValidator();

    private final BaseRepository<Author, Long> authorRepository = new AuthorRepositoryImpl();

    private final AuthorMapper mapper = new AuthorMapper();

    @Override
    public List<AuthorResponseDto> readAll() {
        List<AuthorResponseDto> authorResponseDtoList = new ArrayList<>();
        for (Author author : authorRepository.readAll()) {
            authorResponseDtoList.add(mapper.mapModelToResponseDto(author));
        }
        return authorResponseDtoList;
    }

    @Override
    public AuthorResponseDto readById(Long id) {
        Author author = authorRepository.readById(id).get();
        return mapper.mapModelToResponseDto(author);
    }

    @Override
    public AuthorResponseDto create(AuthorRequestDto authorRequestDto) {
        authorValidator.validateAuthorDTO(authorRequestDto);
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
    public AuthorResponseDto update(AuthorRequestDto authorUpdateRequest) {
        authorValidator.validateAuthorDTO(authorUpdateRequest);
        return mapper.mapModelToResponseDto(
                authorRepository.update(
                        mapper.mapRequestDtoToModel(authorUpdateRequest)
                ));
    }

    @Override
    public boolean deleteById(Long id) {
        return authorRepository.deleteById(id);
    }
}
