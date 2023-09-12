package com.flint.flint.idcard.controller;

import com.flint.flint.common.ResponseForm;
import com.flint.flint.idcard.dto.response.IdCardGetResponse;
import com.flint.flint.idcard.service.IdCardService;
import com.flint.flint.security.auth.dto.AuthorityMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 정순원
 * @since 2023-09-12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/idcard")
public class IdCardGetController {

    private final IdCardService idCardService;

    @PostMapping("/my")
    public ResponseForm<IdCardGetResponse.MyIdCard> getMyIdCard(@AuthenticationPrincipal AuthorityMemberDTO authorityMemberDTO) {
        Long id = authorityMemberDTO.getId();
        return new ResponseForm<>(idCardService.findMyIdCardByMemberId(id));
    }
}