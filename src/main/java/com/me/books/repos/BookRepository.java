package com.me.books.repos;

import com.me.books.entities.Book;
import com.me.books.entities.Genre;
import com.me.books.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    @Override
    Optional<Book> findById(Long aLong);

    @Override
    Page<Book> findAll(Pageable pageable);

    List<Book> findByGenres(Genre id);

}