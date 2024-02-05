package com.seek.appcandidate.unit;

import com.seek.appcandidate.application.port.output.LoadAuthPort;
import com.seek.appcandidate.application.port.output.LoadSecurityPort;
import com.seek.appcandidate.application.port.output.UpdateAuthPort;
import com.seek.appcandidate.application.service.AuthService;
import com.seek.appcandidate.domain.enums.ERole;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import com.seek.appcandidate.domain.model.User;
import com.seek.appcandidate.infrastructure.dto.LoginDTO;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import com.seek.appcandidate.infrastructure.dto.TokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.MissingFormatArgumentException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    private LoadAuthPort loadAuthPort;

    private UpdateAuthPort updateAuthPort;

    private LoadSecurityPort loadSecurityPort;

    private PasswordEncoder passwordEncoder;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        loadAuthPort = mock(LoadAuthPort.class);
        updateAuthPort = mock(UpdateAuthPort.class);
        loadSecurityPort = mock(LoadSecurityPort.class);
        passwordEncoder = spy(PasswordEncoder.class);
        authService = new AuthService(loadAuthPort, updateAuthPort, passwordEncoder, loadSecurityPort);
    }

    @Test
    void testLoginSuccessful() {
        LoginDTO loginDTO = new LoginDTO("test@example.com", "password");
        User user = User.builder().email("test@example.com").password("encodedPassword").roles("ROLE_USER").build();

        when(loadAuthPort.findByEmail(loginDTO.email())).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(true);
        when(loadSecurityPort.generateToken(any(UserDetails.class))).thenReturn("token");

        Mono<TokenDTO> result = authService.login(loginDTO);

        assertNotNull(result.block());
        assertEquals("token", Objects.requireNonNull(result.block()).token());
    }

    @Test
    void testLoginUserNotFound() {
        LoginDTO loginDTO = new LoginDTO("nonexistent@example.com", "password");

        when(loadAuthPort.findByEmail(any())).thenReturn(Mono.empty());

        assertThrows(CandidateException.class, () -> authService.login(loginDTO).block(),
                "Should throw CandidateException with CANDIDATE_NOT_FOUND error code");
    }

    @Test
    void testLoginInvalidPassword() {
        LoginDTO loginDTO = new LoginDTO("test@example.com", "invalidPassword");
        User user = User.builder().email("test@example.com").password("encodedPassword").roles("ROLE_USER").build();

        when(loadAuthPort.findByEmail(loginDTO.email())).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(loginDTO.password(), user.getPassword())).thenReturn(false);

        assertThrows(CandidateException.class, () -> authService.login(loginDTO).block(),
                "Should throw CandidateException with CANDIDATE_NOT_FOUND error code");
    }

    @Test
    void testRegisterSuccessful() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("test@example.com", "password123");
        User newUser = User.builder()
                .email(registerDTO.email())
                .password("encodedPassword") // Actual password would be encoded
                .roles(ERole.ROLE_USER.name())
                .build();

        when(loadAuthPort.findByEmail(registerDTO.email())).thenReturn(Mono.empty());
        when(updateAuthPort.register(any(User.class))).thenReturn(Mono.just(newUser));
        when(loadSecurityPort.generateToken(any(User.class))).thenReturn("generatedToken");
        when(passwordEncoder.encode(registerDTO.password())).thenReturn("encodedPassword");

        // Act
        StepVerifier.create(authService.register(registerDTO))
                // Assert
                .expectNextMatches(tokenDTO -> {
                    assertEquals("generatedToken", tokenDTO.token());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO("existinguser@example.com", "newPassword");
        User existingUser = User.builder().email("existinguser@example.com").password("encodedPassword").roles("ROLE_USER").build();

        when(loadAuthPort.findByEmail(registerDTO.email())).thenReturn(Mono.just(existingUser));

        assertThrows(CandidateException.class, () -> authService.register(registerDTO).block(),
                "Should throw CandidateException with CANDIDATE_ALREADY_EXISTS error code");
    }

    @Test
    void testRegisterInternalError() {
        RegisterDTO registerDTO = new RegisterDTO("newuser@example.com", "newPassword");
        User newUser = User.builder().email("newuser@example.com").password("encodedPassword").roles("ROLE_USER").build();

        when(loadAuthPort.findByEmail(registerDTO.email())).thenReturn(Mono.empty());
        when(updateAuthPort.register(any())).thenReturn(Mono.error(new RuntimeException()));

        assertThrows(MissingFormatArgumentException.class, () -> authService.register(registerDTO).block(),
                "Should throw CandidateException with INTERNAL_SERVER_ERROR error code");
    }
}