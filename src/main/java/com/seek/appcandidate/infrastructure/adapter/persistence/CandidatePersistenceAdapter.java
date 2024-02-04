package com.seek.appcandidate.infrastructure.adapter.persistence;

import com.seek.appcandidate.application.port.output.LoadCandidatePort;
import com.seek.appcandidate.application.port.output.UpdateCandidatePort;
import com.seek.appcandidate.domain.model.Candidate;
import com.seek.appcandidate.infrastructure.common.Adapter;
import com.seek.appcandidate.infrastructure.repository.ICandidateRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Adapter
@RequiredArgsConstructor
public class CandidatePersistenceAdapter implements LoadCandidatePort, UpdateCandidatePort {

    private final ICandidateRepository candidateRepository;
    private final Logger logger = LoggerFactory.getLogger(CandidatePersistenceAdapter.class);

    @Override
    public Flux<Candidate> getAllCandidates(String gender, Double minSalary, Double maxSalary, Double experience) {
        logger.info("Getting all candidates");
        return candidateRepository.findByGenderAndSalaryExpectedBetweenAndExperience(
                gender,
                minSalary,
                maxSalary,
                experience
        );
    }

    @Override
    public Mono<Candidate> getCandidateById(Integer id) {
        logger.info("Getting candidate by id: " + id);
        return candidateRepository.findById(id);
    }

    @Override
    public Mono<Candidate> getCandidateByEmail(String email) {
        logger.info("Getting candidate by email: " + email);
        return candidateRepository.findByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByEmailOrPhone(String email, String phone) {
        logger.info("Checking if candidate exists by email or phone");
        return candidateRepository.findByEmailOrPhone(email, phone)
                .hasElements();
    }

    @Override
    public Mono<Candidate> createCandidate(Candidate candidate) {
        logger.info("Saving candidate: " + candidate.toString());
        return candidateRepository.save(candidate);
    }

    @Override
    public Mono<Candidate> updateCandidate(Candidate candidate) {
        logger.info("Updating candidate: " + candidate.toString());
        return candidateRepository.save(candidate);
    }

    @Override
    public Mono<Void> deleteCandidate(Candidate candidate) {
        logger.info("Deleting candidate by id: " + candidate.getId());
        return candidateRepository.delete(candidate);
    }

    @Override
    public Mono<Void> deleteAllCandidates() {
        logger.info("Deleting all candidates");
        return candidateRepository.deleteAll();
    }
}
