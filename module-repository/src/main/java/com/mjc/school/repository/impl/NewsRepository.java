package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.ExtendedRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.mjc.school.repository.utils.JPAUtils;

@Repository
public class NewsRepository implements BaseRepository<News, Long>, ExtendedRepository {

    private final JPAUtils jpaUtils;

    public NewsRepository(JPAUtils jpaUtils) {
        this.jpaUtils = jpaUtils;
    }

    @Override
    public List<News> readAll() {
        AtomicReference<List<News>> resultList = new AtomicReference<>();
        jpaUtils.doInSessionWithTransaction(session ->
                resultList.set(
                        session.createQuery("select n from News n", News.class).getResultList()
                ));
        return resultList.get();
    }

    @Override
    public Optional<News> readById(Long id) {
        AtomicReference<Optional<News>> result = new AtomicReference<>();
        jpaUtils.doInSessionWithTransaction(session ->
                result.set(
                        Optional.ofNullable(session.createQuery("select a from News a where a.id = :id", News.class)
                                .setParameter("id", id)
                                .getSingleResult())
                ));
        return result.get();
    }

    @Override
    public News create(News newNews) {
        jpaUtils.doInSessionWithTransaction(session -> session.persist(newNews));
        return newNews;
    }

    //TODO update news tags (optional)
    @Override
    public News update(News news) {
        jpaUtils.doInSessionWithTransaction(session ->
                session.createQuery("update News n set " +
                                "n.title = :newTitle, " +
                                "n.content = :newContent, " +
                                "n.author = :newAuthorId, " +
                                "n.lastUpdateDate = CURRENT_TIMESTAMP where n.id = :id")
                        .setParameter("newTitle", news.getTitle())
                        .setParameter("newContent", news.getContent())
                        .setParameter("newAuthorId", news.getAuthor())
                        .setParameter("id", news.getId())
                        .executeUpdate());
        return readById(news.getId()).get();
    }

    @Override
    public boolean deleteById(Long id) {
        AtomicInteger numDeleted = new AtomicInteger();
        jpaUtils.doInSessionWithTransaction(session ->
                numDeleted.set(session.createQuery("delete from News where id = :id")
                        .setParameter("id", id)
                        .executeUpdate())
        );
        return numDeleted.get() == 1;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }

    @Override
    public List<News> readNewsByParams(List<Long> tagsIds,
                                       String tagName,
                                       String authorName,
                                       String title,
                                       String content) {
        AtomicReference<List<News>> resultNewsList = new AtomicReference<>();
        jpaUtils.doInSessionWithTransaction(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<News> query = criteriaBuilder.createQuery(News.class);
            Root<News> news = query.from(News.class);
            CriteriaQuery<News> selectQuery = query.select(news);
            if (!ObjectUtils.isEmpty(title)) {
                selectQuery.where(
                        criteriaBuilder.like(news.get("title"), "%" + title + "%"));
            }
            if (!ObjectUtils.isEmpty(content)) {
                selectQuery.where(
                        criteriaBuilder.like(news.get("content"), "%" + content + "%"));
            }
            if (!ObjectUtils.isEmpty(authorName)) {
                Join<News, Author> authorJoin = news.join("author", JoinType.INNER);
                selectQuery.where(
                        criteriaBuilder.like(authorJoin.get("name"), "%" + authorName + "%"));
            }
            if (!ObjectUtils.isEmpty(tagName)) {
                Join<News, Tag> tagJoin = news.join("newsTags", JoinType.INNER);
                selectQuery.where(
                        criteriaBuilder.like(tagJoin.get("name"), "%" + tagName + "%"));
            }
            if (!ObjectUtils.isEmpty(tagsIds)) {
                Join<News, Tag> tagJoin = news.join("newsTags", JoinType.INNER);
                selectQuery.where(tagJoin.get("id").in(tagsIds)).distinct(true);
            }
            resultNewsList.set(session.createQuery(selectQuery).getResultList());
        });
        return resultNewsList.get();
    }
}
