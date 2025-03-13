package com.twitterClone.twitterClone.controller;

import com.twitterClone.twitterClone.entity.Comment;
import com.twitterClone.twitterClone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getComments(@RequestBody Long tweetid) {
        return commentService.findByTweetId(tweetid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment save(@RequestBody Comment comment,@RequestBody Long tweetId) {
       return commentService.save(comment, tweetId);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.replaceOrCreate(id, comment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {}

}
