package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.UserNotFoundException;
import com.twitterClone.twitterClone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id + "'id'li kayıt bulunamadı"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User replaceOrCreate(Long id, User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
       userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByUsername(email);
    }
}
