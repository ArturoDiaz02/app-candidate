package com.seek.appcandidate.infrastructure.adapter.rest.auth;

import com.seek.appcandidate.application.port.input.IAuthService;
import com.seek.appcandidate.infrastructure.dto.LoginDTO;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import com.seek.appcandidate.infrastructure.dto.TokenDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class AuthController implements IAuthAPI{

    private final IAuthService authService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Override
    public Mono<TokenDTO> login(LoginDTO dto) {
        logger.info("Init Login: " + dto);
        return authService.login(dto);
    }

    @Override
    public Mono<TokenDTO> register(RegisterDTO dto) {
        logger.info("Init Register: " + dto);
        return authService.register(dto);
    }
}
