package com.seek.appcandidate.infrastructure.dto;

import com.seek.appcandidate.domain.enums.EGender;
import com.seek.appcandidate.domain.validation.date.DateFormat;
import com.seek.appcandidate.domain.validation.emailOrPhone.EmailOrPhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@EmailOrPhone
public record CreateCandidateDTO(
        @NotNull
        @NotBlank
        String name,
        @Email
        String email,
        @NotNull
        EGender gender,
        String phone,
        String address,
        @Min(0)
        @NotNull
        Double salary_expected,
        @DateFormat
        String date_of_birth,
        @Min(1)
        @NotNull
        Integer experience_years,
        String education

) {
}
