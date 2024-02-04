package com.seek.appcandidate.infrastructure.adapter.rest.candidate;

import com.seek.appcandidate.domain.exceptions.CustomError;
import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(ICandidateAPI.BASE_URL)
public interface ICandidateAPI {

    String BASE_URL = "/api/v1/candidates";

    //Create
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new candidate",
            description = "Creates a new candidate.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Candidate created successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = OutCandidateDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while creating the candidate.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<OutCandidateDTO> createCandidate(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Candidate details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateCandidateDTO.class)
                    )
            )
            CreateCandidateDTO dto
    );

    //Read
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get a candidate by ID",
            description = "Retrieves a candidate by their ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Candidate retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = OutCandidateDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while retrieving the candidate.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<OutCandidateDTO> getCandidateById(
            @PathVariable("id")
            @Parameter(description = "Candidate ID", required = true)
            Integer id
    );

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get a candidate by email",
            description = "Retrieves a candidate by their email address.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Candidate retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = OutCandidateDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while retrieving the candidate.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<OutCandidateDTO> getCandidateByEmail(
            @PathVariable("email")
            @Parameter(description = "Candidate email address", required = true)
            String email
    );

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all candidates",
            description = "Retrieves all candidates with optional filtering by salary and experience.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Candidates retrieved successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = OutCandidateDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while retrieving the candidates.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Flux<OutCandidateDTO> getAllCandidates(
            @RequestParam(name = "minSalary", required = false)
            @Parameter(description = "Minimum salary for filtering")
            Double minSalary,

            @RequestParam(name = "maxSalary", required = false)
            @Parameter(description = "Maximum salary for filtering")
            Double maxSalary,

            @RequestParam(name = "minExperience", required = false)
            @Parameter(description = "Minimum experience for filtering")
            Integer minExperience,

            @RequestParam(name = "maxExperience", required = false)
            @Parameter(description = "Maximum experience for filtering")
            Integer maxExperience
    );

    //Update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update a candidate by ID",
            description = "Updates a candidate by their ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Candidate updated successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = OutCandidateDTO.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while updating the candidate.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<OutCandidateDTO> updateCandidate(
       @PathVariable("id")
       @Parameter(description = "Candidate ID", required = true)
       Integer id,

       @Valid
       @RequestBody
       @io.swagger.v3.oas.annotations.parameters.RequestBody(
               description = "Candidate details",
               required = true,
               content = @Content(
                       schema = @Schema(implementation = CreateCandidateDTO.class)
               )
       )
       CreateCandidateDTO dto
    );

   //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a candidate by ID",
            description = "Deletes a candidate by their ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Candidate deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = String.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while deleting the candidate.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Candidate not found.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<ResponseEntity<String>> deleteCandidate(
       @PathVariable("id")
       @Parameter(description = "Candidate ID", required = true)
       Integer id
    );

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete all candidates",
            description = "Deletes all candidates.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All candidates deleted successfully.",
                            content = @Content(
                                    schema = @Schema(implementation = String.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "An error occurred while deleting all candidates.",
                            content = @Content(
                                    schema = @Schema(implementation = CustomError.class),
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    Mono<ResponseEntity<String>> deleteAllCandidates();
}
