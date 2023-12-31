package com.flint.flint.member.domain.report;

import com.flint.flint.community.domain.post.PostComment;
import com.flint.flint.common.BaseTimeEntity;
import com.flint.flint.member.domain.main.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author 정순원
 * @Since 2023-08-07
 */
@Entity
@Getter
@NoArgsConstructor
public class PostCommentReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private PostComment postComment;

    @Builder
    public PostCommentReport(Member member, PostComment postComment) {
        this.member = member;
        this.postComment = postComment;
    }
}