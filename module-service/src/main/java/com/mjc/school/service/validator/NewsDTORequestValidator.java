package com.mjc.school.service.validator;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.impl.AuthorRepositoryImpl;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;

import static com.mjc.school.service.exceptions.ExceptionsCodes.AUTHOR_ID_DOES_NOT_EXIST;
import static com.mjc.school.service.exceptions.ExceptionsCodes.VALIDATE_NEGATIVE_OR_NULL_NUMBER;
import static com.mjc.school.service.exceptions.ExceptionsCodes.VALIDATE_STRING_LENGTH;

public class NewsDTORequestValidator {

    private final BaseRepository<Author, Long> authorRepository = new AuthorRepositoryImpl();

    public void validateNewsDTORequest(NewsRequestDto dto) {
        checkNewsTitle(dto.getTitle());
        checkNewsContent(dto.getContent());

    }

    private void checkNewsTitle(String title) {
        if (title == null || title.length() < 5 || title.length() > 30) {
            throw new ValidatorException(
                    String.format(VALIDATE_STRING_LENGTH.getMessage(),
                            "News title", 5, 30, "News title", title)
            );
        }
    }

    private void checkNewsContent(String content) {
        if (content == null || content.length() < 5 || content.length() > 255) {
            throw new ValidatorException(
                    String.format(VALIDATE_STRING_LENGTH.getMessage(),
                            "News content", 5, 255, "News content", content)
            );
        }
    }

    public void validateNewsId(Long id) {
        if (id == null || id < 1) {
            throw new ValidatorException(
                    String.format(VALIDATE_NEGATIVE_OR_NULL_NUMBER.getMessage(),
                            "News id", "News id", id));
        }
    }

    public void validateAuthorId(Long id) {
        if (id == null)
            return;
        if (id < 1) {
            throw new ValidatorException(
                    String.format(VALIDATE_NEGATIVE_OR_NULL_NUMBER.getMessage(), "Author id", "Author id", id));
        }
        if (!authorRepository.existById(id)) {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }
}
