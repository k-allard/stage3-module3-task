package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.utils.DataSource;
import com.mjc.school.repository.utils.HibernateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mjc.school.repository.utils.HibernateUtils.doInSessionWithTransaction;

@Repository
public class NewsRepository implements BaseRepository<News, Long> {

    //TODO create dataSource some where else
    private final DataSource dataSource = new DataSource();

    @Override
    public List<News> readAll() {
        try (Session session = HibernateUtils.getSf().openSession()) {
            return session.createQuery("select n from News n", News.class).list();
        }
    }

    @Override
    public Optional<News> readById(Long id) {
        try (Session session = HibernateUtils.getSf().openSession()) {
            String jpqlSelectById = "select a from News a where a.id = :id";
            return session.createQuery(jpqlSelectById, News.class).setParameter("id", id).uniqueResultOptional();
        }
    }

    @Override
    public News create(News newNews) {
        doInSessionWithTransaction(session -> session.persist(newNews));
        return newNews;
    }

    //TODO update news tags (optional)
    @Override
    public News update(News news) {
        doInSessionWithTransaction(session ->
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
        doInSessionWithTransaction(session ->
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
}
