package com.flint.flint.security.auth.jwt;

import com.flint.flint.member.domain.main.Member;
import com.flint.flint.member.domain.spec.Authority;
import com.flint.flint.member.domain.spec.Gender;
import com.flint.flint.member.repository.MemberRepository;
import com.flint.flint.security.auth.dto.ClaimsDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceTest {

    @Autowired
     MemberRepository memberRepository;

    @Autowired
     JwtService jwtService;

    String accessToken;
    @BeforeAll
    void setup() {
        Member member = Member.builder()
                .providerId("1234")
                .authority(Authority.ANAUTHUSER)
                .birthday(LocalDate.parse("2000-11-13"))
                .email("onesummer24@naver.com")
                .gender(Gender.MALE)
                .providerName("naver")
                .build();
        memberRepository.save(member);
        ClaimsDTO claimsDTO = ClaimsDTO.from(member);
        accessToken = jwtService.generateAccessToken(claimsDTO);
    }

    @Test
    @DisplayName("토큰 검증")
    void verifyToken() {
        System.out.println("엑세스 토큰:" + accessToken);
        Date expiration = jwtService.parseExpiration(accessToken);
        boolean isAlreadyExpired = expiration.after(new Date());
        System.out.println("expiration:" + expiration);
        System.out.println("new date:" + new Date());
        System.out.println("isAlreadyExpired:" + isAlreadyExpired);
    }

}