package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.DataSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private final DataSource dataSource = new DataSource();

    @Override
    public List<NewsModel> readAll() {
        return null;
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return null;
    }

    @Override
    public NewsModel create(NewsModel newNews) {
        return null;
    }

    @Override
    public NewsModel update(NewsModel newsModel) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }
}
