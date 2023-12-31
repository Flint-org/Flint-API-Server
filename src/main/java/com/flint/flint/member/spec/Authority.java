package com.flint.flint.member.spec;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 정순원
 * @Since 2023-08-07
 */
@AllArgsConstructor
@Getter
public enum Authority {

    AUTHUSER("ROLE_AUTHUSER"),
    UNAUTHUSER("ROLE_UNAUTHUSER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}
