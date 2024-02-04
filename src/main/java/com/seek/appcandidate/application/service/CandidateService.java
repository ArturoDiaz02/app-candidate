package com.seek.appcandidate.application.service;

import com.seek.appcandidate.application.port.input.ICandidateService;
import com.seek.appcandidate.application.port.output.LoadCandidatePort;
import com.seek.appcandidate.application.port.output.UpdateCandidatePort;
import com.seek.appcandidate.domain.enums.EExceptionDetails;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import com.seek.appcandidate.domain.model.Candidate;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import com.seek.appcandidate.infrastructure.mapper.ICandidateMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Service
@RequiredArgsConstructor
public class CandidateService implements ICandidateService {

    private final LoadCandidatePort loadCandidatePort;
    private final UpdateCandidatePort updateCandidatePort;
    private final ICandidateMapper candidateMapper;
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private final Logger logger = LoggerFactory.getLogger(CandidateService.class);

    @Override
    public Mono<OutCandidateDTO> createCandidate(CreateCandidateDTO candidate) {
        return loadCandidatePort.existsByEmailOrPhone(candidate.email(), candidate.phone())
                .flatMap(exists -> {
                    if(exists) {
                        logger.error("Candidate already exists by email or phone");
                        return Mono.error(new CandidateException(HttpStatus.BAD_REQUEST, EExceptionDetails.CANDIDATE_ALREADY_EXISTS));
                    }

                    logger.info("Creating candidate: " + candidate);
                    Candidate candidateModel = candidateMapper.candidateFromCreateCandidateDTO(candidate);
                    logger.info("Candidate model: " + candidateModel.toString());

                    try {
                        logger.info("Parsing date: " + candidate.date_of_birth());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                        LocalDate localDate = LocalDate.parse(candidate.date_of_birth(), formatter);
                        logger.info("Parsed date: " + localDate);

                        candidateModel.setDate_of_birth(localDate);

                        return updateCandidatePort.createCandidate(candidateModel)
                                .map(candidateMapper::outCandidateDTOFromCandidate);
                    } catch (DateTimeParseException e) {
                        logger.error("Error parsing date: " + e.getMessage());
                        return Mono.error(new CandidateException(HttpStatus.INTERNAL_SERVER_ERROR, EExceptionDetails.PARSE_DATE_ERROR));
                    }
                });
    }

    @Override
    public Mono<OutCandidateDTO> updateCandidate(Integer id, CreateCandidateDTO candidate) {
        return getMonoCandidateById(id)
                .flatMap(candidateModel -> {
                    logger.info("Candidate model: " + candidateModel);
                    candidateModel.update(candidate);
                    logger.info("Candidate model updated: " + candidateModel);

                    try {
                        logger.info("Parsing date: " + candidate.date_of_birth());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                        LocalDate localDate = LocalDate.parse(candidate.date_of_birth(), formatter);
                        logger.info("Parsed date: " + localDate);

                        candidateModel.setDate_of_birth(localDate);

                        return updateCandidatePort.updateCandidate(candidateModel)
                                .map(candidateMapper::outCandidateDTOFromCandidate);
                    } catch (DateTimeParseException e) {
                        logger.error("Error parsing date: " + e.getMessage());
                        return Mono.error(new CandidateException(HttpStatus.INTERNAL_SERVER_ERROR, EExceptionDetails.PARSE_DATE_ERROR));
                    }
                });
    }

    @Override
    public Mono<OutCandidateDTO> getCandidateById(Integer id) {
        logger.info("Get candidate by id: " + id);
        return getMonoCandidateById(id).map(candidateMapper::outCandidateDTOFromCandidate);
    }

    @Override
    public Mono<OutCandidateDTO> getCandidateByEmail(String email) {
        logger.info("Get candidate by email: " + email);
        return loadCandidatePort.getCandidateByEmail(email)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.error("Candidate not found by email: " + email);
                    return Mono.error(new CandidateException(HttpStatus.NOT_FOUND, EExceptionDetails.CANDIDATE_NOT_FOUND.formatMessage(email)));
                }))
                .map(candidateMapper::outCandidateDTOFromCandidate);
    }

    @Override
    public Flux<OutCandidateDTO> getAllCandidates(String gender, Double minSalary, Double maxSalary, Double experience) {
        //gender EGenderEnum = gender != null ? gender.valueOf(gender) : null;
        logger.info("Get all candidates with filter values -> " + "Gender: " + gender + ", MinSalary: " + minSalary + ", MaxSalary: " + maxSalary + ", Experience: " + experience);
        return loadCandidatePort.getAllCandidates(gender, minSalary, maxSalary, experience)
                .map(candidateMapper::outCandidateDTOFromCandidate);
    }

    @Override
    public Mono<ResponseEntity<String>> deleteCandidate(Integer id) {
        logger.info("Deleting candidate with id: " + id);
        return getMonoCandidateById(id)
                .flatMap(updateCandidatePort::deleteCandidate)
                .then(Mono.just(ResponseEntity.ok("Candidate deleted successfully.")))
                .onErrorResume(throwable -> Mono.error(
                        new CandidateException(
                                HttpStatus.NOT_FOUND,
                                EExceptionDetails.DELETE_CANDIDATE_ERROR.formatMessage(throwable.getMessage())
                        )
                ));


    }

    @Override
    public Mono<ResponseEntity<String>> deleteAllCandidates() {
        logger.info("Deleting all candidates");
        return updateCandidatePort.deleteAllCandidates()
                .then(Mono.just(ResponseEntity.ok("All candidates deleted successfully.")))
                .onErrorResume(throwable -> Mono.error(
                        new CandidateException(
                                HttpStatus.NOT_FOUND,
                                EExceptionDetails.DELETE_ALL_CANDIDATES_ERROR.formatMessage(throwable.getMessage())
                        )
                ));
    }

    private Mono<Candidate> getMonoCandidateById(Integer id) {
        return loadCandidatePort.getCandidateById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    logger.error("Candidate not found: " + id);
                    return Mono.error(new CandidateException(HttpStatus.NOT_FOUND, EExceptionDetails.CANDIDATE_NOT_FOUND.formatMessage(id.toString())));
                }));
    }

}
