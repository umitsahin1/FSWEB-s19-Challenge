package com.twitterClone.twitterClone.service;


import com.twitterClone.twitterClone.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getById(Long id);
    User save(User user);
    User replaceOrCreate(Long id,User user);
    void deleteById(Long id);
    User findByEmail(String email);
}
