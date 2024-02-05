package com.seek.appcandidate.infrastructure.dto;

import com.seek.appcandidate.domain.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutCandidateDTO {
    private int id;
    private String name;
    private String email;
    private EGender gender;
    private String phone;
    private String address;
    private Double salary_expected;
    private String date_of_birth;
    private Integer experience_years;
    private String education;
}
