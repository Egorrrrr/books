package com.me.books.services;

import com.me.books.entities.Genre;
import com.me.books.repos.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<Genre> getGenres(){
        return genreRepository.findAll();
    }
}
