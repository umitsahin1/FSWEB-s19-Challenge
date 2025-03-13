package com.twitterClone.twitterClone.service;


import com.twitterClone.twitterClone.entity.Like;
import com.twitterClone.twitterClone.entity.Tweet;
import com.twitterClone.twitterClone.entity.User;
import com.twitterClone.twitterClone.exceptions.LikeNotFoundException;
import com.twitterClone.twitterClone.repository.LikeRepository;
import com.twitterClone.twitterClone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {


    private final LikeRepository likeRepository;
    private final TweetService tweetService;
    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, TweetService tweetService, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.tweetService = tweetService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Like> getAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like getById(Long id) {
        return likeRepository.findById(id).orElseThrow(() -> new LikeNotFoundException("Like not found"));
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Like replaceOrCreate(Long id, Like like) {
        Optional<Like> likeOptional = likeRepository.findById(id);
        if (likeOptional.isPresent()) {
            like.setId(id);
            return likeRepository.save(like);
        }
        return likeRepository.save(like);
    }

    @Override
    public void deleteById(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId) {
        return likeRepository.findByUser_IdAndTweet_Id(userId, tweetId);
    }

    @Override
    public boolean existsByUserIdAndTweetId(Long userId, Long tweetId) {
        return likeRepository.existsByUser_IdAndTweet_Id(userId, tweetId);
    }

    public Like toggleLike(Long tweetId, Long userId) {
        Tweet tweet = tweetService.getById(tweetId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new LikeNotFoundException("User not found"));


        Optional<Like> existingLike = likeRepository.findByUser_IdAndTweet_Id(userId,tweetId);

        if (existingLike.isPresent()) {
            Like like = existingLike.get();


            if (like.getUser().getId() != (userId)) {
                throw new IllegalStateException("User is not authorized to delete this like");
            }


            likeRepository.delete(like);
            return null;
        } else {
            Like like = new Like();
            like.setTweet(tweet);
            like.setUser(user);
            return likeRepository.save(like);
        }
    }
}
