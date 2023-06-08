package com.mjc.school.service.validator;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.exceptions.ValidatorException;

import static com.mjc.school.service.exceptions.ExceptionsCodes.VALIDATE_NEGATIVE_OR_NULL_NUMBER;
import static com.mjc.school.service.exceptions.ExceptionsCodes.VALIDATE_STRING_LENGTH;

public class AuthorDtoValidator {

    public void validateAuthorDTO(AuthorRequestDto dto) {
        checkName(dto.getName());
    }

    private void checkName(String name) {
        if (name == null || name.length() < 3 || name.length() > 15) {
            throw new ValidatorException(String.format(VALIDATE_STRING_LENGTH.getMessage(),
                    "Author name", 3, 15, "Author name", name));
        }
    }

    public void validateAuthorId(Long id) {
        if (id == null || id < 1) {
            throw new ValidatorException(
                    String.format(VALIDATE_NEGATIVE_OR_NULL_NUMBER.getMessage(), "Author id", "Author id", id));
        }
    }
}
