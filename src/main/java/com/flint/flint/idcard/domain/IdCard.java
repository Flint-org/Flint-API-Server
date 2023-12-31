package com.flint.flint.idcard.domain;

import com.flint.flint.common.BaseTimeEntity;
import com.flint.flint.member.domain.main.Member;
import com.flint.flint.idcard.spec.InterestType;
import com.flint.flint.idcard.spec.InterestConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 정순원
 * @since 2023-08-07
 */
@Entity
@Getter
@NoArgsConstructor
public class IdCard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 4)
    @NotNull
    private int admissionYear;

    @Column(length = 100)
    @NotNull
    private String university;

    @Column(length = 100)
    @NotNull
    private String major;

    @Column(length = 500, columnDefinition = "longtext")
    private String cardBackIntroduction;

    @Column(length = 100)
    private String cardBackSNSId;

    @Column(name = "card_back_mbti", length = 50)
    private String cardBackMBTI;

    @Convert(converter = InterestConverter.class)
    @Column(name ="card_back_interest_list")
    private List<InterestType> cardBackInterestTypeList;


    @Builder
    public IdCard(Member member, String email, int admissionYear, String university, String major) {
        this.member = member;
        this.admissionYear = admissionYear;
        this.university = university;
        this.major = major;
    }

    public void updateBack(String cardBackIntroduction, String cardBackMBTI, String cardBackSNSId, List<InterestType> cardBackInterestTypeList) {
        this.cardBackIntroduction = cardBackIntroduction;
        this.cardBackMBTI = cardBackMBTI;
        this.cardBackSNSId = cardBackSNSId;
        this.cardBackInterestTypeList = cardBackInterestTypeList;
    }
}
