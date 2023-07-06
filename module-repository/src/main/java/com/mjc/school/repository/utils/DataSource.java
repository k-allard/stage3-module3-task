package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.Tag;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static com.mjc.school.repository.utils.HibernateUtils.buildSessionFactory;

@Slf4j
public class DataSource {
    //    @Getter
//    private final List<NewsModel> newsModelList;
//
//    @Getter
//    private final List<Author> authorList;
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public DataSource() {
//        DataInitializer dataInitializer = new DataInitializer();
//        authorList = dataInitializer.initializeAuthorList();
//        newsModelList = dataInitializer.initializeNewsList(authorList);
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        buildSessionFactory(Author.class, NewsModel.class, Tag.class);
    }
}
