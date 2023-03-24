package com.me.books.controllers;

import com.me.books.entities.Genre;
import com.me.books.entities.User;
import com.me.books.services.AppUserDetailService;
import com.me.books.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    AppUserDetailService appUserDetailService;
    @Autowired
    AuthService authService;


    @GetMapping("/settings")
    public String settings(MultipartFile file, String email, String name) {
        return "settings";
    }

    @PostMapping("/changeSettings")
    public String changeSettings(MultipartFile file, String email, String name) throws IOException {
        appUserDetailService.updateUserData(authService.getUserFromAuth(), email, name, file);
        return "settings";
    }
}
