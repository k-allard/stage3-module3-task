package com.mjc.school.repository;

import com.mjc.school.repository.model.News;

import java.util.List;

public interface ExtendedRepository {
    List<News> readNewsByParams(List<Long> tagsIds, String tagName, String authorName, String title, String content);
}