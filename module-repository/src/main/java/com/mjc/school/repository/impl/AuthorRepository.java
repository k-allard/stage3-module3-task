package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.utils.DataSource;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<Author, Long> {

    private final DataSource dataSource = new DataSource();

    @Override
    public List<Author> readAll() {
        return dataSource.getAuthorList();
    }

    @Override
    public Optional<Author> readById(Long id) {
        return dataSource.getAuthorList().stream()
                .filter(news -> id.equals(news.getId()))
                .findFirst();
    }

    @Override
    public Author create(Author newAuthor) {
        List<Author> authorList = dataSource.getAuthorList();
        authorList.sort(Comparator.comparing(Author::getId));
        if (!authorList.isEmpty()) {
            newAuthor.setId(authorList.get(authorList.size() - 1).getId() + 1);
        } else {
            newAuthor.setId(1L);
        }
        authorList.add(newAuthor);
        return newAuthor;
    }

    @Override
    public Author update(Author author) {
        Author authorFromRepo = readById(author.getId()).get();
        authorFromRepo.setName(author.getName());
        return authorFromRepo;
    }

    @Override
    public boolean deleteById(Long id) {
        return dataSource.getAuthorList().remove(new Author(id));
    }

    @Override
    public boolean existById(Long id) {
        int indexOfAuthor = dataSource.getAuthorList().indexOf(new Author(id));
        return indexOfAuthor != -1;
    }
}
