package com.seek.appcandidate.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seek.appcandidate.application.port.input.IAuthService;
import com.seek.appcandidate.infrastructure.adapter.rest.auth.AuthController;
import com.seek.appcandidate.infrastructure.dto.LoginDTO;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import com.seek.appcandidate.infrastructure.dto.TokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class AuthControllerTest {

    @MockBean
    private IAuthService authService;

    private WebTestClient webTestClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new AuthController(authService)).build();
    }

    @Test
    void testLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password");
        TokenDTO tokenDTO = new TokenDTO("token");

        when(authService.login(any(LoginDTO.class))).thenReturn(Mono.just(tokenDTO));

        webTestClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(loginDTO))
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenDTO.class)
                .isEqualTo(tokenDTO);
    }

    @Test
    void testRegister() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("newuser@example.com", "newPassword");
        TokenDTO tokenDTO = new TokenDTO("token");

        when(authService.register(any(RegisterDTO.class))).thenReturn(Mono.just(tokenDTO));

        webTestClient.post()
                .uri("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(registerDTO))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TokenDTO.class)
                .isEqualTo(tokenDTO);
    }
}
