package com.seek.appcandidate.domain.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    @Override
    public void initialize(DateFormat constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        if (!s.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return false;
        }

        String[] parts = s.split("-");
        try {
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);

            boolean isValidDay = (day >= 1 && day <= 31);
            boolean isValidMonth = (month >= 1 && month <= 12);

            return isValidDay && isValidMonth;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
