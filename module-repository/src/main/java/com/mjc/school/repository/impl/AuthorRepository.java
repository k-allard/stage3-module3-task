package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.utils.HibernateUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mjc.school.repository.utils.HibernateUtils.doInSessionWithTransaction;

@Repository
public class AuthorRepository implements BaseRepository<Author, Long> {

    @Override
    public List<Author> readAll() {
        try (Session session = HibernateUtils.getSf().openSession()) {
            return session.createQuery("select a from Author a", Author.class).list();
        }
    }

    @Override
    public Optional<Author> readById(Long id) {
        try (Session session = HibernateUtils.getSf().openSession()) {
            String jpqlSelectById = "select a from Author a where a.id = :id";
            return session.createQuery(jpqlSelectById, Author.class).setParameter("id", id).uniqueResultOptional();
        }
    }

    @Override
    public Author create(Author newAuthor) {
        doInSessionWithTransaction(session -> session.persist(newAuthor));
        return newAuthor;
    }

    @Override
    public Author update(Author author) {
        doInSessionWithTransaction(session ->
                session.createQuery("update Author a set " +
                                "a.name = :newName, " +
                                "a.lastUpdateDate = CURRENT_TIMESTAMP where a.id = :id")
                        .setParameter("newName", author.getName())
                        .setParameter("id", author.getId())
                        .executeUpdate());
        return readById(author.getId()).get();
    }

    @Override
    public boolean deleteById(Long id) {
        AtomicInteger numDeleted = new AtomicInteger();
        doInSessionWithTransaction(session ->
                numDeleted.set(session.createQuery("delete from Author where id = :id")
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
