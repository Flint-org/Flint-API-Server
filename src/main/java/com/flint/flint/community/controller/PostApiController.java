package com.flint.flint.community.controller;

import com.flint.flint.common.ResponseForm;
import com.flint.flint.community.dto.request.PostRequest;
import com.flint.flint.community.dto.response.PostLikeResponse;
import com.flint.flint.community.dto.response.PostListResponse;
import com.flint.flint.community.dto.response.PostPreSignedUrlResponse;
import com.flint.flint.community.service.PostLikeUpdateService;
import com.flint.flint.community.service.PostReportService;
import com.flint.flint.community.service.PostScrapUpdateService;
import com.flint.flint.community.service.PostService;
import com.flint.flint.community.spec.SortStrategy;
import com.flint.flint.security.auth.dto.AuthorityMemberDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 신승건
 * @since 2023-09-13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@PreAuthorize("isAuthenticated()")
public class PostApiController {

    private final PostService postService;
    private final PostLikeUpdateService postLikeUpdateService;
    private final PostReportService postReportService;
    private final PostScrapUpdateService postScrapUpdateService;



    @PostMapping("")
    public ResponseForm<List<PostPreSignedUrlResponse>> createPost(
            @AuthenticationPrincipal AuthorityMemberDTO memberDTO,
            @Valid @RequestBody PostRequest postRequest
    ) {
        return new ResponseForm<>(postService.createPost(memberDTO.getProviderId(), postRequest));
    }

    /**
     * 좋아요 생성
     */
    @PostMapping("/like/{postId}")
    public ResponseForm<PostLikeResponse> createPostLike(@AuthenticationPrincipal AuthorityMemberDTO memberDTO, @PathVariable long postId) {
        return new ResponseForm<>(postLikeUpdateService.createPostLike(memberDTO.getProviderId(), postId));
    }

    @GetMapping("")
    public ResponseForm<List<PostListResponse>> getPostsByPaging(
            @RequestParam Long boardId,
            @RequestParam Long cursorId,
            @RequestParam Long size,
            @RequestParam String sortStrategy
    ) {
        return new ResponseForm<>(postService.getPostsByPaging(
                boardId,
                cursorId,
                size,
                SortStrategy.findMatchedEnum(sortStrategy)
        ));
    }
  
    /**
     * 좋아요 취소
     */
    @DeleteMapping("/like/{postId}")
    public ResponseForm<PostLikeResponse> deletePostLike(@AuthenticationPrincipal AuthorityMemberDTO memberDTO, @PathVariable long postId) {
        postLikeUpdateService.deletePostLike(memberDTO.getProviderId(), postId);
        return new ResponseForm<>();
    }
  
     /**
     * 게시물 신고
     */
    @PostMapping("report/{postId}")
    public ResponseForm reportPost(@AuthenticationPrincipal AuthorityMemberDTO memberDTO, @PathVariable long postId){
            postReportService.reportPost(memberDTO.getProviderId(), postId);
            return new ResponseForm<>();
     }

     /**
     * 스크랩 생성
     */
    @PostMapping("/scrap/{postId}")
    public ResponseForm createScrap (@AuthenticationPrincipal AuthorityMemberDTO memberDTO, @PathVariable long postId) {
        postScrapUpdateService.createScrap(memberDTO.getProviderId(), postId);
        return new ResponseForm<>();
    }

    /**
     * 스크랩 삭제
     */
    @DeleteMapping("/scrap/{postId}")
    public ResponseForm deleteScrap (@AuthenticationPrincipal AuthorityMemberDTO memberDTO, @PathVariable long postId) {
        postScrapUpdateService.deleteScrap(memberDTO.getProviderId(), postId);
        return new ResponseForm<>();
    }
}
