package com.mjc.school.service.validator;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.exceptions.ValidatorException;

public class AuthorDtoValidator {
    public void validateAuthorDTO(AuthorRequestDto dto) {
        checkName(dto.getName());
    }

    private void checkName(String name) {
        if (name == null || name.length() < 3 || name.length() > 15) {
            throw new ValidatorException("Author name can not be less than 3 and more than 15 symbols. " +
                    "Author name is " +
                    name);
        }
    }
}
