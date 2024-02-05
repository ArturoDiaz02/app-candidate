package com.seek.appcandidate.integration;

import com.seek.appcandidate.application.port.input.ICandidateService;
import com.seek.appcandidate.domain.enums.EGender;
import com.seek.appcandidate.infrastructure.adapter.rest.candidate.CandidateController;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class CandidateControllerTest {


    @Spy
    private ICandidateService candidateService;

    @InjectMocks
    private CandidateController candidateController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(candidateController).build();
    }

    @Test
    void testCreateCandidateEndpoint() {
        // Arrange
        CreateCandidateDTO requestDTO = createSampleRequestDTO();
        OutCandidateDTO responseDTO = createSampleResponseDTO();
        when(candidateService.createCandidate(any())).thenReturn(Mono.just(responseDTO));

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/candidates")
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(OutCandidateDTO.class)
                .isEqualTo(responseDTO);
    }

    @Test
    void testGetCandidateByIdEndpoint() {
        // Arrange
        int candidateId = 1;
        OutCandidateDTO responseDTO = createSampleResponseDTO();
        when(candidateService.getCandidateById(candidateId)).thenReturn(Mono.just(responseDTO));

        // Act & Assert
        webTestClient.get()
                .uri("/api/v1/candidates/{id}", candidateId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OutCandidateDTO.class)
                .isEqualTo(responseDTO);
    }

    @Test
    void testGetCandidateByEmailEndpoint() {
        // Arrange
        String email = "test@example.com";
        OutCandidateDTO responseDTO = createSampleResponseDTO();
        when(candidateService.getCandidateByEmail(email)).thenReturn(Mono.just(responseDTO));

        // Act & Assert
        webTestClient.get()
                .uri("/api/v1/candidates/email/{email}", email)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OutCandidateDTO.class)
                .isEqualTo(responseDTO);
    }

    @Test
    void testGetAllCandidatesEndpoint() {
        // Arrange
        Flux<OutCandidateDTO> responseDTOs = Flux.just(createSampleResponseDTO(), createSampleResponseDTO());
        when(candidateService.getAllCandidates(null, null, null, null)).thenReturn(responseDTOs);

        // Act & Assert
        webTestClient.get()
                .uri("/api/v1/candidates")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(OutCandidateDTO.class)
                .hasSize(2);
    }

    @Test
    void testUpdateCandidateEndpoint() {
        // Arrange
        int candidateId = 1;
        CreateCandidateDTO requestDTO = createSampleRequestDTO();
        OutCandidateDTO responseDTO = createSampleResponseDTO();
        when(candidateService.updateCandidate(candidateId, requestDTO)).thenReturn(Mono.just(responseDTO));

        // Act & Assert
        webTestClient.put()
                .uri("/api/v1/candidates/{id}", candidateId)
                .bodyValue(requestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(OutCandidateDTO.class)
                .isEqualTo(responseDTO);
    }

    @Test
    @Order(90)
    void testDeleteCandidateEndpoint() {
        // Arrange
        int candidateId = 1;
        when(candidateService.deleteCandidate(candidateId)).thenReturn(Mono.just(ResponseEntity.ok("Candidate deleted successfully.")));

        // Act & Assert
        webTestClient.delete()
                .uri("/api/v1/candidates/{id}", candidateId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Candidate deleted successfully.");
    }

    @Test
    @Order(99)
    void testDeleteAllCandidatesEndpoint() {
        // Arrange
        when(candidateService.deleteAllCandidates()).thenReturn(Mono.just(ResponseEntity.ok("All candidates deleted successfully.")));

        // Act & Assert
        webTestClient.delete()
                .uri("/api/v1/candidates")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("All candidates deleted successfully.");
    }

    private CreateCandidateDTO createSampleRequestDTO() {
        return CreateCandidateDTO.builder()
                .name("John Doe")
                .email("hola@gmail.com")
                .gender(EGender.FEMALE)
                .phone("1234567890")
                .address("123 Main St")
                .salary_expected(1000.0)
                .date_of_birth("01-01-2000")
                .experience_years(2)
                .education("Bachelor's degree")
                .build();
    }

    private OutCandidateDTO createSampleResponseDTO() {
        return OutCandidateDTO.builder()
                .name("John Doe")
                .email("hola@gmail.com")
                .gender(EGender.FEMALE)
                .phone("1234567890")
                .address("123 Main St")
                .salary_expected(1000.0)
                .date_of_birth("01-01-2000")
                .experience_years(2)
                .education("Bachelor's degree")
                .build();
    }
}
