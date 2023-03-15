package com.me.books.repos;

import com.me.books.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Override
    boolean existsById(Long aLong);

    @Override
    Optional<User> findById(Long aLong);
}