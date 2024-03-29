package com.seek.appcandidate.domain.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
public class CustomError {
    HttpStatus status;
    List<CustomDetail> details;
}