package com.twitterClone.twitterClone.controller;

import com.twitterClone.twitterClone.dto.CommentRequest;
import com.twitterClone.twitterClone.dto.CommentResponse;
import com.twitterClone.twitterClone.entity.Comment;
import com.twitterClone.twitterClone.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    @GetMapping
    public List<CommentResponse> getComments(@RequestParam Long tweetId) {
        return commentService.findByTweetId(tweetId).stream()
                .map(this::toResponse)
                .toList();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse save(@RequestBody @Valid CommentRequest commentRequest) {
        Comment comment = toEntity(commentRequest);
        return toResponse(commentService.save(comment, commentRequest.getTweetId()));
    }


    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable Long id, @RequestBody @Valid CommentRequest commentRequest) {
        Comment comment = toEntity(commentRequest);
        return toResponse(commentService.replaceOrCreate(id, comment));
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteById(id);
    }


    private CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTweet().getId(),
                comment.getUser().getId(),
                comment.getCreatedAt()
        );
    }


    private Comment toEntity(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        return comment;
    }
}
