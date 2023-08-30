package com.flint.flint.community.domain.post;

import com.flint.flint.common.BaseTimeEntity;
<<<<<<< HEAD:src/main/java/com/flint/flint/community/domain/post/PostLike.java
import com.flint.flint.member.domain.Member;
=======
import com.flint.flint.community.domain.Post;
>>>>>>> develop:src/main/java/com/flint/flint/community/domain/PostLike.java
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 좋아요 엔티티
 * @author 신승건
 * @since 2023-08-04
 */
@Entity
@Getter
@NoArgsConstructor
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_LIKE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public PostLike(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
}
