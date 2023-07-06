package com.mjc.school.service.congiguration;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.validator.AuthorRequestDtoValidator;
import com.mjc.school.service.validator.NewsRequestDtoValidator;
import com.mjc.school.service.validator.ValidationAspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfiguration {

    BaseRepository<Author, Long> authorRepository;
    BaseRepository<News, Long> newsRepository;

    public AppConfiguration(@Qualifier("authorRepository") BaseRepository<Author, Long> authorRepository,
                            @Qualifier("newsRepository") BaseRepository<News, Long> newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
    }

    @Bean
    public ValidationAspect myAspect() {
        return new ValidationAspect(newsDTORequestValidator(), authorRequestDtoValidator());
    }

    @Bean
    public AuthorRequestDtoValidator authorRequestDtoValidator() {
        return new AuthorRequestDtoValidator(authorRepository);
    }

    @Bean
    public NewsRequestDtoValidator newsDTORequestValidator() {
        return new NewsRequestDtoValidator(authorRepository, newsRepository);
    }
}