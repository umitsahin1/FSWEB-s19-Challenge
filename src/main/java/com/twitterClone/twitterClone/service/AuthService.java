package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.User;

public interface AuthService {

    User register(String username,String email, String password);
    User login(String email, String password);
}
