package com.seek.appcandidate.infrastructure.adapter.rest.candidate;

import com.seek.appcandidate.infrastructure.dto.CreateCandidateDTO;
import com.seek.appcandidate.infrastructure.dto.OutCandidateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
    Mono<OutCandidateDTO> createCandidate(
       @RequestBody @Valid CreateCandidateDTO dto
    );

    //Read
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<OutCandidateDTO> getCandidateById(
            @PathVariable("id") Integer id
    );

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    Mono<OutCandidateDTO> getCandidateByEmail(
            @PathVariable("email") @Email String email
    );

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Flux<OutCandidateDTO> getAllCandidates(
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "minSalary", required = false) Double minSalary,
            @RequestParam(name = "maxSalary", required = false) Double maxSalary,
            @RequestParam(name = "experience", required = false) Double experience
    );

    //Update
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<OutCandidateDTO> updateCandidate(
       @PathVariable("id") Integer id,
       @RequestBody @Valid CreateCandidateDTO dto
    );

   //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<ResponseEntity<String>> deleteCandidate(
       @PathVariable("id") Integer id
    );

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<ResponseEntity<String>> deleteAllCandidates();
}
