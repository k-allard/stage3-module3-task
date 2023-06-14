package com.mjc.school.service.congiguration;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.impl.NewsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    BaseRepository<Author, Long> authorRepository;
    BaseRepository<NewsModel, Long> newsRepository;

    public AppConfig(@Qualifier("authorRepository") BaseRepository<Author, Long> authorRepository,
                     @Qualifier("newsRepository") BaseRepository<NewsModel, Long> newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
    }

    @Bean
    public AuthorService authorService() {
        return new AuthorService(authorRepository);
    }

    @Bean
    public NewsService newsService() {
        return new NewsService(newsRepository);
    }

    @Bean
    public MyAspect myAspect() {
        return new MyAspect();
    }
}