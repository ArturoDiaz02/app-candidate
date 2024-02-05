package com.seek.appcandidate.application.port.output;

import com.seek.appcandidate.domain.model.User;
import reactor.core.publisher.Mono;

public interface LoadAuthPort {

    Mono<User> findByEmail(String email);
}
