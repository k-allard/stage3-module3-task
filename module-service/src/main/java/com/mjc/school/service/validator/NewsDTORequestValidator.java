package com.mjc.school.service.validator;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.exceptions.ValidatorException;

public class NewsDTORequestValidator {
    public void validateNewsDTORequest(NewsRequestDto dto) {
        checkNewsTitle(dto.getTitle());
        checkNewsContent(dto.getContent());

    }

    private void checkNewsTitle(String title) {
        if (title == null || title.length() < 5 || title.length() > 30) {
            throw new ValidatorException("News title can not be less than 5 and more than 30 symbols. " +
                    "News title is " +
                    title);
        }
    }

    private void checkNewsContent(String content) {
        if (content == null || content.length() < 5 || content.length() > 255) {
            throw new ValidatorException("News content can not be less than 5 and more than 255 symbols. " +
                    "News content is " +
                    content);
        }
    }
}
