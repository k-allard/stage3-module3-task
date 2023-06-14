package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.AuthorDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mjc.school.service.exceptions.ExceptionsCodes.AUTHOR_ID_DOES_NOT_EXIST;

public class AuthorService implements BaseService<AuthorRequestDto, AuthorResponseDto, Long> {

    //TODO think of declarative validation outside of business logic, e.g. via custom annotations.
    //  use Aspects.
    private final AuthorDtoValidator authorValidator = new AuthorDtoValidator();
    private final AuthorMapper mapper = new AuthorMapper();

    private final BaseRepository<Author, Long> authorRepository;

    public AuthorService(@Qualifier("authorRepository") BaseRepository<Author, Long> repository) {
        this.authorRepository = repository;
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
    public AuthorResponseDto readById(Long id) {
        authorValidator.validateAuthorId(id);
        if (!authorRepository.existById(id)) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
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
        Long id = authorUpdateRequest.getId();
        authorValidator.validateAuthorId(id);
        if (!authorRepository.existById(id)) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
        authorValidator.validateAuthorDTO(authorUpdateRequest);

        return mapper.mapModelToResponseDto(
                authorRepository.update(
                        mapper.mapRequestDtoToModel(authorUpdateRequest)
                ));
    }

    //TODO remove her/his news
    //  use custom annotation (e.g. @OnDelete) with its handler (could be implemented via Aspects).
    @Override
    public boolean deleteById(Long id) {
        authorValidator.validateAuthorId(id);
        if (!authorRepository.existById(id)) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
        return authorRepository.deleteById(id);
    }
}
