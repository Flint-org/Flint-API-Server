package com.flint.flint.idcard.controller;

import com.flint.flint.common.ResponseForm;
import com.flint.flint.idcard.service.IdCardBoxService;
import com.flint.flint.security.auth.dto.AuthorityMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author 정순원
 * @since 2023-09-22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/idcard/box")
public class IdCardBoxUpdateController {

    private final IdCardBoxService idCardBoxService;

    @PreAuthorize("hasRole('ROLE_AUTHUSER')")
    @PostMapping("/{idCardId}")
    public ResponseForm createIdCardBox (@PathVariable Long idCardId, @AuthenticationPrincipal AuthorityMemberDTO memberDTO) {
        Long memberId = memberDTO.getId();
        idCardBoxService.createIdCardBox(idCardId, memberId);
        return new ResponseForm<>();
    }

    @PreAuthorize("hasRole('ROLE_AUTHUSER')")
    @DeleteMapping("/{idCardId}")
    public ResponseForm removeIdCardBox (@PathVariable Long idCardId, @AuthenticationPrincipal AuthorityMemberDTO memberDTO) {
        Long memberId = memberDTO.getId();
        idCardBoxService.removeIdCardBox(idCardId, memberId);
        return new ResponseForm<>();
    }
}
