package com.seek.appcandidate.infrastructure.adapter.persistence;

import com.seek.appcandidate.application.port.output.LoadAuthPort;
import com.seek.appcandidate.application.port.output.UpdateAuthPort;
import com.seek.appcandidate.domain.model.User;
import com.seek.appcandidate.infrastructure.common.Adapter;
import com.seek.appcandidate.infrastructure.repository.IAuthRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Adapter
@AllArgsConstructor
public class AuthPersistenceAdapter implements LoadAuthPort, UpdateAuthPort {

    private final IAuthRepository authRepository;

    @Override
    public Mono<User> findByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Override
    public Mono<User> register(User user) {
        return authRepository.save(user);
    }
}
