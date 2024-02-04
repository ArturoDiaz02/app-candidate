package com.seek.appcandidate.application.port.input;

import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICandidateService {
    Mono<OutCandidateDTO> createCandidate(CreateCandidateDTO candidate);
    Mono<OutCandidateDTO> updateCandidate(Integer id, CreateCandidateDTO candidate);
    Mono<OutCandidateDTO> getCandidateById(Integer id);
    Mono<OutCandidateDTO> getCandidateByEmail(String email);
    Flux<OutCandidateDTO> getAllCandidates(Double minSalary, Double maxSalary, Integer minExperience, Integer maxExperience);
    Mono<ResponseEntity<String>> deleteCandidate(Integer id);
    Mono<ResponseEntity<String>> deleteAllCandidates();
}
