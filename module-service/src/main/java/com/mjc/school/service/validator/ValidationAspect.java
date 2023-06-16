package com.mjc.school.service.validator;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.NewsRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class ValidationAspect {

    private final NewsRequestDtoValidator newsValidator;

    private final AuthorRequestDtoValidator authorValidator;

    public ValidationAspect(NewsRequestDtoValidator newsValidator,
                            AuthorRequestDtoValidator authorValidator) {
        this.newsValidator = newsValidator;
        this.authorValidator = authorValidator;
    }

    @Before(value = "@annotation(com.mjc.school.service.validator.annotations.ValidateInput)")
    public void validateInput(JoinPoint joinPoint) {
        log.debug("Entered validateInput advice for method: " + joinPoint.getSignature());
        Object[] requestObject = joinPoint.getArgs();

        if (requestObject.length == 0) {
            log.error(
                    "@ValidateInput annotation should be placed on method with at least 1 parameter.\n" +
                            "No validation will be performed");
        } else if (requestObject[0] instanceof NewsRequestDto news) {
            log.debug("Started executing validateInput advice for NewsRequestDto parameter");
            newsValidator.validateNewsDTORequest(news);
            newsValidator.validateAuthorId(news.getAuthorId());
            if (joinPoint.getSignature().getName().equals("update")) {
                newsValidator.validateNewsId(news.getId());
            }
        } else if (requestObject[0] instanceof Long id) {
            log.debug("Started executing validateInput advice for Long id parameter");
            String className = joinPoint.getSourceLocation().getWithinType().getTypeName();
            if (className.contains("AuthorService"))
                authorValidator.validateAuthorId(id);
            else if (className.contains("NewsService"))
                newsValidator.validateNewsId(id);
            else {
                log.error("@ValidateInput does not know how to validate IDs in "
                        + className + "yet\n" + "No validation will be performed");
            }
        } else if (requestObject[0] instanceof AuthorRequestDto author) {
            log.debug("Started executing validateInput advice for AuthorRequestDto parameter");
            authorValidator.validateAuthorDTO(author);
            if (joinPoint.getSignature().getName().equals("update")) {
                authorValidator.validateAuthorId(author.getId());
            }
        } else {
            log.warn("@ValidateInput annotation does not support validation for parameter of "
                    + requestObject[0].getClass());
        }
        log.debug("Completed executing validateInput advice");
    }
}