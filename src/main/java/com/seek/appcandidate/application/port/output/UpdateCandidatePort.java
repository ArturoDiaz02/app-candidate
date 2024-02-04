package com.seek.appcandidate.application.port.output;

import com.seek.appcandidate.domain.model.Candidate;
import reactor.core.publisher.Mono;

public interface UpdateCandidatePort {

    Mono<Candidate> updateOrCreateCandidate(Candidate candidate);
    Mono<Void> deleteCandidate(Candidate candidate);
    Mono<Void> deleteAllCandidates();
}
