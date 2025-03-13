package com.twitterClone.twitterClone.service;

import com.twitterClone.twitterClone.entity.Tweet;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.TweetNotFoundException;
import com.twitterClone.twitterClone.repository.TweetRepository;
import com.twitterClone.twitterClone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    @Autowired
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Override
    public List<Tweet> getAll() {
        return tweetRepository.findAll();
    }

    @Override
    public Tweet getById(Long id) {
        return tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException(id + "'id'li kayıt bulunamadı"));
    }

    @Override
    public Tweet save(Tweet tweet, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));


        tweet.setUser(user);
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet replaceOrCreate(Long id, Tweet tweet) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if(tweetOptional.isPresent()) {
            tweet.setId(id);
            tweetRepository.save(tweet);
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public void deleteById(Long id) {
      tweetRepository.deleteById(id);
    }

    @Override
    public List<Tweet> findByUserId(Long userId) {
        return tweetRepository.findByUser_Id(userId);
    }
}
