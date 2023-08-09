package com.flint.flint.community.domain;

import com.flint.flint.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private PostComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> replies = new ArrayList<>();

    // 유저

    @Column(length = 300, nullable = false)
    private String contents;

    @Builder
    public PostComment(Post post, PostComment parentComment, List<PostComment> replies, String contents) {
        this.post = post;
        this.parentComment = parentComment;
        this.replies = replies;
        this.contents = contents;
    }
}