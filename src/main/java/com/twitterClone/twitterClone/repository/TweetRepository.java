package com.twitterClone.twitterClone.repository;

import com.twitterClone.twitterClone.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SElECT t FROM Tweet t WHERE t.user.id = :userId")
    List<Tweet> findByUser_Id(Long userId);
}
