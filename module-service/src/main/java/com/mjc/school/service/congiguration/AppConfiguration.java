package com.mjc.school.service.congiguration;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.validator.AuthorRequestDtoValidator;
import com.mjc.school.service.validator.NewsRequestDtoValidator;
import com.mjc.school.service.validator.TagDtoValidator;
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
    BaseRepository<Tag, Long> tagRepository;

    public AppConfiguration(@Qualifier("authorRepository") BaseRepository<Author, Long> authorRepository,
                            @Qualifier("newsRepository") BaseRepository<News, Long> newsRepository,
                            @Qualifier("tagRepository") BaseRepository<Tag, Long> tagRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
    }

    @Bean
    public ValidationAspect myAspect() {
        return new ValidationAspect(newsDTORequestValidator(), authorRequestDtoValidator(), tagDtoValidator());
    }

    @Bean
    public AuthorRequestDtoValidator authorRequestDtoValidator() {
        return new AuthorRequestDtoValidator(authorRepository);
    }

    @Bean
    public NewsRequestDtoValidator newsDTORequestValidator() {
        return new NewsRequestDtoValidator(authorRepository, newsRepository);
    }

    @Bean
    public TagDtoValidator tagDtoValidator() {
        return new TagDtoValidator(tagRepository);
    }
}