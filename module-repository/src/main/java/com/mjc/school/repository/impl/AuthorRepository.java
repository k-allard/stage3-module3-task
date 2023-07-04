package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<Author, Long> {


    @Override
    public List<Author> readAll() {
        return null;

    }

    @Override
    public Optional<Author> readById(Long id) {
        return null;

    }

    @Override
    public Author create(Author newAuthor) {
        return null;

    }

    @Override
    public Author update(Author entity) {
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
