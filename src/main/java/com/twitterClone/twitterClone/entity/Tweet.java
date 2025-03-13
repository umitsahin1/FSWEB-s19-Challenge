package com.twitterClone.twitterClone.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tweet",schema = "twitter")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    @Column(name = "content")
    @Size(max = 280)
    @NotBlank
    @NotNull
    private String content;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Like> likes;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<Retweet> retweets;
}
