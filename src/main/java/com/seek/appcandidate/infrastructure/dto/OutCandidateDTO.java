package com.seek.appcandidate.infrastructure.dto;

import com.seek.appcandidate.domain.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
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
    private String created_at;
    private String updated_at;
}
