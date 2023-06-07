package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.modelmapper.ModelMapper;

public class AuthorMapper {
    private final ModelMapper mapper = new ModelMapper();

    public AuthorResponseDto mapModelToResponseDto(Author author) {
        return mapper.map(author, AuthorResponseDto.class);
    }

    public Author mapResponseDtoToModel(AuthorResponseDto author) {
        return mapper.map(author, Author.class);
    }

    public Author mapRequestDtoToModel(AuthorRequestDto author) {
        return mapper.map(author, Author.class);
    }
}
