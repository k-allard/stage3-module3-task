package com.mjc.school.service.congiguration;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.validator.NewsDTORequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class MyAspect {

    private final NewsDTORequestValidator newsValidator = new NewsDTORequestValidator();

    @Before(value = "@annotation(com.mjc.school.service.congiguration.annotations.ValidateInput)")
        public void validateInput(JoinPoint joinPoint) throws Throwable {
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
            newsValidator.validateNewsId(id);
        } else {
            log.warn("@ValidateInput annotation does not support validation for parameter of "
                    + requestObject[0].getClass());
        }
        log.debug("Completed executing validateInput advice");
    }
}