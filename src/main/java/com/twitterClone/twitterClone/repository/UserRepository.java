package com.twitterClone.twitterClone.repository;

import com.twitterClone.twitterClone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
