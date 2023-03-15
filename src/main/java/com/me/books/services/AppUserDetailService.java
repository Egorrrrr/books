package com.me.books.services;

import com.me.books.entities.User;
import com.me.books.repos.UserRepository;
import com.me.books.services.details.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageService messageService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return new AppUserDetails(user);
    }
    public User loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).get();
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return user;
    }

    public String makeNewUser(String username, String name, String email, String password) {
        if (userRepository.findByUsername(username) != null) {
            return messageService.getMessage("name_used");
        }
        if(userRepository.findByEmail(email) != null){
            return messageService.getMessage("email_registered");
        }
        User userToReg = new User();
        userToReg.setUsername(username);
        userToReg.setEmail(email);
        userToReg.setName(name);
        userToReg.setPassword(new BCryptPasswordEncoder().encode(password));
        userToReg.setType("user");
        userRepository.save(userToReg);
        return "200";

    }
}
