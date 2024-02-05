package com.seek.appcandidate.infrastructure.adapter.rest.auth;

import com.seek.appcandidate.infrastructure.dto.LoginDTO;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import com.seek.appcandidate.infrastructure.dto.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@RequestMapping(IAuthAPI.BASE_URL)
public interface IAuthAPI {
    String BASE_URL = "/api/v1/auth";

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Login",
            description = "Login",
            tags = {"auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login",
                            content = @Content(
                                    schema = @Schema(implementation = TokenDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(implementation = String.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    schema = @Schema(implementation = String.class),
                                    mediaType = "application/json"
                            )
                    ),
            }
    )
    Mono<TokenDTO> login(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginDTO.class)
                    )
            )
            LoginDTO dto
    );

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register",
            description = "Register",
            tags = {"auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Register",
                            content = @Content(
                                    schema = @Schema(implementation = TokenDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(implementation = String.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<TokenDTO> register(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Register",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterDTO.class)
                    )
            )
            RegisterDTO dto
    );
}
