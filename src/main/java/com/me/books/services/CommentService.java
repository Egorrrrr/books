package com.me.books.services;

import com.me.books.entities.Book;
import com.me.books.entities.Comment;
import com.me.books.entities.User;
import com.me.books.repos.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public String postComment(Comment comment, User user){
        comment.setUser(user);
        commentRepository.save(comment);
        return "200";
    }

    public List<Comment> getCommentsByBook(Book book){
        return commentRepository.findByBook(book);
    }

}
