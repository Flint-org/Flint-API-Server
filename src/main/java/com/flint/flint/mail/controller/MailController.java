package com.flint.flint.mail.controller;

import com.flint.flint.common.ResponseForm;
import com.flint.flint.mail.dto.request.SendEmailAuthNumberReqeust;
import com.flint.flint.mail.dto.request.SuccessUniversityAuthRequest;
import com.flint.flint.mail.dto.response.EmailAuthNumberRespose;
import com.flint.flint.mail.service.AuthEmailService;
import com.flint.flint.security.auth.dto.response.AuthenticationResponse;
import com.flint.flint.security.auth.dto.AuthorityMemberDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 메일 발송 관련 API
 *
 * @Author 정순원
 * @Since 2023-08-31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {


    private final AuthEmailService authEmailService;

    /**
     * 인증번호 보내기
     * 검증: API호출 횟수
     */
    @PreAuthorize("hasRole('ROLE_UNAUTHUSER')")
    @PostMapping("/send")
    public ResponseForm<EmailAuthNumberRespose> sendEmail(@Valid @RequestBody SendEmailAuthNumberReqeust request, @AuthenticationPrincipal AuthorityMemberDTO authorityMemberDTO) {
        return new ResponseForm<>(authEmailService.sendCodeEmail(request.getEmail(), authorityMemberDTO.getId()));
    }

    /**
     * 유저 권한 바꿔주고, 명함 생성해주고
     * 인증 한 유저 권한으로 새로운 액세스 토큰, 리프레쉬 토큰 발급해주기
     * 검증: 이메일 인증코드
     */
    @PreAuthorize("hasRole('ROLE_UNAUTHUSER')")
    @PostMapping("/success/auth")
    public ResponseForm<AuthenticationResponse> successUniversityAuth(@Valid @RequestBody SuccessUniversityAuthRequest request, @AuthenticationPrincipal AuthorityMemberDTO authorityMemberDTO) {
        return new ResponseForm<>(authEmailService.successEmailAuth(request, authorityMemberDTO.getId()));
    }
}