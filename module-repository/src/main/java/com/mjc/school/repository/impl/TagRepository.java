package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.utils.HibernateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mjc.school.repository.utils.HibernateUtils.doInSessionWithTransaction;

@Repository
public class TagRepository implements BaseRepository<Tag, Long> {

    @Override
    public List<Tag> readAll() {
        try (Session session = HibernateUtils.getSf().openSession()) {
            return session.createQuery("select n from Tag n", Tag.class).list();
        }
    }

    @Override
    public Optional<Tag> readById(Long id) {
        try (Session session = HibernateUtils.getSf().openSession()) {
            String jpqlSelectById = "select a from Tag a where a.id = :id";
            return session.createQuery(jpqlSelectById, Tag.class).setParameter("id", id).uniqueResultOptional();
        }
    }

    @Override
    public Tag create(Tag newNews) {
        doInSessionWithTransaction(session -> session.persist(newNews));
        return newNews;
    }

    @Override
    public Tag update(Tag news) {
        doInSessionWithTransaction(session ->
                session.createQuery("update Tag n set " +
                                "n.name = :newName where n.id = :id")
                        .setParameter("newName", news.getName())
                        .setParameter("id", news.getId())
                        .executeUpdate());
        return readById(news.getId()).get();
    }

    @Override
    public boolean deleteById(Long id) {
        AtomicInteger numDeleted = new AtomicInteger();
        doInSessionWithTransaction(session ->
                numDeleted.set(session.createQuery("delete from Tag where id = :id")
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
