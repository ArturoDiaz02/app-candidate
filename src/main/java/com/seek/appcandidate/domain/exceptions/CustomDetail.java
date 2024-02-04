package com.seek.appcandidate.domain.exceptions;

import io.r2dbc.spi.Parameter;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class CustomDetail {
    private Integer errorCode;
    private String errorMessage;
}