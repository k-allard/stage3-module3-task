//package com.mjc.school.repository.utils;
//
//import com.mjc.school.repository.model.Author;
//import com.mjc.school.repository.model.NewsModel;
//import lombok.SneakyThrows;
//import org.hibernate.cfg.Configuration;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class DataInitializer {
//    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
//
//    private static final String FILENAME_AUTHORS = "/authors";
//
//    private static final String FILENAME_NEWS_TITLES = "/news";
//
//    private static final String FILENAME_NEWS_CONTENT = "/content";
//
//    private final AtomicInteger idSequence = new AtomicInteger(0);
//
//    @SneakyThrows
//    public List<Author> initializeAuthorList() {
//        doDBMigration();
//        List<Author> authorList = new ArrayList<>();
//        long authorIdSequence = 0;
//        List<String> lines = readLinesFromFile(FILENAME_AUTHORS);
//
//        for (String line : lines) {
//            authorList.add(
//                    new Author(
//                            ++authorIdSequence,
//                            line,
//                            LocalDateTime.now(),
//                            LocalDateTime.now()
//                    ));
//        }
//        return authorList;
//    }
//
//    private void doDBMigration() {
//        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
//        var dbUrl = configuration.getProperty("hibernate.connection.url");
//        var dbUserName = configuration.getProperty("hibernate.connection.username");
//        var dbPassword = configuration.getProperty("hibernate.connection.password");
//        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
//    }
//
//    @SneakyThrows
//    public List<NewsModel> initializeNewsList(List<Author> authorList) {
//        List<NewsModel> newsModelList = new ArrayList<>();
//
//        List<String> titlesLines = readLinesFromFile(FILENAME_NEWS_TITLES);
//        List<String> contentLines = readLinesFromFile(FILENAME_NEWS_CONTENT);
//        Random random = new Random();
//
//        for (String titleLine : titlesLines) {
//            int id = idSequence.incrementAndGet();
//            newsModelList.add(
//                    new NewsModel(
//                            (long) id,
//                            titleLine,
//                            contentLines.get(id - 1),
//                            LocalDateTime.now(),
//                            LocalDateTime.now(),
//                            authorList.get(random.nextInt(authorList.size())).getId()
//                    ));
//        }
//        return newsModelList;
//    }
//
//    private List<String> readLinesFromFile(String filename)
//            throws IOException {
//        InputStream inputStream = DataInitializer.class.getResourceAsStream(filename);
//        if (inputStream == null) {
//            throw new RuntimeException(
//                    "File %s not found in resources".formatted(filename));
//        }
//        List<String> result = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                result.add(line);
//            }
//        }
//        return result;
//    }
//}
