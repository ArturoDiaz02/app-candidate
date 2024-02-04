package com.seek.appcandidate.infrastructure.adapter.rest.candidate;

import com.seek.appcandidate.application.port.input.ICandidateService;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CandidateController implements ICandidateAPI {

    private final ICandidateService candidateService;
    private final Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @Override
    public Mono<OutCandidateDTO> createCandidate(CreateCandidateDTO dto) {
        logger.info("Init Create Candidate: " + dto);
        return candidateService.createCandidate(dto);
    }

    @Override
    public Mono<OutCandidateDTO> getCandidateById(Integer id) {
        logger.info("Init Get Candidate By Id: " + id);
        return candidateService.getCandidateById(id);
    }

    @Override
    public Mono<OutCandidateDTO> getCandidateByEmail(String email) {
        logger.info("Init Get Candidate By Email: " + email);
        return candidateService.getCandidateByEmail(email);
    }

    @Override
    public Flux<OutCandidateDTO> getAllCandidates(Double minSalary, Double maxSalary, Integer maxExperience, Integer minExperience) {
        logger.info("Init Get All Candidates");
        return candidateService.getAllCandidates(minSalary, maxSalary, maxExperience, minExperience);
    }

    @Override
    public Mono<OutCandidateDTO> updateCandidate(Integer id, CreateCandidateDTO dto) {
        logger.info("Init Update Candidate: " + id);
        return candidateService.updateCandidate(id, dto);
    }

    @Override
    public Mono<ResponseEntity<String>> deleteCandidate(Integer id) {
        logger.info("Init Delete Candidate: " + id);
        return candidateService.deleteCandidate(id);
    }

    @Override
    public Mono<ResponseEntity<String>> deleteAllCandidates() {
        logger.info("Init Delete All Candidates");
        return candidateService.deleteAllCandidates();
    }
}
