package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.Tag;
import lombok.Getter;

import java.util.List;

import static com.mjc.school.repository.utils.HibernateUtils.buildSessionFactory;

public class DataSource {
//    @Getter
//    private final List<NewsModel> newsModelList;
//
//    @Getter
//    private final List<Author> authorList;

    public DataSource() {
//        DataInitializer dataInitializer = new DataInitializer();
//        authorList = dataInitializer.initializeAuthorList();
//        newsModelList = dataInitializer.initializeNewsList(authorList);
        buildSessionFactory(Author.class, NewsModel.class, Tag.class);
    }
}
