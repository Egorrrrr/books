package com.me.books.controllers;


import com.me.books.services.AppUserDetailService;
import com.me.books.services.AuthService;
import com.me.books.services.MessageService;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    AppUserDetailService appUserDetailService;
    @Autowired
    MessageService messageService;
    @Autowired
    AuthService authService;
    @GetMapping("/login")
    public String login(@RequestParam(required=false) boolean error, Model model) {
        if(authService.checkAuthentication()){
            return "redirect:";
        }
        if(error){
            model.addAttribute("message", messageService.getMessage("login_error"));
        }
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        if(authService.checkAuthentication()){

            return "redirect:";
        }
        return "registration";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody MultiValueMap<String, String> formData, Model model){
        String status = appUserDetailService.makeNewUser(
                formData.getFirst("username"),
                formData.getFirst("name"),
                formData.getFirst("email"),
                formData.getFirst("password")
                );

        if(status != "200"){
            model.addAttribute("message", status);
            return "registration";
        }
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(formData.getFirst("username"), formData.getFirst("password"));
        Authentication authResult = authenticationProvider.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return "redirect:main";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "login";
    }
}
