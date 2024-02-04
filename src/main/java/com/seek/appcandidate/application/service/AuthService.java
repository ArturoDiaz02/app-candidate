package com.seek.appcandidate.application.service;

import com.seek.appcandidate.application.port.input.IAuthService;
import com.seek.appcandidate.application.port.output.LoadAuthPort;
import com.seek.appcandidate.application.port.output.UpdateAuthPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final LoadAuthPort loadAuthPort;
    private final UpdateAuthPort updateAuthPort;
}
