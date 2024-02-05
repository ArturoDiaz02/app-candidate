package com.seek.appcandidate.application.service;

import com.seek.appcandidate.application.port.input.IAuthService;
import com.seek.appcandidate.application.port.output.LoadAuthPort;
import com.seek.appcandidate.application.port.output.LoadSecurityPort;
import com.seek.appcandidate.application.port.output.UpdateAuthPort;
import com.seek.appcandidate.domain.enums.EErrorCode;
import com.seek.appcandidate.domain.enums.ERole;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import com.seek.appcandidate.domain.model.User;
import com.seek.appcandidate.infrastructure.dto.LoginDTO;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import com.seek.appcandidate.infrastructure.dto.TokenDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final LoadAuthPort loadAuthPort;
    private final UpdateAuthPort updateAuthPort;
    private final PasswordEncoder passwordEncoder;
    private final LoadSecurityPort loadSecurityPort;


    @Override
    public Mono<TokenDTO> login(LoginDTO loginDTO) {
        return loadAuthPort.findByEmail(loginDTO.email())
                .filter(user -> passwordEncoder.matches(loginDTO.password(), user.getPassword()))
                .map(loadSecurityPort::generateToken)
                .onErrorResume(e -> Mono.error(new CandidateException(EErrorCode.CANDIDATE_NOT_FOUND, loginDTO.email())))
                .map(TokenDTO::new);

    }

    @Override
    public Mono<TokenDTO> register(RegisterDTO registerDTO) {
        User newUser = User.builder()
                .email(registerDTO.email())
                .password(passwordEncoder.encode(registerDTO.password()))
                .roles(ERole.ROLE_USER.name())
                .build();

       return loadAuthPort.findByEmail(registerDTO.email())
                .flatMap(u -> Mono.error(new CandidateException(EErrorCode.CANDIDATE_ALREADY_EXISTS, registerDTO.email())))
                .switchIfEmpty(Mono.just(newUser))
                .flatMap(user -> updateAuthPort.register((User) user)
                        .map(loadSecurityPort::generateToken)
                        .onErrorResume(e -> Mono.error(new CandidateException(EErrorCode.INTERNAL_SERVER_ERROR)))
                        .map(TokenDTO::new)
                );
    }
}
