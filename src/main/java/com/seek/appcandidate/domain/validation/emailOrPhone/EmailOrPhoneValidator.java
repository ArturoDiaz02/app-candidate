package com.seek.appcandidate.domain.validation.emailOrPhone;

import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailOrPhoneValidator implements ConstraintValidator<EmailOrPhone, CreateCandidateDTO> {

    @Override
    public void initialize(EmailOrPhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreateCandidateDTO createCandidateDTO, ConstraintValidatorContext constraintValidatorContext) {
        if(createCandidateDTO.email() == null || createCandidateDTO.phone() == null) {return false;}
        return !createCandidateDTO.email().isBlank() || !createCandidateDTO.phone().isBlank();
    }
}
