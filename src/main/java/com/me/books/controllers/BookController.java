package com.me.books.controllers;

import com.me.books.entities.Book;
import com.me.books.services.AuthService;
import com.me.books.services.BookService;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

    @GetMapping("/book/{id}")
    public String viewBook(@PathVariable("id") long id, Model model){
        model.addAttribute("book", bookService.getBookById(id));
        return "book";
    }

    @PostMapping("/upload_book")
    public String handleFileUpload(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {
         bookService.addBook(book, authService.getUserFromAuth());
        return "";
    }
}
