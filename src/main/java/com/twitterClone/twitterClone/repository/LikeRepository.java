package com.twitterClone.twitterClone.repository;

import com.twitterClone.twitterClone.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser_IdAndTweet_Id(Long userId, Long tweetId);
    boolean existsByUser_IdAndTweet_Id(Long userId, Long tweetId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.tweet.id = :tweetId")
    int getLikeCountByTweetId(@Param("tweetId") Long tweetId);
}

