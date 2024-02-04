package com.seek.appcandidate.application.port.output;

import com.seek.appcandidate.domain.model.Candidate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoadCandidatePort {

    Flux<Candidate> getAllCandidates(Double minSalary, Double maxSalary, Integer minExperience, Integer maxExperience);

    Mono<Boolean> existsByEmailOrPhone(String email, String phone);
    Mono<Candidate> getCandidateById(Integer id);

    Mono<Candidate> getCandidateByEmail(String email);
}
