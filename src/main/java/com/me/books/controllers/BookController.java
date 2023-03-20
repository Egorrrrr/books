package com.me.books.controllers;

import com.me.books.entities.Book;
import com.me.books.entities.Comment;
import com.me.books.entities.User;
import com.me.books.services.AuthService;
import com.me.books.services.BookService;
import com.me.books.services.CommentService;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Set;

@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    AuthService authService;
    @Autowired
    CommentService commentService;

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable("id") Book book, Model model){
        model.addAttribute("book", bookService.getBookById(book.getId()));
        model.addAttribute("comments", commentService.getCommentsByBook(book));
        User user = authService.getUserFromAuth();
        if(user != null){
            model.addAttribute("logged_in", true);
            model.addAttribute("user", user);
        }
        return "book";
    }

    @GetMapping("/read/{id}/{chapter}")
    public String readBook(@PathVariable("id") long id, @PathVariable("chapter") int chapter, Model model) throws ParserConfigurationException, IOException, SAXException {
        Book book = bookService.getBookById(id);
        model.addAttribute("chapter", bookService.getChapter(book, chapter));
        model.addAttribute("book", book);
        if(chapter - 1 >= 0)
            model.addAttribute("p_link", String.format("/read/%s/%s", id, chapter-1));
        if(chapter + 1 < book.getChapterCount())
            model.addAttribute("n_link", String.format("/read/%s/%s", id, chapter+1 ));
        User user =authService.getUserFromAuth();
        if(user != null){
            model.addAttribute("logged_in", true);
            model.addAttribute("user", user);
        }
        return "read";
    }

    @PostMapping("/upload_book")
    public ResponseEntity<String> handleBookUpload(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        bookService.addBook(book, authService.getUserFromAuth());
        return new ResponseEntity<>("Success!", HttpStatus.CREATED);
    }

    @PostMapping("/post_comment")
    public ResponseEntity<String> postComment(Comment comment){
        User user = authService.getUserFromAuth();
        if(user == null){
            return new ResponseEntity<>("Fail", HttpStatus.FORBIDDEN);
        }
        commentService.postComment(comment, user);
        return new ResponseEntity<>("Success!", HttpStatus.CREATED);


    }
}
