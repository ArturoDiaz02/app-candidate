package com.seek.appcandidate.infrastructure.repository;

import com.seek.appcandidate.domain.model.Candidate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ICandidateRepository extends R2dbcRepository<Candidate, Integer>{

    @Query("SELECT * FROM candidates c " +
            "WHERE (:gender IS NULL OR UPPER(c.gender) = UPPER(:gender) ) " +
            "AND (:minSalary IS NULL OR c.salary_expected >= :minSalary) " +
            "AND (:maxSalary IS NULL OR c.salary_expected <= :maxSalary)")
    Flux<Candidate> findByGenderAndSalaryExpectedBetweenAndExperience(
            @Param("gender") String gender,
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary,
            @Param("experience") Double experience
    );

    //if exists by email or phone
    @Query("SELECT * FROM candidates c " +
            "WHERE (:email IS NULL OR c.email = :email) " +
            "OR (:phone IS NULL OR c.phone = :phone)")
    Flux<Candidate> findByEmailOrPhone(
            @Param("email") String email,
            @Param("phone") String phone
    );

    Mono<Candidate> findByEmail(String email);
}
