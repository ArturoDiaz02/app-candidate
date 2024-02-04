package com.seek.appcandidate.domain.validation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateFormatValidator.class)
@Target( {ElementType.TYPE, ElementType.FIELD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {

    String message() default "the date must be in the format dd-MM-yyyy";
    String field() default "date_of_birth";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
