package com.flint.flint.community.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대분류 전공 게시판 목록 조회 결과 DTO
 * @author 신승건
 * @since 2023-08-21
 */
@Data
@NoArgsConstructor
public class UpperMajorListResponse {

    private Long boardId;
    private Long upperMajorId;
    private String upperMajorName;

    @Builder
    public UpperMajorListResponse(Long boardId, Long upperMajorId, String upperMajorName) {
        this.boardId = boardId;
        this.upperMajorId = upperMajorId;
        this.upperMajorName = upperMajorName;
    }
}
