package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.mjc.school.repository.utils.JPAUtils.doInSessionWithTransaction;

@Repository
public class TagRepository implements BaseRepository<Tag, Long> {

    @Override
    public List<Tag> readAll() {
        AtomicReference<List<Tag>> resultList = new AtomicReference<>();
        doInSessionWithTransaction(session ->
                resultList.set(
                        session.createQuery("select n from Tag n", Tag.class).getResultList()
                ));
        return resultList.get();
    }

    @Override
    public Optional<Tag> readById(Long id) {
        AtomicReference<Optional<Tag>> result = new AtomicReference<>();
        doInSessionWithTransaction(session ->
                result.set(
                        Optional.ofNullable(session.createQuery("select a from Tag a where a.id = :id", Tag.class)
                                .setParameter("id", id)
                                .getSingleResult())
                ));
        return result.get();
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
