package com.me.books.repos;

import com.me.books.entities.Book;
import com.me.books.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Override
    Optional<Genre> findById(Long aLong);

    @Override
    Page<Genre> findAll(Pageable pageable);

    Set<Genre> findByBooks_Id(Long id);
}
