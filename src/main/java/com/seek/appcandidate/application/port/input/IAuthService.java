package com.seek.appcandidate.application.port.input;

import com.seek.appcandidate.infrastructure.dto.LoginDTO;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import com.seek.appcandidate.infrastructure.dto.TokenDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface IAuthService {

    Mono<TokenDTO> login(LoginDTO loginDTO);

    Mono<TokenDTO>  register(RegisterDTO registerDTO);
}
