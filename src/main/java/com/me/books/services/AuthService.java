package com.me.books.services;

import com.me.books.entities.User;
import com.me.books.services.details.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AppUserDetailService appUserDetailService;

    public boolean checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            return principal instanceof UserDetails;
        }
        return false;
    }
    public User getUserFromAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                AppUserDetails userDetails = (AppUserDetails) principal;
                return appUserDetailService.loadUserById(userDetails.getId());

            }
        }
        return null;
    }
}
