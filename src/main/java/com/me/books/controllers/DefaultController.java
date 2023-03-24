package com.me.books.controllers;

import com.me.books.entities.Genre;
import com.me.books.entities.User;
import com.me.books.services.AuthService;
import com.me.books.services.BookService;
import com.me.books.services.FileService;
import com.me.books.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Controller
public class DefaultController {
    @Autowired
    GenreService genreService;
    @Autowired
    AuthService authService;
    @Autowired
    BookService bookService;
    @Autowired
    FileService fileService;

    @GetMapping(value = {"/", "/main"})
    public String mainPage(@RequestParam(required=false) Genre genre, @RequestParam(required=false) String search, Model model) {
        User user = authService.getUserFromAuth();
        if(search == null){
            model.addAttribute("books",
                    genre == null ? bookService.getBooks(0) : bookService.getBooksByGenre(genre));
        }
        else{
            model.addAttribute("books",bookService.searchBooksByName(search));
        }

        model.addAttribute("genres", genreService.getGenres());

        model.addAttribute("selected_genre", genre != null ?  genre.getId() : "");


        return "main";
    }

    @GetMapping("/pics/{filename:.+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getPfp(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(new ByteArrayInputStream(fileService.getPfp(filename))));
    }
}