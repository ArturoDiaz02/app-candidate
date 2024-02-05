package com.seek.appcandidate.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
