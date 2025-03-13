package com.twitterClone.twitterClone.repository;

import com.twitterClone.twitterClone.entity.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {
    void deleteByTweetId(Long tweetId);

    @Query("SELECT COUNT(r) FROM Retweet r WHERE r.tweet.id = :tweetId")
    int getRetweetCountByTweetId(@Param("tweetId") Long tweetId);

    boolean existsByUserIdAndTweetId(Long userId, Long tweetId);

    Optional<Retweet> findByUserIdAndTweetId(Long userId, Long tweetId);
}
