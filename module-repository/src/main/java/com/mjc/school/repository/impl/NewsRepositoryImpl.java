package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.DataSource;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NewsRepositoryImpl implements BaseRepository<NewsModel, Long> {

    private final DataSource dataSource = new DataSource();

    @Override
    public List<NewsModel> readAll() {
        return dataSource.getNewsModelList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return dataSource.getNewsModelList().stream()
                .filter(news -> id.equals(news.getId()))
                .findFirst();
    }

    @Override
    public NewsModel create(NewsModel newNews) {
        List<NewsModel> newsList = dataSource.getNewsModelList();
        newsList.sort(Comparator.comparing(NewsModel::getId));
        if (!newsList.isEmpty()) {
            newNews.setId(newsList.get(newsList.size() - 1).getId() + 1);
        } else {
            newNews.setId(1L);
        }
        newsList.add(newNews);
        return newNews;
    }

    @Override
    public NewsModel update(NewsModel newsModel) {
        NewsModel newsModelFromRepo = readById(newsModel.getId()).get();
        newsModelFromRepo.setTitle(newsModel.getTitle());
        newsModelFromRepo.setContent(newsModel.getContent());
        newsModelFromRepo.setAuthorId(newsModel.getAuthorId());
        newsModelFromRepo.setLastUpdateDate(LocalDateTime.now());
        return newsModelFromRepo;
    }

    @Override
    public boolean deleteById(Long id) {
        return dataSource.getNewsModelList().remove(new NewsModel(id));
    }

    @Override
    public boolean existById(Long id) {
        int indexOfNews = dataSource.getNewsModelList().indexOf(new NewsModel(id));
        return indexOfNews != -1;
    }
}
