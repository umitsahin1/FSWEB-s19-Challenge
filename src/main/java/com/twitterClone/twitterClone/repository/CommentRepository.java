package com.twitterClone.twitterClone.repository;

import com.twitterClone.twitterClone.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTweet_Id(Long tweetId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.tweet.id = :tweetId")
    int getCommentCountByTweetId(@Param("tweetId") Long tweetId);
}
