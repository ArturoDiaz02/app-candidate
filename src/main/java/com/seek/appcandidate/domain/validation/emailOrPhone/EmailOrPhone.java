package com.seek.appcandidate.domain.validation.emailOrPhone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailOrPhoneValidator.class)
@Target( {ElementType.TYPE, ElementType.ANNOTATION_TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailOrPhone {
    String message() default "the email or the phoneNumber must have a value";
    String field() default "email or phoneNumber";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
