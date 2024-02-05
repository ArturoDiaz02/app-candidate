package com.seek.appcandidate.infrastructure.repository;

import com.seek.appcandidate.domain.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IAuthRepository extends R2dbcRepository<User, Integer>{

    Mono<User> findByEmail(String email);
}
