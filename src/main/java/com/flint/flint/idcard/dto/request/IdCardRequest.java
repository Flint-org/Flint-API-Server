package com.flint.flint.idcard.dto.request;

import com.flint.flint.idcard.spec.InterestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class IdCardRequest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class  updateBackReqeust {
        private Long idCardId;
        private String cardBackIntroduction;
        private String cardBackSNSId;
        private String cardBackMBTI;
        private List<InterestType> cardBackInterestTypeList;
    }
}
