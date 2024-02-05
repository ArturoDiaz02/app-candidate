package com.seek.appcandidate.unit;


import com.seek.appcandidate.application.port.output.LoadCandidatePort;
import com.seek.appcandidate.application.port.output.UpdateCandidatePort;
import com.seek.appcandidate.domain.enums.EGender;
import com.seek.appcandidate.domain.exceptions.CandidateException;
import com.seek.appcandidate.domain.model.Candidate;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import com.seek.appcandidate.infrastructure.mapper.ICandidateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.seek.appcandidate.application.service.CandidateService;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CandidateServiceTest {

    @Mock
    private LoadCandidatePort loadCandidatePort;

    @Mock
    private UpdateCandidatePort updateCandidatePort;

    @Mock
    private ICandidateMapper candidateMapper;

    @InjectMocks
    private CandidateService candidateService;

    @BeforeEach
    void setUp() {
        candidateService = new CandidateService(loadCandidatePort, updateCandidatePort, candidateMapper);
    }

    @Test
    void testCreateCandidateSuccess() {
        // Arrange
        CreateCandidateDTO createCandidateDTO = new CreateCandidateDTO(
                "John Doe",
                "john.doe@example.com",
                EGender.MALE,
                "123456789",
                "Address",
                50000.0,
                "01-01-1990",
                5,
                "Education"
        );

        when(loadCandidatePort.existsByEmailOrPhone(anyString(), anyString())).thenReturn(Mono.just(false));
        when(candidateMapper.candidateFromCreateCandidateDTO(any())).thenReturn(new Candidate());
        when(updateCandidatePort.updateOrCreateCandidate(any())).thenReturn(Mono.just(new Candidate()));
        when(candidateMapper.outCandidateDTOFromCandidate(any())).thenReturn(new OutCandidateDTO(
                1,
                "John Doe",
                "jon@gmail.com",
                EGender.FEMALE,
                "123456789",
                "Address",
                50000.0,
                "01-01-1990",
                5,
                "Education"
        ));

        // Act
        Mono<OutCandidateDTO> result = candidateService.createCandidate(createCandidateDTO);

        // Assert
        assertNotNull(result);
        assertTrue(result.block() instanceof OutCandidateDTO);
    }

    @Test
    void testCreateCandidateFailureCandidateExists() {
        // Arrange
        CreateCandidateDTO createCandidateDTO = new CreateCandidateDTO(
                "John Doe",
                "john.doe@example.com",
                EGender.MALE,
                "123456789",
                "Address",
                50000.0,
                "01-01-1990",
                5,
                "Education"
        );

        when(loadCandidatePort.existsByEmailOrPhone(anyString(), anyString())).thenReturn(Mono.just(true));

        // Act & Assert
        assertThrows(CandidateException.class, () -> candidateService.createCandidate(createCandidateDTO).block());
    }


    @Test
    void testGetCandidateByIdSuccess() {
        // Arrange
        Integer candidateId = 1;
        when(loadCandidatePort.getCandidateById(candidateId)).thenReturn(Mono.just(new Candidate()));
        when(candidateMapper.outCandidateDTOFromCandidate(any())).thenReturn(new OutCandidateDTO(
                1,
                "John Doe",
                "jon@gmail.com",
                EGender.FEMALE,
                "123456789",
                "Address",
                50000.0,
                "01-01-1990",
                5,
                "Education"
        ));

        // Act
        Mono<OutCandidateDTO> result = candidateService.getCandidateById(candidateId);

        // Assert
        assertNotNull(result);
        assertTrue(result.block() instanceof OutCandidateDTO);
    }

    @Test
    void testGetCandidateByIdFailureCandidateNotFound() {
        // Arrange
        Integer candidateId = 1;
        when(loadCandidatePort.getCandidateById(candidateId)).thenReturn(Mono.empty());

        // Act & Assert
        assertThrows(CandidateException.class, () -> candidateService.getCandidateById(candidateId).block());
    }

    @Test
    void testGetCandidateByEmailSuccess() {
        // Arrange
        String email = "test@example.com";
        when(loadCandidatePort.getCandidateByEmail(email)).thenReturn(Mono.just(new Candidate()));
        when(candidateMapper.outCandidateDTOFromCandidate(any())).thenReturn(new OutCandidateDTO(
                1,
                "John Doe",
                "jon@gmail.com",
                EGender.FEMALE,
                "123456789",
                "Address",
                50000.0,
                "01-01-1990",
                5,
                "Education"
        ));

        // Act
        Mono<OutCandidateDTO> result = candidateService.getCandidateByEmail(email);

        // Assert
        assertNotNull(result);
        assertTrue(result.block() instanceof OutCandidateDTO);
    }

    @Test
    void testGetCandidateByEmailFailureCandidateNotFound() {
        // Arrange
        String email = "test@example.com";
        when(loadCandidatePort.getCandidateByEmail(email)).thenReturn(Mono.empty());

        // Act & Assert
        assertThrows(CandidateException.class, () -> candidateService.getCandidateByEmail(email).block());
    }

    @Test
    void testGetAllCandidatesSuccess() {
        // Arrange
        when(loadCandidatePort.getAllCandidates(anyDouble(), anyDouble(), anyInt(), anyInt())).thenReturn(Flux.just(new Candidate()));
        when(candidateMapper.outCandidateDTOFromCandidate(any())).thenReturn(new OutCandidateDTO(
                1,
                "John Doe",
                "jon@gmail.com",
                EGender.FEMALE,
                "123456789",
                "Address",
                50000.0,
                "01-01-1990",
                5,
                "Education"
        ));

        // Act
        Flux<OutCandidateDTO> result = candidateService.getAllCandidates(2000.0, 5000.0, 2, 5);

        // Assert
        assertNotNull(result);
        assertTrue(result.collectList().block() instanceof List<OutCandidateDTO>);
    }

    @Test
    void testDeleteAllCandidatesSuccess() {
        // Arrange
        when(updateCandidatePort.deleteAllCandidates()).thenReturn(Mono.empty());

        // Act
        Mono<ResponseEntity<String>> result = candidateService.deleteAllCandidates();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.block().getStatusCode());
    }

}
