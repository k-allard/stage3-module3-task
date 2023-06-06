package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataSource {
    @Getter
    private final List<NewsModel> newsModelList;

    @Getter
    private final List<Author> authorList;

    private final AtomicInteger idSequence = new AtomicInteger(0);

    public DataSource() {
        DataInitializer dataInitializer = new DataInitializer(idSequence);
        authorList = dataInitializer.initializeAuthorList();
        newsModelList = dataInitializer.initializeNewsList(authorList);
    }

    public int getNextNewsId() {
        return idSequence.incrementAndGet();
    }
}
