package com.flint.flint.idcard.dto.response;

import com.flint.flint.idcard.domain.IdCard;
import com.flint.flint.idcard.spec.InterestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 정순원
 * @since 2023-09-10
 */
public class IdCardGetResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyIdCard {
        private Long id;
        private int admissionYear;
        private String university;
        private String major;
        private String cardBackIntroduction;
        private String cardBackSNSId;
        private String cardBackMBTI;
        private List<InterestType> cardBackInterestTypeList;

        public static MyIdCard of(IdCard idCard) {
            return new MyIdCard(
                    idCard.getId(),
                    idCard.getAdmissionYear(),
                    idCard.getUniversity(),
                    idCard.getMajor(),
                    idCard.getCardBackIntroduction(),
                    idCard.getCardBackSNSId(),
                    idCard.getCardBackMBTI(),
                    idCard.getCardBackInterestTypeList()
            );
        }
    }
}