package com.me.books.controllers;

import com.me.books.entities.Book;
import com.me.books.entities.Genre;
import com.me.books.entities.User;
import com.me.books.repos.BookRepository;
import com.me.books.repos.GenreRepository;
import com.me.books.services.AuthService;
import com.me.books.services.BookService;
import com.me.books.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;


@Controller
public class DefaultController {
    @Autowired
    GenreService genreService;
    @Autowired
    AuthService authService;
    @Autowired
    BookService bookService;

    @GetMapping(value = {"/", "/main"})
    public String mainPage(@RequestParam(required=false) Genre genre, Model model) {
        User user = authService.getUserFromAuth();
        model.addAttribute("books",
                genre == null ? bookService.getBooks(0) : bookService.getBooksByGenre(genre));
        model.addAttribute("genres", genreService.getGenres());

        model.addAttribute("selected_genre", genre != null ?  genre.getId() : "");
        if(user == null){
            return "main";
        }
        model.addAttribute("logged_in", true);
        model.addAttribute("user", user);
        return "main";
    }
}